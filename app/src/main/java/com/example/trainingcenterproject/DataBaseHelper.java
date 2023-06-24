package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "USERS_DATABASE";
    public DataBaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO TEXT,MOBILE_NUM TEXT,ADDRESS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists ADMINS");
        MyDatabase.execSQL("drop Table if exists TRAINEES");
        MyDatabase.execSQL("drop Table if exists INSTRUCTORS");
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor1 = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor1.getCount() > 0) {
            return true;
        }
        Cursor cursor2 = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor2.getCount() > 0) {
            return true;
        }
        Cursor cursor3 = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor3.getCount() > 0) {
            return true;
        }
        return false;
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

    public Cursor getAllInstructors() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM INSTRUCTORS", null);
    }


}
