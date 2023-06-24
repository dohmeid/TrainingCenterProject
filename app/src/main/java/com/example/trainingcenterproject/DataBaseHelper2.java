package com.example.trainingcenterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper2 extends SQLiteOpenHelper {

    public static final String databaseName = "SignIn.db";
    public DataBaseHelper2(Context context) {
        super(context, "SignIn.db", null, 1);
    }

    public DataBaseHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists users");
    }

    public void insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);
        MyDatabase.insert("USERS", null, contentValues);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM USERS", null);
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from USERS where EMAIL = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from USERS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

}
