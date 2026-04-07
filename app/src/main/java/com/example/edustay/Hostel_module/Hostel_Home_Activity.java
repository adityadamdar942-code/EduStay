package com.example.edustay.Hostel_module;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

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

    public boolean doubletap = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    BottomNavigationView bottomNavigationView;

    Home_hostel_Fragment homeFragment = new Home_hostel_Fragment();
    Complaints_hostel_Fragment complaintsFragment = new Complaints_hostel_Fragment();
    RoomInfo_hostel_Fragment roominfoFragment = new RoomInfo_hostel_Fragment();
    Profile_Fragment profileFragment = new Profile_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hostel_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(Hostel_Home_Activity.this);
        editor = preferences.edit();

        bottomNavigationView = findViewById(R.id.Hostel_home_BottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Hostel_home_framelayout, homeFragment)
                    .commit();

            bottomNavigationView.setSelectedItemId(R.id.hostel_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.hostel_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Hostel_home_framelayout, homeFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.hostel_complaint) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Hostel_home_framelayout, complaintsFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.hostel_room) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Hostel_home_framelayout, roominfoFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.hostel_profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Hostel_home_framelayout, profileFragment)
                    .commit();
        }

        return true;
    }
}
