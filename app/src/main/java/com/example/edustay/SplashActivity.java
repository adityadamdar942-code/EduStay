package com.example.edustay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    //classname object name
    LinearLayout llmain;
    ImageView imagemain;
    TextView tvmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //objectname = findViewById(R.id.idname);

        llmain = findViewById(R.id.llmain);
        imagemain = findViewById(R.id.imagemain);
        tvmain = findViewById(R.id.tvmain);

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                //Classname objectname = new constructorname(FirstActivity.this,secondActivity.class);
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },  3000);


    }

}