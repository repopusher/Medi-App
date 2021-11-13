//Tobias Lennon
package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import com.example.medi_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class register2 extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<String> spinnerDisplayInsurance = new ArrayList<>();
    private final ArrayList<String> spinnerDisplayGP        = new ArrayList<>();
    private final ArrayList<String> GPkeys                  = new ArrayList<>();
    private final ArrayList<String> insuranceKeys           = new ArrayList<>();
    private final FirebaseDatabase  database                = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private ProgressBar registerProgressBar;
    private int insurancePos, GPpos;
    private FirebaseAuth mAuth;
    private User patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Spinner spinnerInsurance = findViewById(R.id.spinnerReg);
        Spinner spinnerGPs       = findViewById(R.id.spinnerReg2);
        Button regBtn            = findViewById(R.id.registerBtn2);
        registerProgressBar      = findViewById(R.id.progressBar);
        mAuth                    = FirebaseAuth.getInstance();
        patient                  = getIntent().getParcelableExtra("user");

        regBtn.setOnClickListener(this);
        registerProgressBar.setVisibility(View.GONE);

        readDatabase("Insurance", spinnerInsurance);
        readDatabase("GPs", spinnerGPs);

        //Get position of selected spinner item and uses that position as an index in a list of
        //GP and insurance IDs to then assign to the patient.
        spinnerInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                insurancePos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGPs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GPpos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn2) {

            patient.setHealthInsurance(insuranceKeys.get(insurancePos));
            patient.setGPId(GPkeys.get(GPpos));
            String regPassword = getIntent().getStringExtra("password");
            registerUser(regPassword, patient);

        }
    }

    //Reads database based on the branch passed, puts read items into adapter and sets onto view.
    private void readDatabase(String branch, Spinner spinner) {
        DatabaseReference myRef = database.getReference().child(branch);
        ArrayAdapter<String> adapter;

        if (branch.equals("Insurance")) {
            adapter = new ArrayAdapter<>(register2.this, android.R.layout.simple_list_item_1, spinnerDisplayInsurance);
        } else {
            adapter = new ArrayAdapter<>(register2.this, android.R.layout.simple_list_item_1, spinnerDisplayGP);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (branch.equals("Insurance")) {
                    spinnerDisplayInsurance.clear();
                    insuranceKeys.clear();
                } else {
                    spinnerDisplayGP.clear();
                    GPkeys.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    switch (branch) {
                        case "Insurance":
                            InsuranceCompany insurance = snapshot.getValue(InsuranceCompany.class);
                            assert insurance != null;
                            spinnerDisplayInsurance.add(insurance.getHcName());
                            insuranceKeys.add(snapshot.getKey());
                            break;
                        case "GPs":
                            GP gp = snapshot.getValue(GP.class);
                            assert gp != null;
                            spinnerDisplayGP.add(gp.getName() + ", " + gp.getPhone());
                            GPkeys.add(snapshot.getKey());
                            break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR\t", "Failed to read value.", error.toException());
            }
        });
    }

    //Method authenticates the patients email and password and then inserts the patient object to
    // the firebase realtime database in JSON format.
    private void registerUser(String regPassword, User patient) {

        registerProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(patient.getEmail(), regPassword).addOnCompleteListener(task -> {

            //If authentication is successful, create a user and store it in the realtime database.
            if (task.isSuccessful()) {
                database.getReference("Patients").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                        .getUid()).setValue(patient).addOnCompleteListener(registerTask -> {
                            if (registerTask.isSuccessful()) {
                                registerProgressBar.setVisibility(View.GONE);
                                Toast.makeText(register2.this, "Successfully registered", Toast.LENGTH_LONG).show();

                                Intent switchActivity = new Intent(register2.this, Index.class);
                                startActivity(switchActivity);
                            } else {
                                Toast.makeText(register2.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                                registerProgressBar.setVisibility(View.GONE);
                            }
                        });
            } else {
                Toast.makeText(register2.this, "Failed to register.", Toast.LENGTH_LONG).show();
                registerProgressBar.setVisibility(View.GONE);
            }
        });
    }
}