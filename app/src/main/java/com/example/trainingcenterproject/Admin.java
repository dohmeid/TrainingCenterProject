package com.example.trainingcenterproject;

import android.graphics.Bitmap;

public class Admin extends User{
    public Admin() {
    }
    public Admin(String firstName, String secondName, String email, String password, Bitmap photo) {
        super(firstName, secondName, email, password,photo);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
