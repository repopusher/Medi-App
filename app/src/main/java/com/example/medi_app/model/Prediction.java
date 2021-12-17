package com.example.medi_app.model;

public class Prediction {
    private float diabetes;
    private float heartDisease;
    private float alzheimers;

    public Prediction(){}

    public Prediction(float diabetes, float heartDisease, float alzheimers) {
        this.diabetes = diabetes;
        this.heartDisease = heartDisease;
        this.alzheimers = alzheimers;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "diabetes=" + diabetes +
                ", heartDisease=" + heartDisease +
                ", alzheimers=" + alzheimers +
                '}';
    }

    public float getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(float diabetes) {
        this.diabetes = diabetes;
    }

    public float getHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(float heartDisease) {
        this.heartDisease = heartDisease;
    }

    public float getAlzheimers() {
        return alzheimers;
    }

    public void setAlzheimers(float alzheimers) {
        this.alzheimers = alzheimers;
    }
}
