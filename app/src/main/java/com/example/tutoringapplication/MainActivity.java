package com.example.tutoringapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Splash screen: animated logo, then on to the login screen. */
public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.logoIV);
        TextView welcome = findViewById(R.id.welcomeTV);

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        logo.setAnimation(rotate);
        welcome.setAnimation(fade);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            finish();
        }, SPLASH_DELAY_MS);
    }
}
