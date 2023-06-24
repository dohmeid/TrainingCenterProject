package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class TraineeSignUp extends AppCompatActivity {

    EditText firstName,secondName,email,password,confirmPass,number,address;
    ImageView photo;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_sign_up);

        firstName = findViewById(R.id.editTextName1);
        secondName = findViewById(R.id.editTextName2);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPass = findViewById(R.id.editTextConfirmPass);
        number = findViewById(R.id.editTextMobile);
        address = findViewById(R.id.editTextAddress);

        photo = findViewById(R.id.imageView3);
        imgbtn = findViewById(R.id.buttonImage);
        Button getStarted = findViewById(R.id.button);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }


    void validateData() {
        boolean b1 = checkFirstName();
        boolean b2 = checkSecondName();
        boolean b3 = checkEmail();
        boolean b4 = checkPassword();
        boolean b5 = checkConfirmPassword();
        boolean b6 = checkMobileNumber();
        boolean b7 = checkAddress();

        if(b1 && b2 && b3 && b4 && b5 && b6 && b7){
            Toast.makeText(TraineeSignUp.this, "Successful SignIn", Toast.LENGTH_SHORT).show();
            String name1 = firstName.getText().toString();
            String name2 = secondName.getText().toString();
            String mail = email.getText().toString();
            String pass = password.getText().toString();
            String num = number.getText().toString();
            String add = address.getText().toString();

            Trainee newTrainee =new Trainee(name1,name2,mail,pass," ",num,add);
            DataBaseHelper dataBaseHelper =new DataBaseHelper(this);
            dataBaseHelper.insertTrainee(newTrainee);

            //go to Trainee profile
            //Intent i = new Intent(MainActivity.this, person.class);
            //startActivity(i);
            //this.finish(); //close this activity

        }
        else{
            Toast.makeText(TraineeSignUp.this, "SignIn Failed - ERROR (check input fields)!", Toast.LENGTH_SHORT).show();
        }

    }

    boolean checkFirstName(){
        String name1 = firstName.getText().toString();
        if (name1.isEmpty() || name1.trim().isEmpty()) {
            firstName.setError("This field is empty!");
            return false;
        }
        else if (name1.length()>20) {
            firstName.setError("Maximum length for name is 20 characters");
            return false;
        }
        else if (name1.length()<3) {
            firstName.setError("Minimum length for name is 3 characters");
            return false;
        }
        else {
            firstName.setError(null);
            return true;
        }
    }

    boolean checkSecondName(){
        String name = secondName.getText().toString();
        if (name.isEmpty() || name.trim().isEmpty()) {
            secondName.setError("This field is empty!");
            return false;
        }
        else if (name.length()>20) {
            secondName.setError("Maximum length for name is 20 characters");
            return false;
        }
        else if (name.length()<3) {
            secondName.setError("Minimum length for name is 3 characters");
            return false;
        }
        else {
            secondName.setError(null);
            return true;
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
        else if (str.length() > 15) {
            password.setError("Password must be at most 15 characters long!");
            return false;
        }

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag){
                formatFlag = true;
                break;
            }
        }
        if(formatFlag == false){
            password.setError("Password must be contain at least 1 number, 1 lowercase letter, and 1 uppercase letter!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    boolean checkConfirmPassword() {
        String str = confirmPass.getText().toString();
        String pass = password.getText().toString();
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean formatFlag = false;

        if (str.isEmpty() || str.trim().isEmpty()) {
            confirmPass.setError("This field is empty!");
            return false;
        }
        else if (str.length() < 8) {
            confirmPass.setError("Password must be at least 8 characters long!");
            return false;
        }
        else if (str.length() > 15) {
            confirmPass.setError("Password must be at most 15 characters long!");
            return false;
        }

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag){
                formatFlag = true;
                break;
            }
        }
        if(formatFlag == false){
            confirmPass.setError("Password must be contain at least 1 number, 1 lowercase letter, and 1 uppercase letter!");
            return false;
        }
        else if(str.equals(pass) == false){
            confirmPass.setError("Passwords are not matching!");
            return false;
        }
        else {
            confirmPass.setError(null);
            return true;
        }
    }

    boolean checkMobileNumber(){
        String val = number.getText().toString();
        if (val.isEmpty()) {
            number.setError("This field cannot be empty");
            return false;
        }
        else if (number.length() != 8) {
            number.setError("Number must contain 8 digits!");
            return false;
        }
        else {
            number.setError(null);
            return true;
        }
    }

    boolean checkAddress(){
        String val = address.getText().toString();
        if (val.isEmpty()) {
            address.setError("This field cannot be empty");
            return false;
        }
        else {
            address.setError(null);
            return true;
        }
    }

    boolean checkImage(){
        //photo.setImageResource(R.drawable.placeholder)
        return true;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}