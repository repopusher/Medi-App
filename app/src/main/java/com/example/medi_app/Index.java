//Tobias Lennon
package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import java.util.Objects;

public class Index extends AppCompatActivity implements View.OnClickListener{

    private EditText loginEmail, loginPassword;
    private ProgressBar indexProgressBar;
    private FirebaseAuth mAuth;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private Switch switchLogin;
    private Boolean validGP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button indexRegisterBtn = findViewById(R.id.indexRegisterBtn);
        Button indexLoginBtn    = findViewById(R.id.indexLoginBtn);
        loginEmail              = findViewById(R.id.indexEmail);
        loginPassword           = findViewById(R.id.indexPassword);
        indexProgressBar        = findViewById(R.id.indexProgressBar);
        switchLogin             = findViewById(R.id.switchIndex);
        mAuth                   = FirebaseAuth.getInstance();

        indexRegisterBtn.setOnClickListener(this);
        indexLoginBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.indexRegisterBtn:
                Intent switchActivity = new Intent(Index.this, Register.class);
                startActivity(switchActivity);
                break;
            case R.id.indexLoginBtn:

                String logEmail = loginEmail.getText().toString().trim();
                String logPass = loginPassword.getText().toString().trim();

                Boolean validated = validateLogin(logEmail, logPass);

                if(validated){
                    loginUser(logEmail, logPass, switchLogin);
                }
                
        }
    }

    //Logs a user in.
    private void loginUser(String logEmail, String logPass, Switch switchLogin) {

        indexProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(logEmail, logPass).addOnCompleteListener(loginTask -> {
            if(loginTask.isSuccessful()){
                //GP logging in
                if(switchLogin.isChecked()){

                    validateGP(mAuth.getCurrentUser().getUid(), new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Boolean toggle = false;
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if (mAuth.getCurrentUser().getUid().equals(snapshot.getKey())) {
                                    toggle = true;
                                }
                            }

                            if(toggle){
                                startActivity(new Intent(Index.this, GP_dash.class));
                            }
                            else{
                                indexProgressBar.setVisibility(View.GONE);
                                loginEmail.setError("Not a medical professional.");
                                loginEmail.requestFocus();
                            }
                        }

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onFailure() {
                        }
                    });
                }
                //Patient logging in
                else {
                    startActivity(new Intent(Index.this, UserIndex.class));
                }
            }
            else{
                Toast.makeText(Index.this, "Failed to login! Check details.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public interface OnGetDataListener{
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    private void validateGP(String uid, final OnGetDataListener listener) {
        listener.onStart();
        DatabaseReference myRef = database.getReference().child("GPs");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailure();
            }
        });
    }

    //Validates user input details.
    private Boolean validateLogin(String logEmail, String logPass) {

        if(logEmail.isEmpty()){
            loginEmail.setError("Email is required.");
            loginEmail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(logEmail).matches()){
            loginEmail.setError("Enter a valid email.");
            loginEmail.requestFocus();
            return false;
        }

        if(logPass.isEmpty()){
            loginPassword.setError("Password is required.");
            loginPassword.requestFocus();
            return false;
        }

        if(logPass.length() < 6){
            loginPassword.setError("Minimum password length is 6 characters.");
            loginPassword.requestFocus();
            return false;
        }

        return true;
    }
}