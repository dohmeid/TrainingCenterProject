package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainingcenterproject.ui.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "HELLO2";
    public DataBaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT, SPECIALIZATION TEXT ,DEGREE TEXT ,COURSES TEXT)");
        MyDatabase.execSQL("CREATE TABLE COURSES(NUMBER INTEGER PRIMARY KEY, TITLE TEXT, TOPICS TEXT, PREREQUISITES TEXT, PHOTO BLOB,INSTRUCTOR_NAME TEXT,DEADLINE TEXT, START_DATE TEXT ,SCHEDULE TEXT ,VENUE TEXT)");
        MyDatabase.execSQL("CREATE TABLE REGISTERED_COURSES( NUMBER2 INTEGER PRIMARY KEY, COURSE_TITLE TEXT, STUDENT_NAME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("drop Table if exists ADMINS");
        MyDatabase.execSQL("drop Table if exists TRAINEES");
        MyDatabase.execSQL("drop Table if exists INSTRUCTORS");
        MyDatabase.execSQL("drop Table if exists COURSES");
        MyDatabase.execSQL("drop Table if exists REGISTERED_COURSES");
    }

    //function to check that a password is unique
    public boolean isUniquePassword(String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from ADMINS where PASSWORD = ?", new String[]{password});
        if (cursor.getCount() > 0) {
            return false;
        }
        Cursor cursor2 = MyDatabase.rawQuery("Select * from TRAINEES where PASSWORD = ?", new String[]{password});
        if (cursor2.getCount() > 0) {
            return false;
        }
        Cursor cursor3 = MyDatabase.rawQuery("Select * from INSTRUCTORS where PASSWORD = ?", new String[]{password});
        return cursor3.getCount() <= 0;
    }

    public boolean isUniqueEmail(String mail){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ?", new String[]{mail});
        if (cursor.getCount() > 0) {
            return false;
        }
        Cursor cursor2 = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ?", new String[]{mail});
        if (cursor2.getCount() > 0) {
            return false;
        }
        Cursor cursor3 = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ?", new String[]{mail});
        return cursor3.getCount() <= 0;
    }

    public Boolean adminCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public Boolean traineeCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public Boolean instructorCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public void insertAdmin(Admin a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME1", a.getFirstName());
        contentValues.put("NAME2", a.getSecondName());
        contentValues.put("EMAIL", a.getEmail());
        contentValues.put("PASSWORD", a.getPassword());
        contentValues.put("PHOTO", a.getPhoto());
        MyDatabase.insert("ADMINS", null, contentValues);
    }

    public Cursor getAllAdmins() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM ADMINS", null);
    }


    public void insertTrainee(Trainee a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME1", a.getFirstName());
        contentValues.put("NAME2", a.getSecondName());
        contentValues.put("EMAIL", a.getEmail());
        contentValues.put("PASSWORD", a.getPassword());
        contentValues.put("PHOTO", a.getPhoto());
        contentValues.put("MOBILE_NUM", a.getMobile_number());
        contentValues.put("ADDRESS", a.getAddress());
        MyDatabase.insert("TRAINEES", null, contentValues);
    }

    public Cursor getAllTrainees() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM TRAINEES", null);
    }




    //instructor methods
    public void insertInstructor(Instructor a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME1", a.getFirstName());
        contentValues.put("NAME2", a.getSecondName());
        contentValues.put("EMAIL", a.getEmail());
        contentValues.put("PASSWORD", a.getPassword());
        contentValues.put("PHOTO", a.getPhoto());
        contentValues.put("MOBILE_NUM", a.getMobile_number());
        contentValues.put("ADDRESS", a.getAddress());
        contentValues.put("SPECIALIZATION", a.getSpecialization());
        contentValues.put("DEGREE", a.getDegree());

        ArrayList<String> c = a.getCourses();
        String courses = "";
        for (int i = 0; i < c.size(); i++) {
            courses += c.get(i);
            if(i != c.size()-1 )
                courses += ",";
        }
        contentValues.put("COURSES", courses);
        MyDatabase.insert("INSTRUCTORS", null, contentValues);
    }

    public Cursor getAllInstructors() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM INSTRUCTORS", null);
    }

    public Instructor getInstructorData(String email){ //used in main activity
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ?", new String[]{email});
        cursor.moveToFirst();
        String mail = cursor.getString(0);
        String name1 = cursor.getString(1);
        String name2 = cursor.getString(2);
        String pass = cursor.getString(3);

        byte[] photo = cursor.getBlob(4);

        String number = cursor.getString(5);
        String address = cursor.getString(6);
        String spec = cursor.getString(7);
        String degree = cursor.getString(8);

        String courses = cursor.getString(9);

        //to extract courses
        ArrayList<String> c = new ArrayList<>();
        String[] splitArray = courses.split(",");
        for (String item : splitArray) {
            c.add(item);
        }

        return new Instructor(name1,name2,mail,pass,photo,number,address,spec,degree,c);
        //return new Instructor("name1","name2","mail","pass",photo,"number","address","spec","degree",c);
    }

    public void updateInstructorData(String oldEmail, Instructor newInstructorData ) {

        if (oldEmail != "" && newInstructorData != null) {
            // calling a method to get writable database.
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            // on below line we are passing all values along with its key and value pair.
            values.put("EMAIL", newInstructorData.getEmail());
            values.put("NAME1", newInstructorData.getFirstName());
            values.put("NAME2", newInstructorData.getSecondName());
            values.put("PHOTO", newInstructorData.getPhoto());
            values.put("MOBILE_NUM", newInstructorData.getMobile_number());
            values.put("ADDRESS", newInstructorData.getAddress());
            values.put("SPECIALIZATION", newInstructorData.getAddress());
            values.put("DEGREE", newInstructorData.getDegree());

            db.update("INSTRUCTORS", values, "EMAIL=?", new String[]{oldEmail});
            db.close();
            //MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT,
            // NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT, SPECIALIZATION TEXT ,DEGREE TEXT ,COURSES TEXT)");
        }
    }

    public void insertCourse(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NUMBER", a.getNumber());
        contentValues.put("TITLE", a.getTitle());
        contentValues.put("TOPICS", a.getMainTopics());
        contentValues.put("PREREQUISITES", a.getPrerequisites());
        contentValues.put("PHOTO", a.getPhoto());
        contentValues.put("INSTRUCTOR_NAME", a.getInstructorName());
        contentValues.put("DEADLINE", a.getRegistrationDeadline());
        contentValues.put("START_DATE", a.getStartDate());
        contentValues.put("SCHEDULE", a.getSchedule());
        contentValues.put("VENUE", a.getVenue());
        MyDatabase.insert("COURSES", null, contentValues);
    }

    public Cursor getAllCourse() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM COURSES", null);
    }

    public  ArrayList<Course> getInstructorCourses(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor1 = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ?", new String[]{email});
        cursor1.moveToFirst();
        String name1 = cursor1.getString(1);
        String name2 = cursor1.getString(2);
        String name = name1 + " " + name2;

        ArrayList<Course> courses = new ArrayList<>();
        Cursor cursor = MyDatabase.rawQuery("Select * from COURSES where INSTRUCTOR_NAME = ?", new String[]{name});
        while (cursor.moveToNext()) {
            int number = cursor.getInt(cursor.getColumnIndexOrThrow("NUMBER"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String topics = cursor.getString(cursor.getColumnIndexOrThrow("TOPICS"));
            String prerequisites = cursor.getString(cursor.getColumnIndexOrThrow("PREREQUISITES"));
            byte[] photo = cursor.getBlob(cursor.getColumnIndexOrThrow("PHOTO"));
            String instructor = cursor.getString(cursor.getColumnIndexOrThrow("INSTRUCTOR_NAME"));
            String deadline = cursor.getString(cursor.getColumnIndexOrThrow("DEADLINE"));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow("START_DATE"));
            String schedule = cursor.getString(cursor.getColumnIndexOrThrow("SCHEDULE"));
            String venue = cursor.getString(cursor.getColumnIndexOrThrow("VENUE"));

            Course course = new Course(number, title, topics, prerequisites, photo, instructor, deadline, startDate, schedule, venue);
            courses.add(course);
        }

        cursor.close();
        return courses;
    }


    public void insertRegisteredCourse(int num , String course_name,String student_name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NUMBER2",num);
        contentValues.put("COURSE_TITLE",course_name);
        contentValues.put("STUDENT_NAME", student_name);
        MyDatabase.insert("REGISTERED_COURSES", null, contentValues);
    }

    public  ArrayList<String> getCourseStudents(String courseTitle) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ArrayList<String> students = new ArrayList<>();
        Cursor cursor = MyDatabase.rawQuery("Select * from REGISTERED_COURSES where COURSE_TITLE = ?", new String[]{courseTitle});
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("STUDENT_NAME"));
            students.add(name);
        }
        cursor.close();
        return students;
    }

}
