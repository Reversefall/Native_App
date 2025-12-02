package com.example.hikingmanagement.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast; // Thêm Toast để thông báo

import androidx.appcompat.app.AppCompatActivity;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Observation;

public class AddEditObservationActivity extends AppCompatActivity {

    private EditText edtObservation, edtTime, edtComments;
    private Button btnSave;
    private DatabaseHelper db;
    private long hikeId;
    private long observationId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_observation);

        edtObservation = findViewById(R.id.edtObservation);
        edtTime = findViewById(R.id.edtTime);
        edtComments = findViewById(R.id.edtComments);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        hikeId = getIntent().getLongExtra("hikeId", -1);
        observationId = getIntent().getLongExtra("observationId", -1);

        if (observationId != -1) {
            Observation observation = db.getObservation(observationId);
            if (observation != null) {
                edtObservation.setText(observation.getObservation());
                edtTime.setText(observation.getTime());
                edtComments.setText(observation.getComments());
            }
        }

        btnSave.setOnClickListener(v -> {
            String observationText = edtObservation.getText().toString();
            String time = edtTime.getText().toString();
            String comments = edtComments.getText().toString();

            if (observationId == -1) {
                Observation observation = new Observation(hikeId, observationText, time, comments);
                long result = db.addObservation(observation);
                if (result > 0) {
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
            } else {

                Observation observation = new Observation(hikeId, observationText, time, comments);

                observation.setId(observationId);

                int rows = db.updateObservation(observation);
                if (rows > 0) {
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }
}