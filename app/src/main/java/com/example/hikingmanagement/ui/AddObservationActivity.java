package com.example.hikingmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Hike;
import com.example.hikingmanagement.model.Observation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddObservationActivity extends AppCompatActivity {

    private EditText etObservation, etTime, etComments;
    private Button btnSaveObservation;
    private DatabaseHelper db;
    private long hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        etObservation = findViewById(R.id.etObservation);
        etTime = findViewById(R.id.etTime);
        etComments = findViewById(R.id.etComments);
        btnSaveObservation = findViewById(R.id.btnSaveObservation);

        db = new DatabaseHelper(this);

        hikeId = getIntent().getLongExtra("hikeId", -1);

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        etTime.setText(currentTime);

        btnSaveObservation.setOnClickListener(v -> {
            String obsText = etObservation.getText().toString().trim();
            String comments = etComments.getText().toString().trim();

            if (obsText.isEmpty()) {
                Toast.makeText(this, "Observation is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            Observation obs = new Observation(hikeId, obsText, etTime.getText().toString(), comments);
            long result = db.addObservation(obs);

            if (result != -1) {
                Toast.makeText(this, "Observation saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save observation", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
