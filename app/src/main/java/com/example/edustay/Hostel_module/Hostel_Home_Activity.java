package com.example.edustay.Hostel_module;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edustay.Comman.Profile_Fragment;
import com.example.edustay.Hostel_module.Hostel_Fragment.Complaints_hostel_Fragment;
import com.example.edustay.Hostel_module.Hostel_Fragment.Home_hostel_Fragment;
import com.example.edustay.Hostel_module.Hostel_Fragment.RoomInfo_hostel_Fragment;
import com.example.edustay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Hostel_Home_Activity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


        SharedPreferences preferences;
        BottomNavigationView bottomNavigationView;
        Toolbar toolbar;

        Home_hostel_Fragment homeFragment = new Home_hostel_Fragment();
        Complaints_hostel_Fragment complaintsFragment = new Complaints_hostel_Fragment();
        RoomInfo_hostel_Fragment roominfoFragment = new RoomInfo_hostel_Fragment();
        Profile_Fragment profileFragment = new Profile_Fragment();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_hostel_home);


            toolbar = findViewById(R.id.toolbarHostel);
            setSupportActionBar(toolbar);

            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            bottomNavigationView = findViewById(R.id.Hostel_home_BottomNavigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Hostel_home_framelayout, homeFragment).commit();
                bottomNavigationView.setSelectedItemId(R.id.hostel_home);


                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Hostel Home");
                }
            }
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();


            if (id == R.id.hostel_home) {
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Hostel Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.Hostel_home_framelayout, homeFragment).commit();

            } else if (id == R.id.hostel_complaint) {
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Complaints");
                getSupportFragmentManager().beginTransaction().replace(R.id.Hostel_home_framelayout, complaintsFragment).commit();

            } else if (id == R.id.hostel_room) {
                if (getSupportActionBar() != null) getSupportActionBar().setTitle("Room Information");
                getSupportFragmentManager().beginTransaction().replace(R.id.Hostel_home_framelayout, roominfoFragment).commit();

            } else if (id == R.id.hostel_profile) {
                if (getSupportActionBar() != null) getSupportActionBar().setTitle(" Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.Hostel_home_framelayout, profileFragment).commit();
            }

            return true;
        }
    }