package com.student.usermanagementdemo.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.entities.GruhUdhyog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GruhUdhyogCreate extends AppCompatActivity {

    EditText edt_gName, edt_gAddress, edt_gContact, edt_gEmail, edt_oName, edt_oAddress;
    Button btnRegister;
    ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference gruhUdhyogReference;
    private final String refName = "gruhUdhyogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruh_udhyog_create);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register your business");

        firebaseDatabase = FirebaseDatabase.getInstance();
        gruhUdhyogReference = firebaseDatabase.getReference(refName);

        findViewById();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String gName = edt_gName.getText().toString();
                String gAddress = edt_gAddress.getText().toString();
                String gContact = edt_gContact.getText().toString();
                String gEmail = edt_gEmail.getText().toString();
                String oName = edt_oName.getText().toString();
                String oAddress = edt_oAddress.getText().toString();
                //TODO : Perform validation that you require and then store the data
                GruhUdhyog gruhUdhyog = new GruhUdhyog(gName, gAddress, gContact, gEmail, oName, oAddress);
                gruhUdhyogReference.push().setValue(gruhUdhyog)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(progressBar.getVisibility() == View.VISIBLE)
                                    progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Your business register successfully.", Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(getApplicationContext(), "Error registering your business, Try gain later.", Toast.LENGTH_LONG).show();
                                }
                                onBackPressed();
                            }
                        });
            }
        });
    }

    private void findViewById() {
        edt_gName = findViewById(R.id.edtBusinessName);
        edt_gAddress = findViewById(R.id.edtBusinessAddress);
        edt_gContact = findViewById(R.id.edtBusinessContact);
        edt_gEmail = findViewById(R.id.edtBusinessEmail);
        edt_oName = findViewById(R.id.edtBusinessOwnerName);
        edt_oAddress = findViewById(R.id.edtBusinessOwnerAddress);
        btnRegister = findViewById(R.id.btnRegisterBusiness);
        progressBar = findViewById(R.id.pBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
