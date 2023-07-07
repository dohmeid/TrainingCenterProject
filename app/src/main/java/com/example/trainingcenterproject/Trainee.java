package com.example.trainingcenterproject;

import android.graphics.Bitmap;

public class Trainee extends User{

    private String mobile_number;
    private String address;

    public Trainee(){

    }
    public Trainee(String firstName, String secondName, String email, String password, String photo, String mobile_number, String address) {
        super(firstName, secondName, email, password, photo);
        this.mobile_number = mobile_number;
        this.address = address;
    }
    public Trainee(String firstName, String secondName, String email, String password, Bitmap photo, String mobile_number, String address) {
        super(firstName, secondName, email, password, photo);
        this.mobile_number = mobile_number;
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString() + " Trainee{" +
                "mobile_number='" + mobile_number + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

