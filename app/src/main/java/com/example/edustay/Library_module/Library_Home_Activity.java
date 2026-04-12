package com.example.edustay.Library_module;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

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

    SharedPreferences preferences;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Home_library_Fragment homeFragment = new Home_library_Fragment();
    MyBooks_library_Fragment myBooksFragment = new MyBooks_library_Fragment();
    Search_library_Fragment searchFragment = new Search_library_Fragment();
    Profile_Fragment profileFragment = new Profile_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_home);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        bottomNavigationView = findViewById(R.id.Library_home_BottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout, homeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.lib_home);
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Library Home");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.lib_home) {
            getSupportActionBar().setTitle("Library Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout, homeFragment).commit();

        } else if (id == R.id.lib_my_books) {
            getSupportActionBar().setTitle("My Books");
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout, myBooksFragment).commit();

        } else if (id == R.id.lib_search) {
            getSupportActionBar().setTitle("Search Library");
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout, searchFragment).commit();

        } else if (id == R.id.lib_profile) {
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout, profileFragment).commit();
        }
        return true;
    }
}