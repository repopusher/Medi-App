package com.example.medi_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GP implements Parcelable {

    private String email, name, phone;

    public GP(String email, String name, String phone){
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public GP(){ }

    protected GP(Parcel in) {
        email = in.readString();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<GP> CREATOR = new Creator<GP>() {
        @Override
        public GP createFromParcel(Parcel in) {
            return new GP(in);
        }

        @Override
        public GP[] newArray(int size) {
            return new GP[size];
        }
    };

    @Override
    public String toString() {
        return "GP{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(phone);
    }
}
