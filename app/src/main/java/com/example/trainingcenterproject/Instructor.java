package com.example.trainingcenterproject;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;

public class Instructor extends User implements Parcelable {

    private String mobile_number;
    private String address;
    private String specialization;
    private String degree;
    ArrayList<String> courses = new ArrayList<>();

    public Instructor(){
    }

    public Instructor(String firstName, String secondName, String email, String password, byte[]  photo, String mobile_number, String address, String specialization, String degree, ArrayList<String> courses) {
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


    //Parcelable interface methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(this.getFirstName());
        dest.writeString(this.getSecondName());
        dest.writeString(this.getEmail());
        dest.writeString(this.getPassword());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dest.writeBlob(this.getPhoto());
        }
        dest.writeString(this.mobile_number);
        dest.writeString(this.address);
        dest.writeString(this.specialization);
        dest.writeString(this.degree);
        dest.writeStringList(this.courses);
    }

    protected Instructor(Parcel in) {
        this.setFirstName(in.readString());
        this.setSecondName(in.readString());
        this.setEmail(in.readString());
        this.setPassword(in.readString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.setPhoto(in.readBlob());
        }
        mobile_number = in.readString();
        address = in.readString();
        specialization = in.readString();
        degree = in.readString();
        courses = in.createStringArrayList();
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

}
