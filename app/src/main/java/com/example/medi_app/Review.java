package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.medi_app.model.ReviewClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Review extends AppCompatActivity {

    RatingBar reviewRatingBar;
    EditText reviewContent;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    FirebaseUser user;
    ListView reviewLv;
    DatabaseReference myRef;
    ArrayList<String> reviews = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reviewContent = findViewById(R.id.reviewEditText);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
        reviewLv = findViewById(R.id.reviewLv);

        //OnTouchListener listen for touch event on the drawable in the edit text to the right.
        reviewContent.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (reviewContent.getRight() - reviewContent.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    //Validating review fields
                    float rating = reviewRatingBar.getRating();
                    if (!reviewContent.getText().toString().isEmpty() && rating != 0) {
                        String content = reviewContent.getText().toString().trim();
                        int score = (int) reviewRatingBar.getRating();
                        ReviewClass review = new ReviewClass(user.getEmail(), content, score);

                        insertReview(review);
                    }
                    else {
                        reviewContent.setError("Field(s) left empty.");
                        reviewContent.requestFocus();
                    }
                    return true;
                }
            }
            return false;
        });

        myRef = database.getReference().child("Reviews");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.review_adapter, R.id.reviewTvContents, reviews);
        reviewLv.setAdapter(adapter);
        //Listens for when Reviews is updated and redefines the arraylist shown to the adapter.
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviews.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ReviewClass review = snapshot.getValue(ReviewClass.class);
                    String txt = review.getEmail() + "\n" + review.getReviewContent() + "\n" + review.getReviewScore();
                    reviews.add(txt);
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
    //Method inserts a review object into the Review branch in the firebase realtime database.
    public void insertReview(ReviewClass review){
        database.getReference("Reviews").child(FirebaseAuth.getInstance().getCurrentUser()
                .getUid()).setValue(review).addOnCompleteListener(insertReviewTask -> {
                    if(insertReviewTask.isSuccessful()){
                        Toast.makeText(Review.this, "Review posted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Review.this, "Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                });
    }
}