package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    ArrayList<String> spinnerView = new ArrayList<>();
    InsuranceCompany insurance;
    Spinner spinnerContact;
    ArrayAdapter<String> adapter;
    GP gp;
    Button callBtn, sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        spinnerContact  = findViewById(R.id.spinnerContact);
        insurance       = getIntent().getParcelableExtra("Insurance");
        callBtn         = findViewById(R.id.callContactBtn);
        sendBtn         = findViewById(R.id.sendContactBtn);
        gp              = getIntent().getParcelableExtra("GP");

        spinnerView.add(insurance.getHcName());
        String gpString = gp.getName() + "        " + gp.getPhone();
        spinnerView.add(gpString);

        adapter = new ArrayAdapter<>(Contact.this, android.R.layout.simple_list_item_1, spinnerView);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContact.setAdapter(adapter);

    }
}