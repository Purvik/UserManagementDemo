package com.student.usermanagementdemo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.utils.AppHelpers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();
    TextView tvLoginHere;
    private EditText edtEmail, edtPassword;
    private Button btnRegister;
    private String email, password;
    private boolean isValidEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById();

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starting a new activity will push it on the stack
                //Options:
                //1. Clear Previous stack and push Login activity
                //2. Go back to login Activity

                // startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                onBackPressed();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndValidateEmailPassword();
            }
        });
    }

    private void getAndValidateEmailPassword() {
        email = edtEmail.getText().toString();
        isValidEmail = AppHelpers.isValidEmail(email);
        if (isValidEmail) {
            Log.d(TAG, "getAndValidateEmailPassword: VALID EMAIL");
            password = edtPassword.getText().toString();
            performUserRegistration(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect Email", Toast.LENGTH_LONG).show();
        }
    }

    private void performUserRegistration(String email, String password) {
        Log.d(TAG, "performUserLogin: PERFORMING REGISTRATION");
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful. Login now.", Toast.LENGTH_LONG).show();
                            sendVerificationEmailToUser();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationEmailToUser() {
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration done. Check inbox for verification email", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }
                });
    }

    private void findViewById() {
        tvLoginHere = findViewById(R.id.tvLogIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


