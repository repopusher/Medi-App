package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.medi_app.model.InsuranceCompany;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PayPalButton;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;

public class Insurance extends AppCompatActivity {

    PayPalButton payPalButton;
    RadioGroup purchaseQuantityRadioGroup;
    RadioButton selectedButton;
    EditText userAddressField;
//    MediCoin contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        TextView tvHead = findViewById(R.id.insuranceHeading);
        TextView tvMonthly = findViewById(R.id.insuranceMonthly);
        purchaseQuantityRadioGroup = findViewById(R.id.select_coin_amount);
        userAddressField = (EditText) findViewById(R.id.userAddress);


        InsuranceCompany insurance = getIntent().getParcelableExtra("Insurance");

        tvHead.setText(insurance.getHcName());
        tvMonthly.setText(String.valueOf(insurance.getMonthly()));

        payPalButton = findViewById(R.id.payPalButton);





        // ====================================
        // PayPal
        // ====================================
        CheckoutConfig config = new CheckoutConfig(
                getApplication(),
                "AQNDWqdzYC3Rr9B96ZMC7osJxtH5cn5nhhjy8by_mIH22ezisg0YSC7c0zLoB7Pach5ke6s2pvRQmttY",
                Environment.SANDBOX,
                String.format("%s://paypalpay", "com.example.medi.app"),
                CurrencyCode.EUR,
                UserAction.PAY_NOW,
                new SettingsConfig (
                true,
                false
                )
        );
        PayPalCheckout.setConfig(config);


        payPalButton.setup(
                createOrderActions -> {
                    ArrayList purchaseUnits = new ArrayList<>();

                    String radioCoinValue = ((RadioButton)findViewById(purchaseQuantityRadioGroup.getCheckedRadioButtonId())).getText().toString();
                    float coinValue = Float.parseFloat(radioCoinValue);
                    coinValue = coinValue / 100;
                    float finalCoinValue = coinValue;

                    purchaseUnits.add(new PurchaseUnit.Builder()
                        .amount(new Amount.Builder()
                                .currencyCode(CurrencyCode.EUR)
                                .value(Float.toString(finalCoinValue))
                                .build()
                        )
                        .build()
                    );
                    Order order = new Order(
                        OrderIntent.CAPTURE,
                        new AppContext.Builder()
                            .userAction(UserAction.PAY_NOW)
                            .build(),
                        purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },

                approval -> approval.getOrderActions().capture(result -> {
                    Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));

                    // Get Chosen MediCoin Amount
                    String radiovalue = ((RadioButton)findViewById(purchaseQuantityRadioGroup.getCheckedRadioButtonId())).getText().toString();
                    int selectedAmount = Integer.parseInt(radiovalue);


                    // Get user address
                    String userAddress = userAddressField.getText().toString();


                    // ====================================
                    // MediCoin Web3j
                    // ====================================
                    // Create Web3j and connect to Infura
                    Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/e9ec5256504146bfbcb2f677bca26bce"));
                    System.out.println("Connecting to ropsten");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                    Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
                                    String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                                    Log.i("Client Version: ", String.valueOf(clientVersion));
                                    String privateKey = "43eb97896a41ac0e47c141b9d403fe986c4e59bd3fd5c923c7ea658f43dba9be";

                                    BigInteger key = new BigInteger(privateKey, 16);
                                    ECKeyPair ecKeyPair = ECKeyPair.create(key.toByteArray());
                                    Credentials credentials = Credentials.create(ecKeyPair);

                                    TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3);
                                    TransactionManager transactionManager = new RawTransactionManager(
                                            web3, credentials, ChainId.ROPSTEN, transactionReceiptProcessor
                                    );
                                    MediCoin contract = MediCoin.load("0xdfE246A4502c86f0e5BCB88118EFdC4442Aa17eD",
                                            web3,
                                            transactionManager,
                                            GAS_PRICE, GAS_LIMIT);

                                        // Reset address field
                                        userAddressField.setText("");

                                        // Purchase Medicoin
                                        purchaseMediCoin(userAddress, selectedAmount, contract);

                                }
                                catch(Exception ex) {
                                    System.out.println("Exception: " + ex);
                                }
                        }
                    });
                    thread.start();
                })
        );
    }

    public void purchaseMediCoin(String address, int amount, MediCoin contract){
        try {
            Log.i("Test Firing:", "Purchase MediCoin Function");

            BigInteger value = new BigInteger(Integer.toString(amount) + "000000000000000000");
            Log.i("Test Amount:", String.valueOf(value));
            Log.i("Test Amount Type:", String.valueOf(value.getClass()));
            Log.i("Test Address:", address);
            Log.i("Test Address Type:", String.valueOf(address.getClass()));
            TransactionReceipt mReceipt = contract.transfer(address, value).sendAsync().get();

        } catch (Exception e) {
            System.out.println("Eth Exception " + e);
        }
    }
}