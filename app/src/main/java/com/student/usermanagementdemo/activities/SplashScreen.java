package com.student.usermanagementdemo.activities;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.student.usermanagementdemo.R;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser == null) {
                    //If user not signed in
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                } else {
                    //if user already signed in
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}
