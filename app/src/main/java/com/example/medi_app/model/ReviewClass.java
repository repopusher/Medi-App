package com.example.medi_app.model;

public class ReviewClass {

    private String email, reviewContent;
    private int reviewScore;

    public ReviewClass(){}

    public ReviewClass(String email, String reviewContent, int reviewScore){
        this.email = email;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
    }

    @Override
    public String toString() {
        return "ReviewClass{" +
                "email='" + email + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewScore=" + reviewScore +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }
}
