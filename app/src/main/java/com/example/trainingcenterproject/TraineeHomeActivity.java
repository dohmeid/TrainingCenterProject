package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainingcenterproject.databinding.ActivityTraineesHomeBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class TraineeHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTraineesHomeBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri selectedImageUri;
    TextView name, email;
    ImageView photo, showHideBtn, profilePhoto;
    int show = 0;
    EditText userName, userEmail, password, phone, address;
    Button save, cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityTraineesHomeBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());//
        setContentView(R.layout.activity_trainees_home) ;
        DrawerLayout drawer = findViewById(R.id.drawer_layout) ;
        //setSupportActionBar(binding.appBarInstructorHome.toolbar);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar) ;
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//        });
        //DrawerLayout drawer = binding.drawerLayout;

//        NavigationView navigationView = binding.navView;
        NavigationView navigationView = findViewById(R.id.nav_view) ;
        NavController navController = Navigation.findNavController(TraineeHomeActivity.this, R.id.nav_host_fragment_content_trainees);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_profile, R.id.nav_available_courses,
                R.id.nav_search_courses, R.id.nav_studied_course, R.id.nav_courses_history,
                R.id.nav_log_out).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(TraineeHomeActivity.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //to change the header data into the registered instructor data
        View headerView = navigationView.getHeaderView(0);
        String mail = getIntent().getStringExtra("email");
        Bundle bundle = new Bundle();
        bundle.putString("email", mail);

        name = findViewById(R.id.profileTraineeName);
        email = findViewById(R.id.tv_email);
        photo = (ImageView) headerView.findViewById(R.id.imageViewPhoto);
        profilePhoto  = findViewById(R.id.imageView2);
        userName = findViewById(R.id.TraineeName);
        userEmail = findViewById(R.id.TraineeEmail);
        password = findViewById(R.id.TraineePass);
        phone = findViewById(R.id.TraineePhone);
        address = findViewById(R.id.TraineeAddress);
        showHideBtn = findViewById(R.id.showHideBtn);
        save = findViewById(R.id.saveBtn);
        cancel = findViewById(R.id.cancelBtn);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        Cursor customersCursor = dataBaseHelper.getTrainee(mail);

        customersCursor.moveToFirst();
        name.setText(customersCursor.getString(1) + " " + customersCursor.getString(2) );
        userName.setText(customersCursor.getString(1) + " " + customersCursor.getString(2) );
        email.setText(customersCursor.getString(0));
        userEmail.setText(customersCursor.getString(0));
        userEmail.setEnabled(false);
        password.setText(customersCursor.getString(3));
        phone.setText(customersCursor.getString(5));
        address.setText(customersCursor.getString(6));

        byte[] img = customersCursor.getBlob(4);
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(img, 0, img.length); // Convert the image bytes back to a Bitmap
        photo.setImageBitmap(imageBitmap);
        profilePhoto.setImageBitmap(imageBitmap);

        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewName);
        //String name = instructor.getFirstName() + instructor.getSecondName();
        navUsername.setText(customersCursor.getString(1) + " " + customersCursor.getString(2));
        TextView navEmail = (TextView) headerView.findViewById(R.id.textViewEmail);
        navEmail.setText(customersCursor.getString(0));

        showHideBtn.setOnClickListener(view -> {
            if(show == 1){
                password.setTransformationMethod(null);
                show = 0;
            } else{
                password.setTransformationMethod(new PasswordTransformationMethod());
                show = 1;
            }
        });


//        cancel.setOnClickListener(view -> {
//            startActivity(new Intent(TraineeHomeActivity.this, TraineeHomeActivity.class));
//            finish();
//        });


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
                dataBaseHelper.updateTrainee(mailAddress, firstName, lastName, pass, img, mobile, add);
                name.setText(firstName + " " + lastName);
                email.setText(mailAddress);
                Toast.makeText(TraineeHomeActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.


//        navController.navigate(R.id.nav_profile, bundle);
//        navController.navigate(R.id.nav_available_courses, bundle);
//         navController.navigate(R.id.nav_search_courses, bundle);
//        navController.navigate(R.id.nav_studied_course, bundle);
//        navController.navigate(R.id.nav_courses_history, bundle);
//        navController.navigate(R.id.nav_log_out, bundle);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_trainees);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
                profilePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}