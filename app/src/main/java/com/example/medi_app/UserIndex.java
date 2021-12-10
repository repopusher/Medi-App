package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medi_app.model.Form;
import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import com.example.medi_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserIndex extends AppCompatActivity implements View.OnClickListener {

    private ImageView reviewBtn, logoutBtn;
    private CardView accountBtn, insuranceBtn, contactBtn, mediPredictBtn;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private FirebaseAuth mAuth;
    private User patient;
    private GP patientGP;
    private InsuranceCompany patientInsurance;
    private Form patientForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);
        Objects.requireNonNull(getSupportActionBar()).hide();

        logoutBtn = findViewById(R.id.logoutBtn);
        reviewBtn = findViewById(R.id.reviewBtn);
        accountBtn = findViewById(R.id.accountCard);
        insuranceBtn = findViewById(R.id.insuranceCard);
        contactBtn = findViewById(R.id.contactCard);
        mediPredictBtn = findViewById(R.id.mediPredictCard);
        mAuth = FirebaseAuth.getInstance();

        mediPredictBtn.setOnClickListener(this);
        insuranceBtn.setOnClickListener(this);
        contactBtn.setOnClickListener(this);
        accountBtn.setOnClickListener(this);
        reviewBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        readDatabase("Patients");
        readDatabase("Insurance");
        readDatabase("GPs");
        readForm();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UserIndex.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                startActivity(new Intent(UserIndex.this, Index.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reviewBtn:
                startActivity(new Intent(UserIndex.this, Review.class));
                break;
            case R.id.accountCard:
                startActivity(new Intent(UserIndex.this, Account.class));
                break;
            case R.id.insuranceCard:
                if (patientInsurance != null) {
                    Intent insuranceIntent = new Intent(this, Insurance.class);
                    insuranceIntent.putExtra("Insurance", patientInsurance);
                    startActivity(insuranceIntent);
                }
                break;
            case R.id.contactCard:
                if (patientInsurance != null && patientGP != null) {
                    Intent contactIntent = new Intent(this, Contact.class);
                    contactIntent.putExtra("GP", patientGP);
                    contactIntent.putExtra("Insurance", patientInsurance);
                    startActivity(contactIntent);
                }
                break;
            case R.id.mediPredictCard:
                if (patientForm != null) {
                    Intent intent = new Intent(UserIndex.this, MediPredict.class);
                    intent.putExtra("patientForm", patientForm);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "GP has not filled out your form yet.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logoutBtn:
                onBackPressed();
                break;
        }
    }


    private void readDatabase(String branch) {
        DatabaseReference myRef = database.getReference().child(branch);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    switch (branch) {
                        case "Insurance":
                            if (Objects.equals(snapshot.getKey(), patient.getHealthInsurance())) {
                                patientInsurance = snapshot.getValue(InsuranceCompany.class);
                            }
                            break;
                        case "GPs":
                            if (Objects.equals(snapshot.getKey(), patient.getGPId())) {
                                patientGP = snapshot.getValue(GP.class);
                            }
                            break;
                        case "Patients":
                            if (Objects.equals(snapshot.getKey(), Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                                patient = snapshot.getValue(User.class);
                            }
                            break;
                        default:
                            Toast.makeText(UserIndex.this, "Can't run without branch parameter", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR\t", "Failed to read value.", error.toException());
            }
        });
    }

    private void readForm() {
        DatabaseReference myRef = database.getReference("Forms");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (Objects.equals(dataSnapshot.child("patient").getValue(), mAuth.getUid())) {
                        patientForm = dataSnapshot.getValue(Form.class);
                    }
                    else {
                        Toast.makeText(UserIndex.this, "Please contact your GP and ask them to fill out your forms.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}