package com.example.hikingmanagement.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.Adapter.ObservationAdapter;
import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Observation;

import java.util.ArrayList;

public class ViewObservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ObservationAdapter adapter;
    private DatabaseHelper db;
    private long hikeId;
    private ArrayList<Observation> observationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observations);

        recyclerView = findViewById(R.id.recyclerViewObservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        hikeId = getIntent().getLongExtra("hikeId", -1);

        observationList = db.getObservationsByHikeId(hikeId);

        adapter = new ObservationAdapter(observationList, this);
        recyclerView.setAdapter(adapter);
    }
}
