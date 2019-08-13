package com.example.stuar.myroundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.stuar.myroundapp.Other.MainPageActivity;
import com.example.stuar.myroundapp.SignUpAndLogInActivities.LogIn;


public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainPageActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
