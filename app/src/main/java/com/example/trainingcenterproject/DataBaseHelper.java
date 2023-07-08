package com.example.trainingcenterproject;

import static android.database.sqlite.SQLiteDatabase.openDatabase;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "APPLICATION_DATABASE";
    public DataBaseHelper(Context context) {
        super(context, databaseName, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, MAIN_TOPICS TEXT, PREREQUISITES TEXT)");
        MyDatabase.execSQL("CREATE TABLE AVAILABLE_COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, MAIN_TOPICS TEXT, PREREQUISITES TEXT, INSTRUCTOR TEXT,DEADLINE DATE,START_DATE DATE, " +
                "SCHEDULE TEXT, VENUE TEXT)");
        MyDatabase.execSQL("CREATE TABLE PENDING_COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, COURSE_ID INTEGER, USER_EMAIL TEXT)");
        MyDatabase.execSQL("CREATE TABLE REGISTERED_COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, COURSE_ID INTEGER, USER_EMAIL TEXT)");
        MyDatabase.execSQL("CREATE TABLE COMPLETED_COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, COURSE_ID INTEGER, TITLE TEXT, USER_EMAIL TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {

        MyDatabase = openDatabase(databaseName, null, SQLiteDatabase.OPEN_READWRITE);
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS COURSES(ID INT PRIMARY KEY AUTOINCREMENT, TITLE TEXT, MAIN_TOPICS TEXT, PREREQUISITES TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS AVAILABLE_COURSES(ID INT PRIMARY KEY AUTOINCREMENT, TITLE TEXT, MAIN_TOPICS TEXT, PREREQUISITES TEXT, INSTRUCTOR TEXT,DEADLINE DATE,START_DATE DATE,"+
                                "SCHEDULE TEXT, VENUE TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS PENDING_COURSES(ID INT PRIMARY KEY AUTOINCREMENT, COURSE_ID INT, USER_EMAIL TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS REGISTERED_COURSES(ID INT PRIMARY KEY AUTOINCREMENT, COURSE_ID INT, USER_EMAIL TEXT)");
        MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS COMPLETED_COURSES(ID INT PRIMARY KEY AUTOINCREMENT, COURSE_ID INT, TITLE TEXT, USER_EMAIL TEXT)");


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

    public boolean checkCompletion(String email, String courseName){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor1 = MyDatabase.rawQuery("Select * from COMPLETED_COURSES where USER_EMAIL = ? AND TITLE = ?", new String[]{email, courseName});
        if (cursor1.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void updateTrainee(String email, StringBuilder fName, String lName, String password, byte[] photo, String phone, String address) {
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

    public Cursor getAvailableCourses() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM AVAILABLE_COURSES", null);
    }

    public Cursor getAvailableCourse(String Cid) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM AVAILABLE_COURSES where ID = ?", new String[]{Cid});
    }

    public Cursor getCoursesByLatest() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM COURSES ORDER BY ID DESC", null);
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

    public Cursor getTrainee(String email) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM TRAINEES WHERE EMAIL = '" + email + "'" , null);
    }

    public void insertTraineeCourse(int Cid, String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_ID", Cid);
        contentValues.put("USER_EMAIL", email);

        MyDatabase.insert("REGISTERED_COURSES", null, contentValues);
    }

    public Cursor getCompletedCourses(String email){
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM COURSES C, COMPLETED_COURSES RC WHERE RC.USER_EMAIL = '" + email + "' AND RC.COURSE_ID = C.ID" , null);
    }

    public void insertCompletedCourse(String Cname, String email, int Cid){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_ID", Cid);
        contentValues.put("TITLE", Cname);
        contentValues.put("USER_EMAIL", email);

        MyDatabase.insert("COMPLETED_COURSES", null, contentValues);
    }

    public Cursor getTraineeCourses(String email) {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM AVAILABLE_COURSES C, REGISTERED_COURSES RC WHERE RC.USER_EMAIL = '" + email + "' AND RC.COURSE_ID = C.ID" , null);
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

    public void insertAvailableCourse(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", a.getCourseTitle());
        contentValues.put("MAIN_TOPICS", a.getCourseMainTopics());
        contentValues.put("PREREQUISITES", a.getPrerequisites());
        contentValues.put("INSTRUCTOR", a.getInstructorName());
        contentValues.put("DEADLINE", a.getRegistrationDeadline().getTime());
        contentValues.put("START_DATE", a.getCourseStartDate().getTime());
        contentValues.put("SCHEDULE", a.getCourseSchedule());
        contentValues.put("VENUE", a.getVenue());
        MyDatabase.insert("AVAILABLE_COURSES", null, contentValues);
    }

    public void insertCourse(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", a.getCourseTitle());
        contentValues.put("MAIN_TOPICS", a.getCourseMainTopics());
        contentValues.put("PREREQUISITES", a.getPrerequisites());

        MyDatabase.insert("COURSES", null, contentValues);
    }

    public Cursor getAllInstructors() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM INSTRUCTORS", null);
    }


}
