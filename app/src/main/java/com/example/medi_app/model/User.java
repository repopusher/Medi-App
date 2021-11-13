package com.example.medi_app.model;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {

    private String email, sex, surname, forename, healthInsurance, GPId;
    private int age;

    public User(){}

    public User(String email, String forename, String surname, String sex, int age, String healthInsurance, String GPId){
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
        this.healthInsurance = healthInsurance;
        this.GPId = GPId;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", surname='" + surname + '\'' +
                ", forename='" + forename + '\'' +
                ", healthInsurance='" + healthInsurance + '\'' +
                ", GPId='" + GPId + '\'' +
                ", age=" + age +
                '}';
    }

    protected User(Parcel in) {
        email = in.readString();
        sex = in.readString();
        surname = in.readString();
        forename = in.readString();
        healthInsurance = in.readString();
        GPId = in.readString();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(sex);
        dest.writeString(surname);
        dest.writeString(forename);
        dest.writeString(healthInsurance);
        dest.writeString(GPId);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getGPId() {
        return GPId;
    }

    public void setGPId(String GPId) {
        this.GPId = GPId;
    }

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
