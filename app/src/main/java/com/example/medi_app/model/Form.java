package com.example.medi_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Form implements Parcelable {
    private String patient;
    private int pregnancies, glucose, bloodPressure, skinThickness, insulin, age, gender, hand,
            educ, ses, mmse, etiv, zero, one, two, three, four, five, six, seven, eight, ten,
            eleven, twelve;
    private float bmi, diabetesPedigreeFunction, nwbv, asf, nine;

    public Form(String patient, int pregnancies, int glucose, int bloodPressure, int skinThickness, int insulin, int age, int gender, int hand, int educ, int ses, int mmse, int etiv, int zero, int one, int two, int three, int four, int five, int six, int seven, int eight, int ten, int eleven, int twelve, float bmi, float diabetesPedigreeFunction, float nwbv, float asf, float nine) {
        this.patient = patient;
        this.pregnancies = pregnancies;
        this.glucose = glucose;
        this.bloodPressure = bloodPressure;
        this.skinThickness = skinThickness;
        this.insulin = insulin;
        this.age = age;
        this.gender = gender;
        this.hand = hand;
        this.educ = educ;
        this.ses = ses;
        this.mmse = mmse;
        this.etiv = etiv;
        this.zero = zero;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.ten = ten;
        this.eleven = eleven;
        this.twelve = twelve;
        this.bmi = bmi;
        this.diabetesPedigreeFunction = diabetesPedigreeFunction;
        this.nwbv = nwbv;
        this.asf = asf;
        this.nine = nine;
    }

    public Form(){ }

    @Override
    public String toString() {
        return "Form{" +
                "patient='" + patient + '\'' +
                ", pregnancies=" + pregnancies +
                ", glucose=" + glucose +
                ", bloodPressure=" + bloodPressure +
                ", skinThickness=" + skinThickness +
                ", insulin=" + insulin +
                ", age=" + age +
                ", gender=" + gender +
                ", hand=" + hand +
                ", educ=" + educ +
                ", ses=" + ses +
                ", mmse=" + mmse +
                ", etiv=" + etiv +
                ", zero=" + zero +
                ", one=" + one +
                ", two=" + two +
                ", three=" + three +
                ", four=" + four +
                ", five=" + five +
                ", six=" + six +
                ", seven=" + seven +
                ", eight=" + eight +
                ", ten=" + ten +
                ", eleven=" + eleven +
                ", twelve=" + twelve +
                ", bmi=" + bmi +
                ", diabetesPedigreeFunction=" + diabetesPedigreeFunction +
                ", nwbv=" + nwbv +
                ", asf=" + asf +
                ", nine=" + nine +
                '}';
    }

    protected Form(Parcel in) {
        patient = in.readString();
        pregnancies = in.readInt();
        glucose = in.readInt();
        bloodPressure = in.readInt();
        skinThickness = in.readInt();
        insulin = in.readInt();
        age = in.readInt();
        gender = in.readInt();
        hand = in.readInt();
        educ = in.readInt();
        ses = in.readInt();
        mmse = in.readInt();
        etiv = in.readInt();
        zero = in.readInt();
        one = in.readInt();
        two = in.readInt();
        three = in.readInt();
        four = in.readInt();
        five = in.readInt();
        six = in.readInt();
        seven = in.readInt();
        eight = in.readInt();
        ten = in.readInt();
        eleven = in.readInt();
        twelve = in.readInt();
        bmi = in.readFloat();
        diabetesPedigreeFunction = in.readFloat();
        nwbv = in.readFloat();
        asf = in.readFloat();
        nine = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patient);
        dest.writeInt(pregnancies);
        dest.writeInt(glucose);
        dest.writeInt(bloodPressure);
        dest.writeInt(skinThickness);
        dest.writeInt(insulin);
        dest.writeInt(age);
        dest.writeInt(gender);
        dest.writeInt(hand);
        dest.writeInt(educ);
        dest.writeInt(ses);
        dest.writeInt(mmse);
        dest.writeInt(etiv);
        dest.writeInt(zero);
        dest.writeInt(one);
        dest.writeInt(two);
        dest.writeInt(three);
        dest.writeInt(four);
        dest.writeInt(five);
        dest.writeInt(six);
        dest.writeInt(seven);
        dest.writeInt(eight);
        dest.writeInt(ten);
        dest.writeInt(eleven);
        dest.writeInt(twelve);
        dest.writeFloat(bmi);
        dest.writeFloat(diabetesPedigreeFunction);
        dest.writeFloat(nwbv);
        dest.writeFloat(asf);
        dest.writeFloat(nine);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Form> CREATOR = new Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public int getPregnancies() {
        return pregnancies;
    }

    public void setPregnancies(int pregnancies) {
        this.pregnancies = pregnancies;
    }

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getSkinThickness() {
        return skinThickness;
    }

    public void setSkinThickness(int skinThickness) {
        this.skinThickness = skinThickness;
    }

    public int getInsulin() {
        return insulin;
    }

    public void setInsulin(int insulin) {
        this.insulin = insulin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public int getEduc() {
        return educ;
    }

    public void setEduc(int educ) {
        this.educ = educ;
    }

    public int getSes() {
        return ses;
    }

    public void setSes(int ses) {
        this.ses = ses;
    }

    public int getMmse() {
        return mmse;
    }

    public void setMmse(int mmse) {
        this.mmse = mmse;
    }

    public int getEtiv() {
        return etiv;
    }

    public void setEtiv(int etiv) {
        this.etiv = etiv;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public int getSeven() {
        return seven;
    }

    public void setSeven(int seven) {
        this.seven = seven;
    }

    public int getEight() {
        return eight;
    }

    public void setEight(int eight) {
        this.eight = eight;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public int getEleven() {
        return eleven;
    }

    public void setEleven(int eleven) {
        this.eleven = eleven;
    }

    public int getTwelve() {
        return twelve;
    }

    public void setTwelve(int twelve) {
        this.twelve = twelve;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getDiabetesPedigreeFunction() {
        return diabetesPedigreeFunction;
    }

    public void setDiabetesPedigreeFunction(float diabetesPedigreeFunction) {
        this.diabetesPedigreeFunction = diabetesPedigreeFunction;
    }

    public float getNwbv() {
        return nwbv;
    }

    public void setNwbv(float nwbv) {
        this.nwbv = nwbv;
    }

    public float getAsf() {
        return asf;
    }

    public void setAsf(float asf) {
        this.asf = asf;
    }

    public float getNine() {
        return nine;
    }

    public void setNine(float nine) {
        this.nine = nine;
    }
}
