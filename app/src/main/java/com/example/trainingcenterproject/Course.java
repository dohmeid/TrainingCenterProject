package com.example.trainingcenterproject;

public class Course {

    private int number;
    private String title;
    private String mainTopics;
    private String prerequisites;
    private byte[]  photo;
    private String instructorName;
    private String registrationDeadline;
    private String startDate;
    private String schedule;
    private String venue;


    public Course(){

    }
    public Course(int number, String title, String mainTopics, String prerequisites, byte[] photo,
                  String instructorName, String registrationDeadline, String startDate, String schedule, String venue) {
        this.number = number;
        this.title = title;
        this.mainTopics = mainTopics;
        this.prerequisites = prerequisites;
        this.photo = photo;
        this.instructorName = instructorName;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTopics() {
        return mainTopics;
    }

    public void setMainTopics(String mainTopics) {
        this.mainTopics = mainTopics;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
}
