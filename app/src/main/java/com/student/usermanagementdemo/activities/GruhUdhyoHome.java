package com.student.usermanagementdemo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.usermanagementdemo.R;
import com.student.usermanagementdemo.adapters.GruhUdhyogAdapter;
import com.student.usermanagementdemo.entities.GruhUdhyog;

import java.util.ArrayList;
import java.util.List;

public class GruhUdhyoHome extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton createFabIcon;
    GruhUdhyogAdapter gruhUdhyogAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference gruhUdhyogReference;
    private List<GruhUdhyog> gruhUdhyogList;
    ProgressBar pBarLinear;
    private final String refName = "gruhUdhyogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruh_udhyo_home);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gruh Udhyogs");

        firebaseDatabase = FirebaseDatabase.getInstance();
        gruhUdhyogReference = firebaseDatabase.getReference(refName);

        createFabIcon = findViewById(R.id.fab_create);
        createFabIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GruhUdhyoHome.this, GruhUdhyogCreate.class));
            }
        });

        pBarLinear = findViewById(R.id.pBarLinear);
        recyclerView = findViewById(R.id.recyclerView);
        gruhUdhyogList = new ArrayList<>();
        gruhUdhyogAdapter = new GruhUdhyogAdapter(gruhUdhyogList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gruhUdhyogAdapter);

        gruhUdhyogReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pBarLinear.setVisibility(View.VISIBLE);
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        gruhUdhyogList.add(postSnapshot.getValue(GruhUdhyog.class));
                    }
                    gruhUdhyogAdapter.notifyDataSetChanged();
                }
                pBarLinear.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pBarLinear.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Something went wrong. Try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
