package com.student.usermanagementdemo.activities;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    private TextView tvNewUser, tvForgotPassword;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private String email, password;
    private boolean isValidEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById();

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
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
            performUserLogin(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect Email", Toast.LENGTH_LONG).show();
        }
    }

    private void performUserLogin(final String email, String password) {
        Log.d(TAG, "performUserLogin: PERFORMING LOGIN");
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser.isEmailVerified()) {
                                Intent intent = new Intent(LoginActivity.this, UserInformationActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else {
                                firebaseAuth.signOut();
                                Toast.makeText(getApplicationContext(), "Please verify your email from mail we've sent at time of registration.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void findViewById() {
        tvNewUser = findViewById(R.id.tvNewUser);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
    }
}
