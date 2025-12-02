package com.example.hikingmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.Adapter.ObservationAdapter;
import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Observation;

import java.util.ArrayList;

public class ObservationActivity extends AppCompatActivity {

    private RecyclerView rvObservations;
    private ObservationAdapter observationAdapter;
    private ArrayList<Observation> observationList;
    private long hikeId;
    private DatabaseHelper db;
    private com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton btnAddObservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observations);

        rvObservations = findViewById(R.id.rvObservations);
        btnAddObservation = findViewById(R.id.fabAddObservation);
        db = new DatabaseHelper(this);

        hikeId = getIntent().getLongExtra("hikeId", -1);
        Log.d("ObservationActivity", "Hike ID: " + hikeId);

        observationList = new ArrayList<>();
        observationAdapter = new ObservationAdapter(this, observationList);

        rvObservations.setLayoutManager(new LinearLayoutManager(this));
        rvObservations.setAdapter(observationAdapter);

        loadObservations();

        btnAddObservation.setOnClickListener(v -> {
            Intent intent = new Intent(ObservationActivity.this, AddEditObservationActivity.class);
            intent.putExtra("hikeId", hikeId);
            startActivity(intent);
        });
    }

    private void loadObservations() {
        Log.d("ObservationActivity", "Loading observations for Hike ID: " + hikeId);
        observationList.clear();
        ArrayList<Observation> observationsFromDb = db.getObservationsByHikeId(hikeId);

        Log.d("OB  S: ", observationsFromDb.toString());
        if (observationsFromDb != null && observationsFromDb.size() > 0) {
            Log.d("ObservationActivity", "Found " + observationsFromDb.size() + " observations.");
        } else {
            Log.d("ObservationActivity", "No observations found.");
        }

        observationList.addAll(observationsFromDb);
        observationAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadObservations();
    }

}
