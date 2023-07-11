package com.example.trainingcenterproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class InstructorSignUp extends AppCompatActivity {

    EditText firstName,secondName,email,password,confirmPass,number,address,specialization,courses;
    ImageView photo;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button imgBtn;
    Spinner spinner;
    String selectedDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_up);

        firstName = findViewById(R.id.editTextName1);
        secondName = findViewById(R.id.editTextName2);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPass = findViewById(R.id.editTextConfirmPass);
        number = findViewById(R.id.editTextMobile);
        address = findViewById(R.id.editTextAddress);
        specialization = findViewById(R.id.editTextSpecialization);
        courses = findViewById(R.id.editTextCourses);

        photo = findViewById(R.id.instructorImage);
        imgBtn = findViewById(R.id.buttonImage);
        Button getStarted = findViewById(R.id.button);

        spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Associate degree");
        arrayList.add("Bachelor's degree");
        arrayList.add("Master's degree");
        arrayList.add("Doctoral degree");
        arrayList.add("Professional degree");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDegree = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + selectedDegree, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        imgBtn.setOnClickListener(v -> openImagePicker());

        getStarted.setOnClickListener(view -> {
            try {
                validateData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void validateData() throws IOException {
        boolean b1 = checkFirstName();
        boolean b2 = checkSecondName();
        boolean b3 = checkEmail();
        boolean b4 = checkPassword();
        boolean b44 = checkPasswordConfirmation();
        boolean b5 = checkImage();
        boolean b6 = checkMobileNumber();
        boolean b7 = checkAddress();
        boolean b8 = checkSpecialization();
        boolean b9 = checkCourses();


        if(b1 && b2 && b3 && b4 && b44 && b5 && b6 && b7 && b8 && b9){
            Toast.makeText(InstructorSignUp.this, "Successful SignIn", Toast.LENGTH_SHORT).show();
            String name1 = firstName.getText().toString();
            String name2 = secondName.getText().toString();
            String mail = email.getText().toString();
            String pass = password.getText().toString();

            String img = selectedImageUri.toString();
            byte[] imageBytes = convertImageUriToByteArray(selectedImageUri);

            String num = number.getText().toString();
            String add = address.getText().toString();
            String sp = specialization.getText().toString();

            //to extract courses
            ArrayList<String> c = new ArrayList<>();
            String input = courses.getText().toString();
            String[] splitArray = input.split(",");
            for (String item : splitArray) {
                c.add(item);
            }

            Instructor newInstructor =new Instructor(name1,name2,mail,pass,imageBytes,num,add,sp,selectedDegree,c);
            DataBaseHelper dataBaseHelper =new DataBaseHelper(this);
            dataBaseHelper.insertInstructor(newInstructor);

            //go to Instructor profile
            Intent i = new Intent(InstructorSignUp.this, InstructorHome.class);
            i.putExtra("email", mail);
            startActivity(i);
            this.finish(); //close this activity
        }
        else{
            Toast.makeText(InstructorSignUp.this, "SignIn Failed - ERROR (check input fields)!", Toast.LENGTH_SHORT).show();
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

    boolean checkMobileNumber(){
        String val = number.getText().toString();
        if (val.isEmpty()) {
            number.setError("This field cannot be empty");
            return false;
        }
        else if (number.length() != 10) {
            number.setError("Number must contain 10 digits!");
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

    boolean checkSpecialization(){
        String val = specialization.getText().toString();
        if (val.isEmpty()) {
            specialization.setError("This field cannot be empty");
            return false;
        }
        else {
            specialization.setError(null);
            return true;
        }
    }

    boolean checkCourses(){
        String val = courses.getText().toString();
        if (val.isEmpty()) {
            courses.setError("This field cannot be empty");
            return false;
        }
        else {
            courses.setError(null);
            return true;
        }
    }

    boolean checkImage(){
        //photo.setImageResource(R.drawable.placeholder)
       // if(selectedImageUri == null){
        if(selectedImageUri == null){
            Toast toast =Toast.makeText(InstructorSignUp.this, "Please Attach your photo",Toast.LENGTH_SHORT);
            toast.show();
            return false;
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertImageUriToByteArray(Uri imageUri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

}