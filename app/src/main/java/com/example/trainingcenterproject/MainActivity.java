package com.example.trainingcenterproject;

import static com.example.trainingcenterproject.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
        rememberMe = (CheckBox) findViewById(R.id.checkBox);

        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.insertData("doha@email.com", "12345678");
        //DataBaseHelper dataBaseHelper =new DataBaseHelper(AddCustomerActivity.this,"DB_NAME_EXP4",null,1);


        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //to get stored data
        String mail = sharedPreferences.getString("email", "No Email Stored");
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


    boolean isEmpty(EditText text) { //check if text view is empty
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmail(EditText text) { //check email format
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    void checkData() {
        boolean flag = true;

       /* if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(lastName)) {
            lastName.setError("Last name is required!");
        }*/
        if (isEmpty(email)) {
            email.setError("This field is empty!");
            flag = false;
        } else if (isEmail(email) == false) {
            email.setError("Please enter valid email!");
            flag = false;
        }

        if (isEmpty(password)) {
            password.setError("This field is empty!");
            flag = false;
        } else if (password.getText().toString().length() < 4) {
            password.setError("Password must be at least 8 characters long!");
            flag = false;
        }

        if (flag) {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            if (dataBaseHelper.checkEmailPassword(em, pass) == true) {
                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                if (rememberMe.isChecked()) {
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                } else {
                    editor.putString("email", "");
                }
                //Intent i = new Intent(MainActivity.this, person.class);
                //startActivity(i);
                //this.finish(); //close this activity
            } else {
                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}