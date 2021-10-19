package com.example.medi_app.model;

import androidx.annotation.NonNull;

public class User {

    private String email, sex, surname, forename;
    private int age;

    public User(){}

    public User(String email, String forename, String surname, String sex, int age){
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.sex = sex;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", surname='" + surname + '\'' +
                ", forename='" + forename + '\'' +
                ", age=" + age +
                '}';
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
