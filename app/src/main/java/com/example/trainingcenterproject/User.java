package com.example.trainingcenterproject;

import android.graphics.Bitmap;

public class User {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String photo;
    private Bitmap photos;

    public User() {
    }
    public User(String firstName, String secondName, String email, String password,String photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }
    public User(String firstName, String secondName, String email, String password,Bitmap photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.photos = photo;
    }

    public Bitmap getPhotos() {
        return photos;
    }

    public void setPhotos(Bitmap photos) {
        this.photos = photos;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

   public void setPhoto(String photo) {
       this.photo = photo;
   }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
