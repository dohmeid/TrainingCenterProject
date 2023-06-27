package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TraineeCourseSeach extends AppCompatActivity {

    EditText courseName;
    Button search;
    LinearLayout courseResult;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_course_seach);

        courseName = findViewById(R.id.courseName);
        search = findViewById(R.id.searchBtn);
        courseResult = findViewById(R.id.resultsLayout);

        search.setOnClickListener(view -> {
            if(courseName.getText().toString().isEmpty()){
                courseName.setError("Please insert course name.");
            }
            else{
                DataBaseHelper dataBaseHelper =new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);
                Cursor allCoursesCurser = dataBaseHelper.getAllCourses();
                courseResult.removeAllViews();

                while (allCoursesCurser.moveToNext()){
                    if(allCoursesCurser.getString(1).contains(courseName.getText().toString())){
                        TextView textView =new TextView(TraineeCourseSeach.this);
                        textView.setText( "Id: "+ allCoursesCurser.getString(0) +
                                "\nTitle: "+ allCoursesCurser.getString(1) +
                                "\nTopic: "+ allCoursesCurser.getString(2) +
                                "\nPrerequisites: "+ allCoursesCurser.getString(3) +
                                "\nInstructor: "+ allCoursesCurser.getString(4) +
                                "\nDeadline: "+ allCoursesCurser.getString(5) +
                                "\nStart Date: "+ allCoursesCurser.getString(6) +
                                "\nSchedule: "+ allCoursesCurser.getString(7) +
                                "\nVenue: "+ allCoursesCurser.getString(8) + "\n\n" );
                        courseResult.addView(textView);
                    }
                }

            }
        });
    }
}