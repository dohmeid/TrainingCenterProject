package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String databaseName = "USERS_DATABASE";
    public DataBaseHelper(Context context) {

        super(context, databaseName, null, 1);
        this.context = context;
    }
    ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, BLOB TEXT,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE COURSE(COURSE_NUM INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, SYMBOL TEXT,MAIN_TOPICS TEXT,  PREREQUISITES TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE AVAILABLE_COURSE(COURSE_NUM INTEGER  , SYMBOL TEXT,INSTRUCTOR_NAME TEXT,REG_DEADLINE TEXT, START_DATE TEXT,SCHEDULE TEXT,VENUE TEXT,FOREIGN  KEY (COURSE_NUM) REFERENCES COURSE(COURSE_NUM))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
       // MyDatabase.execSQL("drop Table if exists ADMINS");
        //MyDatabase.execSQL("drop Table if exists TRAINEES");
       // MyDatabase.execSQL("drop Table if exists INSTRUCTORS");
        MyDatabase.execSQL("drop Table if exists COURSE");
        MyDatabase.execSQL("drop Table if exists AVAILABLE_COURSE");
    }

    public Cursor adminCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        return cursor;
        /* if (cursor.getCount() > 0) {
            return true;
        }
        return false;*/
    }

    public Boolean traineeCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from TRAINEES where EMAIL = ? and PASSWORD = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public Boolean instructorCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from INSTRUCTORS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void insertAdmin(Admin a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Bitmap imageToStoreBitmap = a.getPhotos();
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
        imageInBytes = objectByteArrayOutputStream.toByteArray();
        contentValues.put("NAME1", a.getFirstName());
        contentValues.put("NAME2", a.getSecondName());
        contentValues.put("EMAIL", a.getEmail());
        contentValues.put("PASSWORD", a.getPassword());
        contentValues.put("PHOTO", imageInBytes);
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
    public void insertCourse(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = a.getPhoto();
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
        imageInBytes = objectByteArrayOutputStream.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", a.getTitle());
        contentValues.put("SYMBOL", a.getSymbol());
        contentValues.put("MAIN_TOPICS", a.getMain_topics());
        contentValues.put("PREREQUISITES", a.getPrereq());
        contentValues.put("PHOTO",imageInBytes);
       long checkIfInsert = MyDatabase.insert("COURSE", null, contentValues);
       if(checkIfInsert!=0){
           Toast.makeText(context,"Course added successfully",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Failed to add course",Toast.LENGTH_SHORT).show();
       }
    }

    public Cursor getAllCourses() {
        SQLiteDatabase MyDatabase = getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM COURSE", null);
    }

    public void updateCourse(Course a, String symbol){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Bitmap imageToStoreBitmap = a.getPhoto();
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
        imageInBytes = objectByteArrayOutputStream.toByteArray();
        contentValues.put("TITLE", a.getTitle());
        contentValues.put("SYMBOL", a.getSymbol());
        contentValues.put("MAIN_TOPICS", a.getMain_topics());
        contentValues.put("PREREQUISITES", a.getPrereq());
        contentValues.put("PHOTO",imageInBytes);

        long checkIfUpdate = MyDatabase.update("COURSE", contentValues, "SYMBOL=?", new String[]{symbol});
        if(checkIfUpdate!=0){
            Toast.makeText(context,"Course updated successfully",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Failed to update course",Toast.LENGTH_SHORT).show();
        }
        MyDatabase.close();

    }
    public Course searchCourse(String symbol){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + "COURSE" + " WHERE " +
                "SYMBOL" + " LIKE '%" + symbol + "%'";
        Cursor cursor = MyDatabase.rawQuery(selectQuery, null);
        Course s_course = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Cursor has at least one row, so it's safe to retrieve data
            do {
                // Extract the course details from the cursor
                @SuppressLint("Range") int num = cursor.getInt(cursor.getColumnIndex("COURSE_NUM"));

                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                @SuppressLint("Range") String symb = cursor.getString(cursor.getColumnIndex("SYMBOL"));
                @SuppressLint("Range") String maint = cursor.getString(cursor.getColumnIndex("MAIN_TOPICS"));
                @SuppressLint("Range") String pre = cursor.getString(cursor.getColumnIndex("PREREQUISITES"));
                @SuppressLint("Range") byte[] blobValue = cursor.getBlob(cursor.getColumnIndex("PHOTO"));
                Bitmap ph = BitmapFactory.decodeByteArray(blobValue, 0, blobValue.length);

                // Create a Course object
                s_course = new Course(num, title, symb, maint, pre, ph);

            } while (cursor.moveToNext());
        }else{
            Toast.makeText(context,"Course does not exist",Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database
        cursor.close();
        MyDatabase.close();

        return s_course;

    }

    public void deleteCourse(String symbol){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        long checkIfDel = MyDatabase.delete("COURSE", "SYMBOL = ?", new String[]{symbol});
        if(checkIfDel != 0){
            Toast.makeText(context,"Course deleted successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Course does not exist",Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor getCourseStudents(String symbol){
        String query =  " SELECT NAME1,NAME2,EMAIL" +
                        " FROM COURSE LEFT JOIN REGISTERED_COURSE rs" +
                        " ON COURSE.COURSE_NUM = rs.COURSE_NUM " +
                        " LEFT JOIN TRAINEE ON rs.trainee_id = TRAINEE.trainee_id WHERE " +
                        " COURSE.SYMBOL = '"+symbol+"'" ;

        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(MyDatabase!=null){
            cursor = MyDatabase.rawQuery(query,null);
        }
        return cursor;
    }

    public void insertToAvaCourses(Course a) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SYMBOL", a.getSymbol());
        contentValues.put("INSTRUCTOR_NAME", a.getInstructor_name());
        contentValues.put("REG_DEADLINE", a.getDeadline());
        contentValues.put("START_DATE",a.getStart_date());
        contentValues.put("SCHEDULE", a.getSchedule());
        contentValues.put("VENUE", a.getVenue());
        long checkIfInsert = MyDatabase.insert("AVAILABLE_COURSE", null, contentValues);
        if(checkIfInsert!=0){
            Toast.makeText(context,"Course is now available for registration",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
    }


    public Cursor getOfferingHistory(String symbol){
        String query =  "SELECT COURSE_NUM,TITLE, START_DATE,COUNT(EMAIL),INSTRUCTOR_NAME,VENUE" +
                " FROM COURSE LEFT JOIN AVAILABLE_COURSE ac" +
                " ON COURSE.COURSE_NUM = ac.COURSE_NUM" +
                " LEFT JOIN REGISTERED rc ON COURSE.COURSE_NUM = rc.COURSE_NUM"+
                " LEFT JOIN TRAINEE  ON TRAINEE.EMAIL = rc.EMAIL"+" WHERE " +
                "COURSE.SYMBOL = '"+symbol+"'" ;

        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(MyDatabase!=null){
            cursor = MyDatabase.rawQuery(query,null);
        }
        return cursor;
    }










}
