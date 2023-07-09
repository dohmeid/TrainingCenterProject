package com.example.trainingcenterproject.ui.profile;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.MainActivity;
import com.example.trainingcenterproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// Photo doesn't appear when I open the app, and it doesn't appear in the navigation drawer.
// The pages of courses history, comleted courses, search courses need courses to be added to try the results
// the page of available courses need to be completed
public class ProfileFragment extends Fragment {

    TextView name, email;
    ImageView photo, showHideBtn;
    int show = 1;
    EditText userName, userEmail, password, phone, address;
    Button save, cancel;
    private Uri selectedImageUri;
    byte[] ImageBytes;
    Cursor TraineeCursor;

    //SharedPreferences sharedPreferences;

    // DataBaseHelper dataBaseHelper = new DataBaseHelper(this, DataBaseHelper.databaseName, null, 1);

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            // Inflate the layout for this fragment
            View root = inflater.inflate(R.layout.fragment_view_profile, container, false);

            name = root.findViewById(R.id.profileTraineeName);
            email = root.findViewById(R.id.tv_email);
            photo = root.findViewById(R.id.imageView2);
            userName = root.findViewById(R.id.TraineeName);
            userEmail = root.findViewById(R.id.TraineeEmail);
            password = root.findViewById(R.id.TraineePass);
            phone = root.findViewById(R.id.TraineePhone);
            address = root.findViewById(R.id.TraineeAddress);
            showHideBtn = root.findViewById(R.id.showHideBtn);
            save = root.findViewById(R.id.saveBtn);
            cancel = root.findViewById(R.id.cancelBtn);


            // Retrieve the data from the arguments
//            Bundle arguments = getArguments();
//            String mail = "";
//            if (arguments != null && arguments.containsKey("email")) {
//                mail = arguments.getString("email");
//            }

//            SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("userSignUp", Context.MODE_PRIVATE);
//            String mail = sharedPreferences.getString("email", "");
            String mail = getActivity().getIntent().getStringExtra("email");

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            TraineeCursor = dataBaseHelper.getTrainee(mail);
            TraineeCursor.moveToFirst();

            //while( customersCursor != null && customersCursor.moveToFirst() ) {

                name.setText(TraineeCursor.getString(1) + " " + TraineeCursor.getString(2));
                userName.setText(TraineeCursor.getString(1) + " " + TraineeCursor.getString(2));
                email.setText(TraineeCursor.getString(0));
                userEmail.setText(TraineeCursor.getString(0));
                userEmail.setEnabled(false);
                password.setText(TraineeCursor.getString(3));
                phone.setText(TraineeCursor.getString(5));
                address.setText(TraineeCursor.getString(6));
                if (TraineeCursor.getBlob(4) != null) {
                    ImageBytes = TraineeCursor.getBlob(4);
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(ImageBytes, 0, ImageBytes.length); // Convert the image bytes back to a Bitmap
                    photo.setImageBitmap(imageBitmap);
                }
         //   }

            showHideBtn.setOnClickListener(view -> {
                if (show == 1) {
                    password.setTransformationMethod(null);
                    show = 0;
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    show = 1;
                }
            });

//            cancel.setOnClickListener(view -> {
//                startActivity(new Intent(ProfileFragment.this, TraineeHomeView.class));
//                finish();
//            });

            photo.setOnClickListener(view -> openImagePicker());

            save.setOnClickListener(view -> {
                StringBuilder firstName = new StringBuilder();
                String mailAddress = userEmail.getText().toString();
                String[] splited = userName.getText().toString().split(" ");
                String lastName = splited[splited.length - 1];
                for (int i = 0; i < splited.length - 1; i++)
                    firstName.append(splited[i]);
                String pass = password.getText().toString();
                String mobile = phone.getText().toString();
                String add = address.getText().toString();

                try {
                    if (validateData(firstName, lastName, pass, mobile, add)) {
                        dataBaseHelper.updateTrainee(mailAddress, firstName, lastName, pass, ImageBytes, mobile, add);
                        name.setText(firstName + " " + lastName);
                        email.setText(mailAddress);
                        Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return root;
        }
        catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        TraineeCursor.moveToFirst();

        //while( customersCursor != null && customersCursor.moveToFirst() ) {

        name.setText(TraineeCursor.getString(1) + " " + TraineeCursor.getString(2));
        userName.setText(TraineeCursor.getString(1) + " " + TraineeCursor.getString(2));
        email.setText(TraineeCursor.getString(0));
        userEmail.setText(TraineeCursor.getString(0));
        userEmail.setEnabled(false);
        password.setText(TraineeCursor.getString(3));
        phone.setText(TraineeCursor.getString(5));
        address.setText(TraineeCursor.getString(6));
        if (TraineeCursor.getBlob(4) != null) {
            ImageBytes = TraineeCursor.getBlob(4);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(ImageBytes, 0, ImageBytes.length); // Convert the image bytes back to a Bitmap
            photo.setImageBitmap(imageBitmap);
        }
    }

    boolean validateData (StringBuilder firstName, String lastName, String pass, String
    mobile, String add) throws IOException {


        if (firstName.length() == 0) {
            userName.setError("Trainee Name is required");
            userName.requestFocus();
            return false;
        } else if (firstName.length() < 3) {
            userName.setError("Name is too short!");
            userName.requestFocus();
            return false;
        } else if (firstName.length() > 20) {
            userName.setError("Name is too long!");
            userName.requestFocus();
            return false;
        }
        if (lastName.isEmpty()) {
            userName.setError("Trainee Name is required");
            userName.requestFocus();
            return false;
        } else if (lastName.length() < 3) {
            userName.setError("Name is too short!");
            userName.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        } else if (pass.length() < 8) {
            password.setError("Password must be at least 8 characters long");
            password.requestFocus();
            return false;
        } else if (!isValidPassword(pass)) {
            password.setError("Password must contain at least one number, one lowercase letter, and one uppercase letter.");
            password.requestFocus();
            return false;
        }
        if (selectedImageUri != null) {
            byte[] imageBytes = convertImageUriToByteArray(selectedImageUri);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); // Convert the image bytes back to a Bitmap
            photo.setImageBitmap(imageBitmap);
            ImageBytes = imageBytes;
        }
        if (mobile.isEmpty()) {
            phone.setError("Phone is required");
            phone.requestFocus();
            return false;
        } else if (mobile.length() < 10) {
            phone.setError("Phone number must be at least 10 digits long");
            phone.requestFocus();
            return false;
        }
        if (add.isEmpty()) {
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

    static boolean isValidPassword (String str){
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
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ContentResolver contentResolver = requireContext().getContentResolver();

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
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