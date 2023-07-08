package com.example.trainingcenterproject;

import java.util.Date;

public class Course {
    private int courseNumber;
    private String courseTitle;
    private String courseMainTopics;
    private String prerequisites;
    private String instructorName;
    private Date registrationDeadline;
    private Date courseStartDate;
    private String courseSchedule;
    private String venue;

    public Course(int courseNumber, String courseTitle, String courseMainTopics, String prerequisites, String instructorName, Date registrationDeadline, Date courseStartDate, String courseSchedule, String venue) {
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseMainTopics = courseMainTopics;
        this.prerequisites = prerequisites;
        this.instructorName = instructorName;
        this.registrationDeadline = registrationDeadline;
        this.courseStartDate = courseStartDate;
        this.courseSchedule = courseSchedule;
        this.venue = venue;
    }
    public Course(String courseTitle, String courseMainTopics, String prerequisites, String instructorName, Date registrationDeadline, Date courseStartDate, String courseSchedule, String venue) {
        this.courseTitle = courseTitle;
        this.courseMainTopics = courseMainTopics;
        this.prerequisites = prerequisites;
        this.instructorName = instructorName;
        this.registrationDeadline = registrationDeadline;
        this.courseStartDate = courseStartDate;
        this.courseSchedule = courseSchedule;
        this.venue = venue;
    }

    public Course(String courseTitle, String courseMainTopics, String prerequisites){
        this.courseTitle = courseTitle;
        this.courseMainTopics = courseMainTopics;
        this.prerequisites = prerequisites;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseMainTopics() {
        return courseMainTopics;
    }

    public void setCourseMainTopics(String courseMainTopics) {
        this.courseMainTopics = courseMainTopics;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(String courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
