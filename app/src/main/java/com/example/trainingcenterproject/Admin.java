package com.example.trainingcenterproject;

public class Admin extends User{
    public Admin() {
    }
    public Admin(String firstName, String secondName, String email, String password, byte[] photo) {
        super(firstName, secondName, email, password, photo);
    }

}
