package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class UserIndex extends AppCompatActivity implements View.OnClickListener {

    ImageView reviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);

        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reviewBtn:
                startActivity(new Intent(UserIndex.this, Review.class));
                break;
        }
    }
}