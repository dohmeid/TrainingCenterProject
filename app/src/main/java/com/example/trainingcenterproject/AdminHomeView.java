package com.example.trainingcenterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    LottieAnimationView anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_view);
        anim = findViewById(R.id.anim);
        anim.setAnimation(R.raw.wellcome);
        anim.playAnimation();


        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name1");
        String name2 = intent.getStringExtra("name2");
        String mail = intent.getStringExtra("mail");
        byte[] photo = intent.getByteArrayExtra("photo");
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView usr_name = headerView.findViewById(R.id.usr_name);
        TextView use_email = headerView.findViewById(R.id.use_email);
        ImageView imageView = headerView.findViewById(R.id.imageProfile);
        usr_name.setText(name1+name2);
        use_email.setText(mail);
        imageView.setImageBitmap(photoBitmap);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_add:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new addcourseFragment()).commit();
                break;
            case R.id.nav_delete:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new deletecourseFragment()).commit();
                break;
            case R.id.nav_edit:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new editcourseFragment()).commit();
                break;
            case R.id.nav_accept_reject:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new acceptrejectFragment()).commit();
                break;
            case R.id.nav_available:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new makeavFragment()).commit();
                break;
            case R.id.nav_profile:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new profileFragment()).commit();
                break;
            case R.id.nav_history:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new historyFragment()).commit();
                break;
            case R.id.nav_student:
                anim.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new viewStudentFragment()).commit();
                break;
            case R.id.logout:
                anim.setAnimation(R.raw.goodbye);
                anim.playAnimation();
                long animationDuration = anim.getDuration();
                long delayDuration = 2000; // milliseconds

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Navigate to the desired activity after the delay
                        startActivity(new Intent(AdminHomeView.this, MainActivity.class));
                        finish();
                    }
                }, animationDuration + delayDuration);

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}