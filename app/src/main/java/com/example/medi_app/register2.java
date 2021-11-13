package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import com.example.medi_app.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class register2 extends AppCompatActivity implements View.OnClickListener{
    private final ArrayList<String> spinnerDisplayInsruance = new ArrayList<>();
    private final ArrayList<String> spinnerDisplayGP = new ArrayList<>();
    private final ArrayList<String> GPkeys = new ArrayList<>();
    private final ArrayList<String> insuranceKeys = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private Spinner spinnerInsurance, spinnerGPs;
    private Button regBtn;
    private int insurancePos, GPpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        spinnerInsurance = findViewById(R.id.spinnerReg);
        spinnerGPs = findViewById(R.id.spinnerReg2);
        regBtn = findViewById(R.id.registerBtn2);
        regBtn.setOnClickListener(this);

        User patient = getIntent().getParcelableExtra("user");

        readDatabase("Insurance", spinnerInsurance);
        readDatabase("GPs", spinnerGPs);


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
    public void onClick(View v){
        if (v.getId() == R.id.registerBtn2) {

            String pos = String.valueOf(insurancePos);
            String pos1 = String.valueOf(GPpos);
            String pos2 = pos + " " + pos1;
            Toast.makeText(this, pos2, Toast.LENGTH_SHORT).show();
            }
        }

    private void readDatabase(String branch, Spinner spinner){
        DatabaseReference myRef = database.getReference().child(branch);
        ArrayAdapter<String> adapter;

        if(branch.equals("Insurance")) {
            adapter = new ArrayAdapter<String>(register2.this, android.R.layout.simple_list_item_1, spinnerDisplayInsruance);
        }
        else {
            adapter = new ArrayAdapter<String>(register2.this, android.R.layout.simple_list_item_1, spinnerDisplayGP);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (branch.equals("Insurance")) {
                    spinnerDisplayInsruance.clear();
                    insuranceKeys.clear();
                }
                else {
                    spinnerDisplayGP.clear();
                    GPkeys.clear();
                }

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    switch (branch){
                        case "Insurance":
                            InsuranceCompany insurance = snapshot.getValue(InsuranceCompany.class);
                            spinnerDisplayInsruance.add(insurance.getHcName());
                            insuranceKeys.add(snapshot.getKey());
                            break;
                        case "GPs":
                            GP gp = snapshot.getValue(GP.class);
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
}