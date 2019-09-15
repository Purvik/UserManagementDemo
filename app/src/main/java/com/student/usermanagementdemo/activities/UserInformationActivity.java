package com.student.usermanagementdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.entities.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserInformationActivity extends AppCompatActivity {

    EditText edtName, edtPhoneNo, edtEmail, edtPostalAddress;
    String name, phoneNo, email, postalAddress;
    Button btnAdd;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        email = getIntent().getStringExtra("email");
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("users");

        findViewById();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushUserInfoToFirebase();
            }
        });
    }

    private void pushUserInfoToFirebase() {
        email = edtEmail.getText().toString();
        name = edtName.getText().toString();
        phoneNo = edtPhoneNo.getText().toString();
        postalAddress = edtPostalAddress.getText().toString();
        User user = new User(name, email, phoneNo, postalAddress, "key");
        userReference.push().setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User details stored.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UserInformationActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong, try again..", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void findViewById() {
        edtName = findViewById(R.id.edtName);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtEmail = findViewById(R.id.edtEmail);
        edtEmail.setText(email.length() != 0 ? email : "can\'t get your email");
        edtPostalAddress = findViewById(R.id.edtPostalAddress);
        btnAdd = findViewById(R.id.btnAdd);
    }
}
