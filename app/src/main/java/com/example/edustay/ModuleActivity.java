package com.example.edustay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.edustay.Hostel_module.Hostel_Home_Activity;
import com.example.edustay.Library_module.Library_Home_Activity;

public class ModuleActivity extends AppCompatActivity {


    public boolean doubletap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cardLibrary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView cardLibrary = findViewById(R.id.cardLibrary);
        CardView cardHostel = findViewById(R.id.cardHostel);
        cardLibrary.setOnClickListener(v -> {

            Intent intent = new Intent(ModuleActivity.this, Library_Home_Activity.class);
            startActivity(intent);
        });

        cardHostel.setOnClickListener(v -> {

            Intent intent = new Intent(ModuleActivity.this, Hostel_Home_Activity.class);
            startActivity(intent);
        });
    }

    public void onBackPressed() {
        if (doubletap) {
            finishAffinity();
        } else {
            Toast.makeText(ModuleActivity.this, "Press again to exit",
                    Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 2000);
        }
    }
}