package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.medi_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private EditText registerEmail, registerPassword, registerForename, registerSurname, registerAge;
    private RadioButton registerMale, registerFemale;
    private ProgressBar registerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        registerMale = findViewById(R.id.rdBtnMale);
        registerFemale = findViewById(R.id.rdBtnFemale);
        registerForename = findViewById(R.id.registerFirstname);
        registerSurname = findViewById(R.id.registerSurname);
        registerAge = findViewById(R.id.registerAge);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);

        registerProgressBar = findViewById(R.id.registerProgressBar);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerBtn:

                String regAge = registerAge.getText().toString().trim();
                String regForename = registerForename.getText().toString().trim();
                String regSurname = registerSurname.getText().toString().trim();
                String regEmail = registerEmail.getText().toString().trim();
                String regPassword = registerPassword.getText().toString().trim();
                String regSex = (registerMale.isChecked())?"M":(registerFemale.isChecked())?"F":"";

                Boolean validated = validateRegister(regEmail, regPassword,regForename, regSurname, regSex, regAge);
                if(validated){
                    //Validation is successful, call createUser() and pass return value into registerUser to authenticate and insert user into database.
                    int age = Integer.parseInt(regAge);
                    User user = createUser(regEmail, regForename, regSurname, regSex, age);
                    registerUser(regEmail, regPassword, user);
                }
                break;

        }
    }
    //Method validates all the fields the patient inputs.
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

    //Method authenticates the patients email and password and then inserts the patient object to
    // the firebase realtime database in JSON format.
    private void registerUser(String regEmail, String regPassword, User patient) {

        registerProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(regEmail, regPassword)
                .addOnCompleteListener(task -> {

                    //If authentication is successful, create a user and store it in the realtime database.
                    if(task.isSuccessful()){
                        database.getReference("Patients").child(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid()).setValue(patient).addOnCompleteListener(registerTask -> {
                                    if(registerTask.isSuccessful()){
                                        registerProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_LONG).show();

                                        Intent switchActivity = new Intent(Register.this, Index.class);
                                        startActivity(switchActivity);
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                                        registerProgressBar.setVisibility(View.GONE);
                                    }
                        });
                    }
                    else{
                        Toast.makeText(Register.this, "Failed to register.", Toast.LENGTH_LONG).show();
                        registerProgressBar.setVisibility(View.GONE);
                    }
                });


    }

    //Method instantiates and returns User object.
    public User createUser(String registerEmail, String registerForename, String registerSurname, String registerSex, int registerAge ){
        return new User(registerEmail, registerForename, registerSurname, registerSex, registerAge);
    }
}