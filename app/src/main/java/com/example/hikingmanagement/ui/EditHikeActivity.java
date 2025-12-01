package com.example.hikingmanagement.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Hike;

public class EditHikeActivity extends AppCompatActivity {

    private EditText etName, etLocation, etDate, etDistance, etDescription, etWeather;
    private Spinner spDifficulty;
    private RadioGroup rgParking;
    private Button btnUpdate;
    private DatabaseHelper db;

    private long hikeId; // Use long for IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDistance = findViewById(R.id.etDistance);
        etDescription = findViewById(R.id.etDescription);
        etWeather = findViewById(R.id.etWeather);
        spDifficulty = findViewById(R.id.spDifficulty);
        rgParking = findViewById(R.id.rgParking);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new DatabaseHelper(this);

        // Get data from Intent
        hikeId = getIntent().getLongExtra("id", -1);
        Log.d("EditHike", "Received hikeId: " + hikeId);

        etName.setText(getIntent().getStringExtra("name"));
        etLocation.setText(getIntent().getStringExtra("location"));
        etDate.setText(getIntent().getStringExtra("date"));
        etDistance.setText(String.valueOf(getIntent().getDoubleExtra("length", 0)));
        etDescription.setText(getIntent().getStringExtra("description"));
        etWeather.setText(getIntent().getStringExtra("weather"));

        String difficulty = getIntent().getStringExtra("difficulty");
        String parking = getIntent().getStringExtra("parking");

        // Set Spinner selection
        for (int i = 0; i < spDifficulty.getCount(); i++) {
            if (spDifficulty.getItemAtPosition(i).toString().equals(difficulty)) {
                spDifficulty.setSelection(i);
                break;
            }
        }

        // Set RadioGroup selection
        if ("yes".equals(parking)) {
            rgParking.check(R.id.Yes);
        } else {
            rgParking.check(R.id.No);
        }

        // Update button click
        btnUpdate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String distanceStr = etDistance.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String weather = etWeather.getText().toString().trim();
            String newDifficulty = spDifficulty.getSelectedItem().toString();

            int selectedId = rgParking.getCheckedRadioButtonId();
            String newParking = (selectedId == R.id.Yes) ? "yes" : "no";

            if (name.isEmpty() || location.isEmpty() || date.isEmpty() || distanceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double distance;
            try {
                distance = Double.parseDouble(distanceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid distance value", Toast.LENGTH_SHORT).show();
                return;
            }

            // Log values to debug
            Log.d("EditHike", "Updating hike: ID=" + hikeId + ", Name=" + name +
                    ", Location=" + location + ", Distance=" + distance + ", Parking=" + newParking);

            // Create Hike object
            Hike hike = new Hike();
            hike.setId(hikeId);
            hike.setName(name);
            hike.setLocation(location);
            hike.setDate(date);
            hike.setLength(distance);
            hike.setDescription(description);
            hike.setDifficulty(newDifficulty);
            hike.setWeather(weather);
            hike.setParking(newParking);

            // Update database
            int rowsUpdated = db.updateHike(hike);
            Log.d("EditHike", "Rows updated: " + rowsUpdated);

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Hike updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update hike", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
