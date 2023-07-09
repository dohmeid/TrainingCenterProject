package com.example.trainingcenterproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trainingcenterproject.ui.InstructorStudentsFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingcenterproject.databinding.ActivityInstructorHomeBinding;

import java.io.IOException;

public class InstructorHome extends AppCompatActivity   {

    Instructor instructor;
    String mail;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInstructorHomeBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    ImageView personalPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//setContentView(R.layout.activity_instructor_home) ;
        setSupportActionBar(binding.appBarInstructorHome.toolbar);//Toolbar toolbar = findViewById(R.id.toolbar) ;//setSupportActionBar(toolbar) ;
        DrawerLayout drawer = binding.drawerLayout;//DrawerLayout drawer = findViewById(R.id.drawer_layout) ;
        NavigationView navigationView = binding.navView;//NavigationView navigationView = findViewById(R.id.nav_view) ;

        //to change the header data into the registered instructor data
        View headerView = navigationView.getHeaderView(0);
        mail = getIntent().getStringExtra("email");
        //Bundle bundle = new Bundle();
        //bundle.putString("mail", mail);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        Instructor instructor = dataBaseHelper.getInstructorData(mail);

        personalPhoto = (ImageView) headerView.findViewById(R.id.imageViewPhoto);
        byte[] img = instructor.getPhoto();
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(img, 0, img.length); // Convert the image bytes back to a Bitmap
        personalPhoto.setImageBitmap(imageBitmap);  // Set the Bitmap to the ImageView

        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewName);
        String name = instructor.getFirstName() + instructor.getSecondName();
        navUsername.setText(name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.textViewEmail);
        navEmail.setText(instructor.getEmail());

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_viewProfile, R.id.nav_editProfile,
                R.id.nav_schedule, R.id.nav_courses, R.id.nav_students,
                R.id.nav_logout).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(InstructorHome.this, R.id.nav_host_fragment_content_instructor_home);
        Bundle bundle = new Bundle();
        bundle.putString("mail", mail);
        int id = navController.getCurrentDestination().getId();
        navController.popBackStack(id, true) ;
        navController.navigate(id, bundle);
        NavigationUI.setupActionBarWithNavController(InstructorHome.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_instructor_home);
        Bundle bundle = new Bundle();
        bundle.putString("mail", mail);
        int id = navController.getCurrentDestination().getId();
        navController.popBackStack(id, true) ;
        navController.navigate(id, bundle);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


}