package com.project.daffaalmerf.uaspm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.daffaalmerf.uaspm.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent splashIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(splashIntent);
                finish();

            }
        }, 2000);

    }
}