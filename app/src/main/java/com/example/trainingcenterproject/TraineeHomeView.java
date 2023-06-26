package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class TraineeHomeView extends AppCompatActivity {
    ImageView traineeImage;
    TextView userName, userEmail;
    ImageButton search, studiedCourses, coursesHist, viewProfile, logOut;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_home_view);

        traineeImage = findViewById(R.id.traineeImage);
        userName = findViewById(R.id.TraineeName);
        userEmail = findViewById(R.id.traineeEmail);
        search = findViewById(R.id.searchButton);
        studiedCourses = findViewById(R.id.studiedCBtn);
        coursesHist = findViewById(R.id.cHisBtn);
        viewProfile = findViewById(R.id.viewProfBtn);
        logOut = findViewById(R.id.logOutBtn);

        //Intent data = getIntent();
        //SharedPreferences settings = getSharedPreferences("userSignUp", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        //String email = settings.getString("Email", "noValue");
          //String email =data.getStringExtra("email");
        String email = sharedPreferences.getString("email", "");

          DataBaseHelper dataBaseHelper =new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);
          Cursor customersCursor = dataBaseHelper.getTrainees(email);
//          System.out.println(customersCursor.getString(1) + " " + customersCursor.getString(2));
//          System.out.println(customersCursor.getString(0));
        customersCursor.moveToFirst();
        userName.setText(customersCursor.getString(1) + " " + customersCursor.getString(2) );
        userEmail.setText(customersCursor.getString(0));

        /* Showing the image giving an error relates to ACTION_OPEN_DOCUMENT permission to get to storage */
//        Uri uri;
//        uri = Uri.parse(customersCursor.getString(4));
//
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//            traineeImage.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        search.setOnClickListener(view -> {
            startActivity(new Intent(TraineeHomeView.this, TraineeCourseSeach.class));
        });

        studiedCourses.setOnClickListener(view -> {
            startActivity(new Intent(TraineeHomeView.this, TraineeStudiedCourses.class));
        });

        coursesHist.setOnClickListener(view -> {
            startActivity(new Intent(TraineeHomeView.this, CoursesHistory.class));
        });

        viewProfile.setOnClickListener(view -> {
            startActivity(new Intent(TraineeHomeView.this, TraineeProfile.class));
        });

        logOut.setOnClickListener(view -> {
            startActivity(new Intent(TraineeHomeView.this, MainActivity.class));
        });


    }

}