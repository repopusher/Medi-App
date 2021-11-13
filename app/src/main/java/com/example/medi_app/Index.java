//Tobias Lennon
package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class Index extends AppCompatActivity implements View.OnClickListener{

    private EditText loginEmail, loginPassword;
    private ProgressBar indexProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button indexRegisterBtn = findViewById(R.id.indexRegisterBtn);
        Button indexLoginBtn    = findViewById(R.id.indexLoginBtn);
        loginEmail              = findViewById(R.id.indexEmail);
        loginPassword           = findViewById(R.id.indexPassword);
        indexProgressBar        = findViewById(R.id.indexProgressBar);
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
                    loginUser(logEmail, logPass);
                }
                
        }
    }

    //Logs a user in.
    private void loginUser(String logEmail, String logPass) {

        indexProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(logEmail, logPass).addOnCompleteListener(loginTask -> {
            if(loginTask.isSuccessful()){
                startActivity(new Intent(Index.this, UserIndex.class));
            }
            else{
                Toast.makeText(Index.this, "Failed to login! Check details.", Toast.LENGTH_LONG).show();
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