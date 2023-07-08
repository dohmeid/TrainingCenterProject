package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserRoleUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_role_ui);

        Button admin = findViewById(R.id.buttonAdmin);
        Button trainee = findViewById(R.id.buttonTrainee);
        Button instructor = findViewById(R.id.buttonInstructor);
        Button back = findViewById(R.id.buttonBack);

        admin.setOnClickListener(view -> {
            Intent i = new Intent(UserRoleUI.this, AdminSignUp.class);
            startActivity(i);
            finish(); //close this activity
        });

        trainee.setOnClickListener(view -> {
            Intent i = new Intent(UserRoleUI.this, TraineeSignUp.class);
            startActivity(i);
            finish(); //close this activity
        });

        instructor.setOnClickListener(view -> {
            Intent i = new Intent(UserRoleUI.this, InstructorSignUp.class);
            startActivity(i);
            finish(); //close this activity
        });

        back.setOnClickListener(view -> {
            Intent i = new Intent(UserRoleUI.this, MainActivity.class);
            startActivity(i);
            finish(); //close this activity
        });
    }
}