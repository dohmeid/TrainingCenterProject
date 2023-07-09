package com.example.trainingcenterproject;

import static com.example.trainingcenterproject.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button sign_in;
    Button sign_up;
    CheckBox rememberMe;
    DataBaseHelper dataBaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;

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
        boolean b3 = false;

        if (b1 && b2) {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            dataBaseHelper = new DataBaseHelper(this);

            Cursor c3 = dataBaseHelper.adminCheckEmailPassword(em, pass);
            if (c3.getCount() > 0) {
                b3 = true;
            }else{
                b3 = false;
            }

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

                if(b3){
                    if (c3 != null && c3.moveToFirst()) {
                        Intent intent = new Intent(MainActivity.this, AdminHomeView.class);
                        intent.putExtra("name1", c3.getString(1));
                        intent.putExtra("name2", c3.getString(2));
                        intent.putExtra("mail", c3.getString(0));
                        @SuppressLint("Range") byte[] imageData = c3.getBlob(c3.getColumnIndex("PHOTO"));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        objectByteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
                        imageInBytes = objectByteArrayOutputStream.toByteArray();
                        intent.putExtra("photo", imageInBytes);
                        startActivity(intent);
                    }
                }
                else if(b4) {
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email.getText().toString());
                    Intent i = new Intent(MainActivity.this, TraineeHomeActivity.class);
                    i.putExtra("email", em);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(MainActivity.this, InstructorHome.class);
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