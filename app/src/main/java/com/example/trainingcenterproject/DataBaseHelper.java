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
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String databaseName = "HELLO2";
    public DataBaseHelper(Context context) {

        super(context, databaseName, null, 1);
        this.context = context;
    }
    ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) { //BLOB -> stores the image data as a BLOB (Binary Large Object).
        MyDatabase.execSQL("CREATE TABLE ADMINS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE TRAINEES(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT)");
        MyDatabase.execSQL("CREATE TABLE INSTRUCTORS(EMAIL TEXT PRIMARY KEY, NAME1 TEXT, NAME2 TEXT, PASSWORD TEXT, PHOTO BLOB,MOBILE_NUM TEXT,ADDRESS TEXT, SPECIALIZATION TEXT ,DEGREE TEXT ,COURSES TEXT)");
        MyDatabase.execSQL("CREATE TABLE COURSE(COURSE_NUM INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, SYMBOL TEXT,MAIN_TOPICS TEXT,  PREREQUISITES TEXT, PHOTO BLOB)");
        MyDatabase.execSQL("CREATE TABLE AVAILABLE_COURSE(AVAILABLE_COURSE_NUM INTEGER PRIMARY KEY AUTOINCREMENT, COURSE_NUM INTEGER, SYMBOL TEXT,INSTRUCTOR_NAME TEXT,REG_DEADLINE TEXT, START_DATE TEXT,SCHEDULE TEXT,VENUE TEXT,FOREIGN  KEY (COURSE_NUM) REFERENCES COURSE(COURSE_NUM))");

        MyDatabase.execSQL("CREATE TABLE REGISTERED_COURSES(NUMBER2 INTEGER PRIMARY KEY, COURSE_TITLE TEXT, STUDENT_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists ADMINS");
        MyDatabase.execSQL("drop Table if exists TRAINEES");
        MyDatabase.execSQL("drop Table if exists INSTRUCTORS");
        MyDatabase.execSQL("drop Table if exists COURSE");
        MyDatabase.execSQL("drop Table if exists AVAILABLE_COURSE");
        MyDatabase.execSQL("drop Table if exists REGISTERED_COURSES");
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

    public Cursor adminCheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from ADMINS where EMAIL = ? and PASSWORD = ?", new String[]{email, password});
        return cursor;
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



    //Instructor methods
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
        }
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
        cursor1.close();

        ArrayList<Course> courses = new ArrayList<>();
        Cursor cursor = MyDatabase.rawQuery("Select * from AVAILABLE_COURSE where INSTRUCTOR_NAME = ?", new String[]{name});
        while (cursor.moveToNext()) {
            String symbol = cursor.getString(cursor.getColumnIndexOrThrow("SYMBOL"));
            Cursor cursor2 = MyDatabase.rawQuery("Select * from COURSE where SYMBOL = ?", new String[]{symbol});
            while(cursor2.moveToNext()){
                int number = cursor2.getInt(cursor2.getColumnIndexOrThrow("COURSE_NUM"));
                String title = cursor2.getString(cursor2.getColumnIndexOrThrow("TITLE"));
                Course course = new Course(number, title,symbol);
                courses.add(course);
            }
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
    public  ArrayList<String> getCourseStudents2(String courseTitle) {
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
