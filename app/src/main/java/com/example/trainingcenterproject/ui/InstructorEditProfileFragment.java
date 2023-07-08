package com.example.trainingcenterproject.ui;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.Instructor;
import com.example.trainingcenterproject.InstructorSignUp;
import com.example.trainingcenterproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InstructorEditProfileFragment extends Fragment {
    EditText firstName,secondName,mail,number,address,specialization;
    String selectedDegree;
    ImageView photo;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button imgBtn;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    String oldMail;
    byte[] oldImageBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_instructor_edit_profile, container, false);

        firstName = (EditText) root.findViewById(R.id.textView_name1);
        secondName = (EditText) root.findViewById(R.id.textView_name2);
        mail = (EditText) root.findViewById(R.id.textView_email);
        number = (EditText) root.findViewById(R.id.textView_number);
        address = (EditText) root.findViewById(R.id.textView_address);
        specialization = (EditText) root.findViewById(R.id.textView_specialization);
        photo = (ImageView) root.findViewById(R.id.imageView_photo);

        //degree spinner
        spinner = root.findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Associate degree");
        arrayList.add("Bachelor's degree");
        arrayList.add("Master's degree");
        arrayList.add("Doctoral degree");
        arrayList.add("Professional degree");
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        photo = (ImageView) root.findViewById(R.id.imageView_photo);
        imgBtn = (Button) root.findViewById(R.id.buttonChangeImage);


        // Retrieve the data from the arguments
        Bundle arguments = getArguments();
        String email = "";
        if (arguments != null && arguments.containsKey("mail")) {
            email = arguments.getString("mail");
        }
        Instructor user = null;
        if(email != "") {
            oldMail = email;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            user = dataBaseHelper.getInstructorData(email);
        }
        else {
            oldMail = "";
            user = new Instructor("name1", "name2", "mail", "pass", null, "number", "address", "spec", "degree", new ArrayList<>());
            //Instructor user = (Instructor) arguments.getParcelable("Instructor"); // Retrieve the user object as Serializable
        }
        if(user != null) {
            firstName.setText(user.getFirstName());
            secondName.setText(user.getSecondName());
            mail.setText(user.getEmail());
            number.setText(user.getMobile_number());
            address.setText(user.getAddress());
            specialization.setText(user.getSpecialization());

            String initialValue = user.getDegree();
            int position = arrayAdapter.getPosition(initialValue);
            if(position>=0)
                spinner.setSelection(position);

            if (user.getPhoto() != null) {
                oldImageBytes = user.getPhoto();
                Bitmap oldImageBitmap = BitmapFactory.decodeByteArray(oldImageBytes, 0, oldImageBytes.length); // Convert the image bytes back to a Bitmap
                photo.setImageBitmap(oldImageBitmap);
            }
        }


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

        Button update = (Button) root.findViewById(R.id.button_update);
        update.setOnClickListener(view -> {
            try {
                validateData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return root;
    }

    void validateData() throws IOException {
        boolean b1 = checkFirstName();
        boolean b2 = checkSecondName();
        boolean b3 = checkEmail();
        //boolean b5 = checkImage();
        boolean b6 = checkMobileNumber();
        boolean b7 = checkAddress();
        boolean b8 = checkSpecialization();

        if(b1 && b2 && b3 && b6 && b7 && b8){
            String name1 = firstName.getText().toString();
            String name2 = secondName.getText().toString();
            String mail2 = mail.getText().toString();
            String num = number.getText().toString();
            String add = address.getText().toString();
            String sp = specialization.getText().toString();


            firstName.setText(name1);
            secondName.setText(name2);
            mail.setText(mail2);
            number.setText(num);
            address.setText(add);
            specialization.setText(sp);
            int position = arrayAdapter.getPosition(selectedDegree);
            if(position>=0)
                spinner.setSelection(position);
            if (selectedImageUri != null) {
                byte[] imageBytes = convertImageUriToByteArray(selectedImageUri);
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); // Convert the image bytes back to a Bitmap
                photo.setImageBitmap(imageBitmap);
                oldImageBytes = imageBytes;
            }


            //update this instructor values in the database
            Instructor newInstructor =new Instructor(name1,name2,mail2,"",oldImageBytes,num,add,sp,selectedDegree,null);
            DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
            dataBaseHelper.updateInstructorData(oldMail,newInstructor);

            Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "please log out and then log in to your profile to view the changes", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(), "Failed to update your profile - (check input fields)!", Toast.LENGTH_SHORT).show();
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
        String mail2 = mail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        if (mail2.isEmpty() || mail2.trim().isEmpty()) {
            mail.setError("This field is empty!");
            return false;
        }
        else if (!mail2.matches(emailPattern)) {
            mail.setError("Invalid email address");
            return false;
        }
        else if(dataBaseHelper.isUniqueEmail(mail2)==false && mail2.equals(oldMail)==false){
            mail.setError("This email is already used, please use a unique email address");
            return false;
        }
        else {
            mail.setError(null);
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

    boolean checkImage(){
        if(selectedImageUri == null){
            Toast toast =Toast.makeText(getContext(), "Please Attach your photo",Toast.LENGTH_SHORT);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ContentResolver contentResolver = requireContext().getContentResolver();

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertImageUriToByteArray(Uri imageUri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
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