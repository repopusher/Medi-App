package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Insurance extends AppCompatActivity {

    PayPalButton payPalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        TextView tvHead = findViewById(R.id.insuranceHeading);
        TextView tvMonthly = findViewById(R.id.insuranceMonthly);


        InsuranceCompany insurance = getIntent().getParcelableExtra("Insurance");

        tvHead.setText(insurance.getHcName());
        tvMonthly.setText(String.valueOf(insurance.getMonthly()));

        payPalButton = findViewById(R.id.payPalButton);
        // PayPal
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

                new CreateOrder() {

                    @Override

                    public void create(@NotNull CreateOrderActions createOrderActions) {

                        ArrayList purchaseUnits = new ArrayList<>();

                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.EUR)
                                                        .value("10.00")
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
                    }
                },

                new OnApprove() {

                    @Override

                    public void onApprove(@NotNull Approval approval) {

                        approval.getOrderActions().capture(new OnCaptureComplete() {

                            @Override

                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {

                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));

                            }

                        });

                    }

                }

        );
    }
}