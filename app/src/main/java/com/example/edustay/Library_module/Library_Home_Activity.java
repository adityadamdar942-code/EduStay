package com.example.edustay.Library_module;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edustay.Comman.Profile_Fragment;
import com.example.edustay.Library_module.Library_Fragment.Home_library_Fragment;
import com.example.edustay.Library_module.Library_Fragment.MyBooks_library_Fragment;
import com.example.edustay.Library_module.Library_Fragment.Search_library_Fragment;
import com.example.edustay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Library_Home_Activity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    public boolean doubletap = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    BottomNavigationView bottomNavigationView;

    Home_library_Fragment homeFragment = new Home_library_Fragment();
    MyBooks_library_Fragment myBooksFragment = new MyBooks_library_Fragment();
    Search_library_Fragment searchFragment = new Search_library_Fragment();
    Profile_Fragment profileFragment = new Profile_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(Library_Home_Activity.this);
        editor = preferences.edit();

        bottomNavigationView = findViewById(R.id.Library_home_BottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Library_home_framelayout, homeFragment)
                    .commit();

            bottomNavigationView.setSelectedItemId(R.id.lib_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.lib_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Library_home_framelayout, homeFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.lib_my_books) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Library_home_framelayout, myBooksFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.lib_search) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Library_home_framelayout, searchFragment)
                    .commit();

        } else if (menuItem.getItemId() == R.id.lib_profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Library_home_framelayout, profileFragment)
                    .commit();
        }

        return true;
    }
}

// first commit
//second code