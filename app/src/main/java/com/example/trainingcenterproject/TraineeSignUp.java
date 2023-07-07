package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    Button imgBtn;
    Bitmap bitmap;

    @SuppressLint("MissingInflatedId")
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

        photo = findViewById(R.id.traineeImage);
        imgBtn = findViewById(R.id.buttonImage);
        Button getStarted = findViewById(R.id.button);

        imgBtn.setOnClickListener(new View.OnClickListener() {
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
        boolean b5 = checkPhoto();
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

            Trainee newTrainee =new Trainee(name1,name2,mail,pass,selectedImageUri.toString(),num,add);
            DataBaseHelper dataBaseHelper =new DataBaseHelper(this);
            dataBaseHelper.insertTrainee(newTrainee);

            //go to Trainee profile
            startActivity(new Intent(TraineeSignUp.this, AdminHomeView.class));
            finish(); //close this activity

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
        String str2 = confirmPass.getText().toString();

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
        // Check if the string contains at least one number.
        else if (!str.matches(".*\\d.*")) {
            password.setError("Password must contain at least one number.");
            return false;
        }
        // Check if the string contains at least one lowercase letter.
        else if (!str.matches(".*[a-z].*")) {
            password.setError("Password must contain at least one lowercase letter.");
            return false;
        }
        // Check if the string contains at least one uppercase letter.
        else if (!str.matches(".*[A-Z].*")) {
            password.setError("Password must contain at least one uppercase letter.");
            return false;
        }
        else if (!str.equals(str2)) {
            confirmPass.setError("Passwords do not match");
            confirmPass.requestFocus();
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    boolean checkPhoto() {
        if(photo == null){
            Toast toast =Toast.makeText(TraineeSignUp.this, "Please Attach your photo",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}