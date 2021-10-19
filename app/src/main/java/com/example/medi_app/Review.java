package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import com.example.medi_app.control.Control;

public class Review extends AppCompatActivity implements View.OnClickListener {

    RatingBar reviewRatingBar;
    Button insetReviewBtn;
    EditText reviewContent;
    Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewContent = findViewById(R.id.reviewEditText);
        insetReviewBtn = findViewById(R.id.postReviewBtn);
        insetReviewBtn.setOnClickListener(this);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postReviewBtn:
                String content = reviewContent.getText().toString().trim();
                int score = reviewRatingBar.getNumStars();
        }
    }
}