package com.example.trainingcenterproject;

public class Admin extends User{
    public Admin() {
    }
    public Admin(String firstName, String secondName, String email, String password, String photo) {
        super(firstName, secondName, email, password, photo);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
