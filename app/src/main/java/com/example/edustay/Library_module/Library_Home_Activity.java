package com.example.edustay.Library_module;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edustay.Comman.Profile_Fragment;
import com.example.edustay.Library_module.Library_Fragment.Home_library_Fragment;
import com.example.edustay.Library_module.Library_Fragment.MyBooks_library_Fragment;
import com.example.edustay.Library_module.Library_Fragment.Search_library_Fragment;
import com.example.edustay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Library_Home_Activity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    public boolean doubletap = false;

    SharedPreferences preferences;//store temp data
    SharedPreferences.Editor editor; //put or edit data

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_home);
        preferences = PreferenceManager.getDefaultSharedPreferences(Library_Home_Activity.this);
        editor = preferences.edit();


        bottomNavigationView = findViewById(R.id.Library_home_BottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Library_home_BottomNavigationView);


    }





    Home_library_Fragment homeFragment =new Home_library_Fragment();
    MyBooks_library_Fragment MyBooksFragment =new MyBooks_library_Fragment();
    Search_library_Fragment SearchFragment =new Search_library_Fragment();
    Profile_Fragment profileFragment = new Profile_Fragment();




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){

        if (menuItem.getItemId() == R.id.lib_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout,homeFragment).commit();

        }  else if (menuItem.getItemId() == R.id.lib_my_books)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout,MyBooksFragment).commit();

        } else if (menuItem.getItemId() == R.id.lib_search)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout,SearchFragment).commit();

        }else if (menuItem.getItemId() == R.id.lib_profile)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Library_home_framelayout,profileFragment).commit();

        }
        return true;

    }

}
// first commit
//second code