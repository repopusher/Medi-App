package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import com.example.medi_app.model.ReviewClass;
import com.example.medi_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserIndex extends AppCompatActivity implements View.OnClickListener {

    private ImageView reviewBtn;
    private CardView accountBtn, insuranceBtn, contactBtn, mediPredictBtn;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private FirebaseAuth mAuth;
    private User patient;
    private GP patientGP;
    private InsuranceCompany patientInsurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        readDatabase("Patients");
        readDatabase("Insurance");
        readDatabase("GPs");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reviewBtn:
                startActivity(new Intent(UserIndex.this, Review.class));
                break;
            case R.id.accountCard:
                startActivity(new Intent(UserIndex.this, Account.class));
                break;
            case R.id.insuranceCard:
                if(patientInsurance != null) {
                    Intent insuranceIntent = new Intent(this, Insurance.class);
                    insuranceIntent.putExtra("Insurance", patientInsurance);
                    startActivity(insuranceIntent);
                }
                break;
            case R.id.contactCard:
                if(patientInsurance != null && patientGP != null) {
                    Intent contactIntent = new Intent(this, Contact.class);
                    contactIntent.putExtra("GP", patientGP);
                    contactIntent.putExtra("Insurance", patientInsurance);
                    startActivity(contactIntent);
                }
                break;
            case R.id.mediPredictCard:
                startActivity(new Intent(UserIndex.this, MediPredict.class));
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
}