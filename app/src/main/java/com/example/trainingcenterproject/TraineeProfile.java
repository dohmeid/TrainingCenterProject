package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TraineeProfile extends AppCompatActivity {

    TextView name, email;
    ImageView photo, showHideBtn;
    int show = 0;
    EditText userName, userEmail, password, phone, address;
    Button save, cancel;
    private Uri selectedImageUri;

   // DataBaseHelper dataBaseHelper = new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_profile);

        name = findViewById(R.id.name);
        email = findViewById(R.id.tv_email);
        photo = findViewById(R.id.imageView2);
        userName = findViewById(R.id.TraineeName);
        userEmail = findViewById(R.id.TraineeEmail);
        password = findViewById(R.id.TraineePass);
        phone = findViewById(R.id.TraineePhone);
        address = findViewById(R.id.TraineeAddress);
        showHideBtn = findViewById(R.id.showHideBtn);
        save = findViewById(R.id.saveBtn);
        cancel = findViewById(R.id.cancelBtn);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String mail = sharedPreferences.getString("email", "");

        DataBaseHelper dataBaseHelper =new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);
        Cursor customersCursor = dataBaseHelper.getTrainees(mail);

        customersCursor.moveToFirst();
        name.setText(customersCursor.getString(1) + " " + customersCursor.getString(2) );
        userName.setText(customersCursor.getString(1) + " " + customersCursor.getString(2) );
        email.setText(customersCursor.getString(0));
        userEmail.setText(customersCursor.getString(0));
        userEmail.setEnabled(false);
        password.setText(customersCursor.getString(3));
        phone.setText(customersCursor.getString(5));
        address.setText(customersCursor.getString(6));

        showHideBtn.setOnClickListener(view -> {
            if(show == 1){
                password.setTransformationMethod(null);
                show = 0;
            } else{
                password.setTransformationMethod(new PasswordTransformationMethod());
                show = 1;
            }
        });


        cancel.setOnClickListener(view -> {
            startActivity(new Intent(TraineeProfile.this, TraineeHomeView.class));
            finish();
        });


        photo.setOnClickListener(view -> openImagePicker());

        save.setOnClickListener(view -> {
            StringBuilder firstName = new StringBuilder();
            String mailAddress = userEmail.getText().toString();
            String[] splited = userName.getText().toString().split(" ");
            String lastName = splited[splited.length-1];
            for (int i=0; i<splited.length-1; i++)
                firstName.append(splited[i]);
            String pass = password.getText().toString();
            String mobile = phone.getText().toString();
            String add = address.getText().toString();

            if(validateData(firstName, lastName, pass, mobile, add)){
                dataBaseHelper.updateTrainee(mailAddress, firstName, lastName, pass, selectedImageUri.toString(), mobile, add);
                name.setText(firstName + " " + lastName);
                email.setText(mailAddress);
                Toast.makeText(TraineeProfile.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateData(StringBuilder firstName, String lastName, String pass, String mobile, String add) {


        if (firstName.length() == 0) {
            userName.setError("Trainee Name is required");
            userName.requestFocus();
            return false;
        } else if(firstName.length() < 3){
            userName.setError("Name is too short!");
            userName.requestFocus();
            return false;
        } else if(firstName.length() > 20){
        userName.setError("Name is too long!");
        userName.requestFocus();
        return false;
    }
        if (lastName.isEmpty()) {
            userName.setError("Trainee Name is required");
            userName.requestFocus();
            return false;
        } else if(lastName.length() < 3){
            userName.setError("Name is too short!");
            userName.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        } else if (password.length() < 8) {
            password.setError("Password must be at least 8 characters long");
            password.requestFocus();
            return false;
        } else if (!isValidPassword(pass)) {
            password.setError("Password must contain at least one number, one lowercase letter, and one uppercase letter.");
            password.requestFocus();
            return false;
        }
        if(mobile.isEmpty()){
            phone.setError("Phone is required");
            phone.requestFocus();
            return false;
        } else if (mobile.length() < 10){
            phone.setError("Phone number must be at least 10 digits long");
            phone.requestFocus();
            return false;
        }
        if(add.isEmpty()){
            phone.setError("Address is required");
            phone.requestFocus();
            return false;
        }
        return true;

    }

//    private boolean isValidEmail(String mail) {
//        if(dataBaseHelper.checkEmails(mail))
//            return false;
//        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(mail);
//        return matcher.matches();
//    }

    public static boolean isValidPassword(String str) {
        // Check if the string contains at least one number.
        if (!str.matches(".*\\d.*")) {
            return false;
        }
        // Check if the string contains at least one lowercase letter.
        if (!str.matches(".*[a-z].*")) {
            return false;
        }
        // Check if the string contains at least one uppercase letter.
        if (!str.matches(".*[A-Z].*")) {
            return false;
        }
        // The string contains at least one number, one lowercase letter, and one uppercase letter.
        return true;
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), TraineeSignUp.PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TraineeSignUp.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
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