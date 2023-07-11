package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdminSignUp extends AppCompatActivity {

    EditText firstName,secondName,email,password,confirmPass;
    ImageView photo;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button imgBtn;
    Bitmap bitmap;
    ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        firstName = findViewById(R.id.editTextName1);
        secondName = findViewById(R.id.editTextName2);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPass = findViewById(R.id.editTextConfirmPass);
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
        boolean b5 = checkPasswordConfirmation();
        boolean b6 = checkImage();

        if(b1 && b2 && b3 && b4 && b5 && b6){
            Toast.makeText(AdminSignUp.this, "Successful SignIn", Toast.LENGTH_SHORT).show();
            String name1 = firstName.getText().toString();
            String name2 = secondName.getText().toString();
            String mail = email.getText().toString();
            String pass = password.getText().toString();

            Admin newAdmin =new Admin(name1,name2,mail,pass,bitmap);
            DataBaseHelper dataBaseHelper =new DataBaseHelper(this);
            dataBaseHelper.insertAdmin(newAdmin);

            Intent intent = new Intent(AdminSignUp.this, AdminHomeView.class);
            intent.putExtra("name1",name1);
            intent.putExtra("name2",name2);
            intent.putExtra("mail",mail);

            Bitmap imageToStoreBitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();
            intent.putExtra("photo",imageInBytes);

            startActivity(intent);
            this.finish(); //close this activity
        }
        else{
            Toast.makeText(AdminSignUp.this, "SignIn Failed - ERROR (check input fields)!", Toast.LENGTH_SHORT).show();
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        if (mail.isEmpty() || mail.trim().isEmpty()) {
            email.setError("This field is empty!");
            return false;
        }
        else if (!mail.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        }
        else if(!dataBaseHelper.isUniqueEmail(mail)){
            email.setError("This email is already used, please use a unique email address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    boolean checkPassword() {
        String str = password.getText().toString();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
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
//        else if(!dataBaseHelper.isUniquePassword(str)){
//            password.setError("This password is already taken, enter a unique password");
//            return false;
//        }
        else {
            password.setError(null);
            return true;
        }
    }

    boolean checkPasswordConfirmation(){
        String str = password.getText().toString();
        String str2 = confirmPass.getText().toString();
        if (str2.isEmpty() || str2.trim().isEmpty()) {
            confirmPass.setError("This field is empty!");
            return false;
        }
        else if (!str2.equals(str)) {
            confirmPass.setError("Passwords do not match");
            confirmPass.requestFocus();
            return false;
        }
        else {
            confirmPass.setError(null);
            return true;
        }
    }

    boolean checkImage(){
        if(photo == null) {
            if (selectedImageUri == null) {
                Toast toast = Toast.makeText(AdminSignUp.this, "Please Attach your photo", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}