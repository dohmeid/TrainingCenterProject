package com.example.trainingcenterproject;

import static com.example.trainingcenterproject.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UserRoleUI.class);
                startActivity(i);
                finish(); //close this activity
            }
        });

    }

    void checkData() {
        boolean b1 = checkEmail();
        boolean b2 = checkPassword();
        if (b1 && b2) {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            dataBaseHelper = new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);
            //DataBaseHelper dataBaseHelper =new DataBaseHelper(AddCustomerActivity.this,"DB_NAME_EXP4",null,1);

            int role = dataBaseHelper.checkEmailPassword(em, pass);
            if (role > 0) {
                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                if (rememberMe.isChecked()) {
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                } else {
                    editor.putString("email", "");
                }
                if(role == 1){
                    //Admin User
                    Toast.makeText(MainActivity.this, "You are Admin!", Toast.LENGTH_SHORT).show();
                } else if (role == 2) {
                    // Trainee User
                    Intent i = new Intent(MainActivity.this, TraineeHomeView.class);
                    i.putExtra("email", em);
                    startActivity(i);
                    this.finish(); //close this activity
                } else { // Instructor
                    Toast.makeText(MainActivity.this, "You are Instructor!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
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
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean formatFlag = false;

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

    private void saveSignUpDetails() {
        SharedPreferences settings = getSharedPreferences("userSignUp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Email", email.getText().toString());
        editor.putBoolean("PREF_IS_LOGIN_KEY", true);
        editor.commit();
    }

}