package com.example.hikingmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Hike;

public class AddHikeActivity extends AppCompatActivity {
    private EditText etName, etLocation, etDate, etDistance, etDescription, etWeather;
    private Spinner spDifficulty;
    private RadioGroup rgParking;
    private Button btnSave;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_hike);

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDistance = findViewById(R.id.etDistance);
        etDescription = findViewById(R.id.etDescription);
        etWeather = findViewById(R.id.etWeather);
        spDifficulty = findViewById(R.id.spDifficulty);
        rgParking = findViewById(R.id.rgParking);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String distanceStr = etDistance.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String weather = etWeather.getText().toString().trim();
            String difficulty = spDifficulty.getSelectedItem().toString();

            int selectedId = rgParking.getCheckedRadioButtonId();
            String parking = (selectedId == R.id.Yes) ? "yes" : "no";

            if(name.isEmpty() || location.isEmpty() || date.isEmpty() || distanceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double distance = Double.parseDouble(distanceStr);

            Hike hike = new Hike(name, location, date, distance, description, difficulty, weather, parking);

            long result = db.addHike(hike);

            if(result != -1) {
                Toast.makeText(this, "Hike added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddHikeActivity.this, ListHikeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add hike", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
