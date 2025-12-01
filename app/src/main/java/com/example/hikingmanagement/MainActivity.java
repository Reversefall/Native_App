package com.example.hikingmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.ui.AddHikeActivity;
import com.example.hikingmanagement.ui.AddObservationActivity;
import com.example.hikingmanagement.ui.ListHikeActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        Button btnAdd = findViewById(R.id.btnAddHike);
        Button btnView = findViewById(R.id.btnViewAll);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnObservation = findViewById(R.id.btnObservation);
        btnObservation.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddObservationActivity.class);
            startActivity(intent);
        });
        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddHikeActivity.class)));
        btnView.setOnClickListener(v ->startActivity(new Intent(this, ListHikeActivity.class)));

    }
}