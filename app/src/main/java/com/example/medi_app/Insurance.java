package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.medi_app.model.InsuranceCompany;

public class Insurance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        TextView tvHead = findViewById(R.id.insuranceHeading);
        TextView tvMonthly = findViewById(R.id.insuranceMonthly);


        InsuranceCompany insurance = getIntent().getParcelableExtra("Insurance");

        tvHead.setText(insurance.getHcName());
        tvMonthly.setText(String.valueOf(insurance.getMonthly()));

    }
}