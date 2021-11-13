package com.example.medi_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InsuranceCompany implements Parcelable {

    private String email, hcName, phone;
    private int monthly;

    public InsuranceCompany(){}

    public InsuranceCompany(String email, String hcName, String phone, int monthly) {
        this.email = email;
        this.hcName = hcName;
        this.phone = phone;
        this.monthly = monthly;
    }

    protected InsuranceCompany(Parcel in) {
        email = in.readString();
        hcName = in.readString();
        phone = in.readString();
        monthly = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(hcName);
        dest.writeString(phone);
        dest.writeInt(monthly);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InsuranceCompany> CREATOR = new Creator<InsuranceCompany>() {
        @Override
        public InsuranceCompany createFromParcel(Parcel in) {
            return new InsuranceCompany(in);
        }

        @Override
        public InsuranceCompany[] newArray(int size) {
            return new InsuranceCompany[size];
        }
    };

    @Override
    public String toString() {
        return "InsuranceCompany{" +
                "email='" + email + '\'' +
                ", hcName='" + hcName + '\'' +
                ", phone='" + phone + '\'' +
                ", monthly=" + monthly +
                '}';
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHcName() {
        return hcName;
    }

    public void setHcName(String hcName) {
        this.hcName = hcName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMonthly() {
        return monthly;
    }

    public void setMonthly(int monthly) {
        this.monthly = monthly;
    }
}
