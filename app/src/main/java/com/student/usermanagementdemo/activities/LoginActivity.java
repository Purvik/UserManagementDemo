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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.entities.User;
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
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;
    private ProgressBar progressBar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("users");

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
                                checkForUserInfoRegistration(email);
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

    /* This method checks if email id is already available with user information on Firebase Database */
    private void checkForUserInfoRegistration(final String email) {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isAvail = false;
                for(DataSnapshot postSnashot: dataSnapshot.getChildren()){
                    user = postSnashot.getValue(User.class);
                    if(user.getEmail().equalsIgnoreCase(email)){
                        isAvail = true;
                        break;
                    }
                }
                if (isAvail) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(LoginActivity.this, UserInformationActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Something went wrong, try again", Toast.LENGTH_LONG).show();
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
