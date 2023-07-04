package com.example.trainingcenterproject;

import static com.example.trainingcenterproject.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button sign_in;
    Button sign_up;
    CheckBox rememberMe;
    DataBaseHelper dataBaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPass);
        sign_in = findViewById(R.id.button_signin);
        sign_up = findViewById(R.id.button_signup);
        rememberMe = findViewById(id.checkBox);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //to get stored data
        String mail = sharedPreferences.getString("email", "");
        email.setText(mail);

        sign_in.setOnClickListener(view -> checkData());

        sign_up.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, UserRoleUI.class);
            startActivity(i);
            finish(); //close this activity
        });

    }

    void checkData() {
        boolean b1 = checkEmail();
        boolean b2 = checkPassword();
        if (b1 && b2) {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            dataBaseHelper = new DataBaseHelper(this);

            boolean b3 = dataBaseHelper.adminCheckEmailPassword(em, pass);
            boolean b4 = dataBaseHelper.traineeCheckEmailPassword(em, pass);
            boolean b5 = dataBaseHelper.instructorCheckEmailPassword(em, pass);

            if (b3||b4||b5){
                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                if (rememberMe.isChecked()) {
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                } else {
                    editor.putString("email", "");
                }

                if(b3)
                    startActivity(new Intent(MainActivity.this, AdminHomeView.class));
                else if(b4)
                    startActivity(new Intent(MainActivity.this, TraineeHomeView.class));
                else {
                    //Instructor instructor = dataBaseHelper.getInstructorData(em);
                    Intent i = new Intent(MainActivity.this, InstructorHome.class);
                    //i.putExtra("instructor", instructor); //send the new Instructor object to the next intent
                    i.putExtra("email", em);
                    startActivity(i);
                }
                finish(); //close this activity
            }
            else {
                Toast.makeText(MainActivity.this, "Login Failed!, wrong password/email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean checkEmail(){
        String mail = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mail.isEmpty() || mail.trim().isEmpty()) {
            email.setError("This field is empty!");
            return false;
        }
        else if (!mail.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    boolean checkPassword() {
        String str = password.getText().toString();
        if (str.isEmpty() || str.trim().isEmpty()) {
            password.setError("This field is empty!");
            return false;
        }
        else if (str.length() < 8) {
            password.setError("Password must be at least 8 characters long!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

}