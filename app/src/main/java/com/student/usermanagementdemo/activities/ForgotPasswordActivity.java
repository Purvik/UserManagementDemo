package com.student.usermanagementdemo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.utils.AppHelpers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnSendEmail;
    FirebaseAuth firebaseAuth;
    String email;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmail);
        progressBar = findViewById(R.id.progressBar);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                if (AppHelpers.isValidEmail(email)) {
                    sendPasswordResetEmail(email);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password reset Email sent. You can reset password from it", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else{
                            Toast.makeText(getApplicationContext(), "Something went wrong. Try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
