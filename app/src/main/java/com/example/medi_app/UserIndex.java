package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserIndex extends AppCompatActivity implements View.OnClickListener {

    ImageView reviewBtn;
    CardView accountBtn, insuranceBtn, contactBtn, mediPredictBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);

        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(this);
        accountBtn = findViewById(R.id.accountCard);
        accountBtn.setOnClickListener(this);
        insuranceBtn = findViewById(R.id.insuranceCard);
        insuranceBtn.setOnClickListener(this);
        contactBtn = findViewById(R.id.contactCard);
        contactBtn.setOnClickListener(this);
        mediPredictBtn = findViewById(R.id.mediPredictCard);
        mediPredictBtn.setOnClickListener(this);

    }

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
                startActivity(new Intent(UserIndex.this, Insurance.class));
                break;
            case R.id.contactCard:
                startActivity(new Intent(UserIndex.this, Contact.class));
                break;
            case R.id.mediPredictCard:
                startActivity(new Intent(UserIndex.this, MediPredict.class));
        }
    }
}