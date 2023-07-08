package com.example.trainingcenterproject;

import android.graphics.Bitmap;

public class Course {
    private int course_num;
    private String title;
    private String symbol;
    private String main_topics;
    private String prerequisites;
    private Bitmap photo;
    private String instructor_name;
    private String deadline;
    private String start_date;
    private String schedule;
    private String venue;

    public Course(){
    }

    public Course(int course_num, String title,String symbol) {
        this.course_num = course_num;
        this.title = title;
        this.symbol = symbol;
    }

    public Course(int course_num, String title,String symbol, String main_topics, String prerequisites, Bitmap photo) {
        this.course_num = course_num;
        this.title = title;
        this.symbol = symbol;
        this.main_topics = main_topics;
        this.prerequisites = prerequisites;
        this.photo = photo;
    }
    public Course(String symbol, String instructor_name, String deadline, String start_date, String schedule, String venue){
        this.symbol = symbol;
        this.instructor_name = instructor_name;
        this.deadline = deadline;
        this.start_date = start_date;
        this.schedule = schedule;
        this.venue = venue;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }


    public int getCourse_num() {
        return course_num;
    }

    public void setCourse_num(int course_num) {
        this.course_num = course_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain_topics() {
        return main_topics;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setMain_topics(String main_topics) {
        this.main_topics = main_topics;
    }

    public String getPrereq() {
        return prerequisites;
    }

    public void setPrereq(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_num=" + course_num +
                ", title='" + title + '\'' +
                ", symbol='" + symbol + '\'' +
                ", main_topics='" + main_topics + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
