package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "USERS_DATABASE";
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE COURSES(ID INT PRIMARY KEY AUTOINCREMENT, TITLE TEXT, MAIN_TOPICS TEXT, PREREQUISITES TEXT, INSTRUCTOR TEXT,DEADLINE DATE,START_DATE DATE, " +
                "SCHEDULE TEXT, VENUE TEXT)");
        MyDatabase.execSQL("CREATE TABLE REGISTERED_COURSES(ID INT PRIMARY KEY AUTOINCREMENT, COURSE_ID INT, USER_EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists ADMINS");
        MyDatabase.execSQL("drop Table if exists TRAINEES");
        MyDatabase.execSQL("drop Table if exists INSTRUCTORS");
    }

    public int checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor1 = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor1.getCount() > 0) {
            return 1;
        }
        @SuppressLint("Recycle") Cursor cursor2 = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor2.getCount() > 0) {
            return 2;
        }
        @SuppressLint("Recycle") Cursor cursor3 = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor3.getCount() > 0) {
            return 3;
        }
        return -1;
    }

    public boolean checkEmails(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor1 = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ?", new String[]{email});
        if (cursor1.getCount() > 0) {
            return true;
        }
        @SuppressLint("Recycle") Cursor cursor2 = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ?", new String[]{email});
        if (cursor2.getCount() > 0) {
            return true;
        }
        @SuppressLint("Recycle") Cursor cursor3 = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ?", new String[]{email});
        if (cursor3.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void updateTrainee(String email, StringBuilder fName, String lName, String password, String photo, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TRAINEES " +
                "SET NAME1 = '" + fName +
                "', NAME2 = '" + lName + "', PASSWORD = '" + password + "', MOBILE_NUM = '" + phone + "'"
                + ",  ADDRESS = '" + address + "' ,  PHOTO = '" + photo + "'WHERE EMAIL = '" + email + "'");
        db.close();
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
        return MyDatabase.rawQuery("SELECT * FROM ADMIN", null);
    }

    public Cursor getAllCourses() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM COURSES", null);
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

    public Cursor getTrainees(String email) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM TRAINEES WHERE EMAIL = '" + email + "'" , null);
    }

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
        MyDatabase.insert("INSTRUCTORS", null, contentValues);
    }

    public void insertCourse(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", a.getCourseTitle());
        contentValues.put("MAIN_TOPICS", a.getCourseMainTopics());
        contentValues.put("PREREQUISITES", a.getPrerequisites());
        contentValues.put("INSTRUCTOR", a.getInstructorName());
        contentValues.put("DEADLINE", a.getRegistrationDeadline());
        contentValues.put("START_DATE", a.getCourseStartDate());
        contentValues.put("SCHEDULE", a.getCourseSchedule());
        contentValues.put("VENUE", a.getVenue());
        MyDatabase.insert("COURSES", null, contentValues);
    }

    public Cursor getAllInstructors() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM INSTRUCTORS", null);
    }


}
