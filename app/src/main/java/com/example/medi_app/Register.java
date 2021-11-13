//Tobias Lennon
package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.medi_app.model.User;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText registerEmail, registerPassword, registerForename, registerSurname, registerAge;
    private RadioButton registerMale, registerFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn  = findViewById(R.id.registerBtn);
        registerMale        = findViewById(R.id.rdBtnMale);
        registerFemale      = findViewById(R.id.rdBtnFemale);
        registerForename    = findViewById(R.id.registerFirstname);
        registerSurname     = findViewById(R.id.registerSurname);
        registerAge         = findViewById(R.id.registerAge);
        registerEmail       = findViewById(R.id.registerEmail);
        registerPassword    = findViewById(R.id.registerPassword);

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.registerBtn) {

            String regAge = registerAge.getText().toString().trim();
            String regForename = registerForename.getText().toString().trim();
            String regSurname = registerSurname.getText().toString().trim();
            String regEmail = registerEmail.getText().toString().trim();
            String regPassword = registerPassword.getText().toString().trim();
            String regSex = (registerMale.isChecked()) ? "M" : (registerFemale.isChecked()) ? "F" : "";

            //Validating user inputs.
            Boolean validated = validateRegister(regEmail, regPassword, regForename, regSurname, regSex, regAge);

            //Checks validated variable.
            if (validated) {

                int age = Integer.parseInt(regAge);

                //Created user to send to next register activity via intent.
                User tempPatient = createUser(regEmail, regForename, regSurname, regSex, age);
                Intent nextReg = new Intent(Register.this, register2.class);

                //Sending data between activities.
                nextReg.putExtra("user", tempPatient);
                nextReg.putExtra("password", regPassword);
                startActivity(nextReg);
            }
        }
    }

    //Validates user input returns Boolean.
    private Boolean validateRegister(String regEmail, String regPassword, String regForename, String regSurname, String regSex, String regAge){

        //Surname validation
        if(regSurname.isEmpty()){
            registerSurname.setError("Surname is required.");
            registerSurname.requestFocus();
            return false;
        }
        //Forename validation
        if(regForename.isEmpty()){
            registerForename.setError("Firstname is required.");
            registerForename.requestFocus();
            return false;
        }
        //Age validation
        if(regAge.isEmpty()){
            registerAge.setError("Age is required.");
            registerAge.requestFocus();
            return false;
        }
        //Email validation
        if(regEmail.isEmpty()){
            registerEmail.setError("Email is required.");
            registerEmail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(regEmail).matches()){
            registerEmail.setError("Email not valid.");
            registerEmail.requestFocus();
            return false;
        }

        //Password validation
        if(regPassword.isEmpty()){
            registerPassword.setError("Password is required.");
            registerPassword.requestFocus();
            return false;
        }

        if(regPassword.length() < 6){
            registerPassword.setError("Password must be contain more than 6 characters.");
            registerPassword.requestFocus();
            return false;
        }
        //Sex validation
        if(regSex.isEmpty()){
            Toast.makeText(Register.this, "Please select a gender.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Instantiates a User and returns it.
    public User createUser(String registerEmail, String registerForename, String registerSurname, String registerSex, int registerAge ){
        return new User(registerEmail, registerForename, registerSurname, registerSex, registerAge, "", "");
    }
}