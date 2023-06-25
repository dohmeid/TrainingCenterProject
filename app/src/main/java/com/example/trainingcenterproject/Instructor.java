package com.example.trainingcenterproject;

import java.util.ArrayList;

public class Instructor extends User {

    private String mobile_number;
    private String address;
    private String specialization;
    private String degree;
    ArrayList<String> courses = new ArrayList<>();

    public Instructor(){
    }

    public Instructor(String firstName, String secondName, String email, String password, String photo, String mobile_number, String address, String specialization, String degree, ArrayList<String> courses) {
        super(firstName, secondName, email, password, photo);
        this.mobile_number = mobile_number;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
        this.courses = courses;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return super.toString() + "Instructor{" +
                "mobile_number='" + mobile_number + '\'' +
                ", address='" + address + '\'' +
                ", specialization='" + specialization + '\'' +
                ", degree='" + degree + '\'' +
                ", courses=" + courses +
                '}';
    }
}
