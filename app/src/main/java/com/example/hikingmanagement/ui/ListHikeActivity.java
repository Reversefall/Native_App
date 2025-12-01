package com.example.hikingmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.Adapter.HikeAdapter;
import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.MainActivity;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Hike;

import java.util.ArrayList;

public class ListHikeActivity extends AppCompatActivity implements HikeAdapter.HikeListener {

    private DatabaseHelper db;
    private RecyclerView rvHikes;
    private HikeAdapter hikeAdapter;
    private ArrayList<Hike> hikeList;
    private SearchView searchView;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_hike);

        db = new DatabaseHelper(this);
        rvHikes = findViewById(R.id.rvHikes);
        searchView = findViewById(R.id.searchView); // connect SearchView
        btnBack = findViewById(R.id.btnBack);       // connect Back button

        rvHikes.setLayoutManager(new LinearLayoutManager(this));

        hikeList = new ArrayList<>();
        hikeAdapter = new HikeAdapter(hikeList, this, this);
        rvHikes.setAdapter(hikeAdapter);

        loadHike();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHikes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHikes(newText);
                return true;
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ListHikeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void searchHikes(String keyword) {
        hikeList.clear();
        hikeList.addAll(db.searchHikes(keyword)); // Make sure this method exists in DatabaseHelper
        hikeAdapter.notifyDataSetChanged();

        if (hikeList.isEmpty()) {
            Toast.makeText(this, "No hikes found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHike();
    }

    private void loadHike() {
        hikeList.clear();
        hikeList.addAll(db.getAllHikes());
        hikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEdit(Hike h) {
        Intent intent = new Intent(this, EditHikeActivity.class);
        intent.putExtra("id", h.getId());
        intent.putExtra("name", h.getName());
        intent.putExtra("location", h.getLocation());
        intent.putExtra("date", h.getDate());
        intent.putExtra("parking", h.getParking());
        intent.putExtra("length", h.getLength());
        intent.putExtra("difficulty", h.getDifficulty());
        intent.putExtra("description", h.getDescription());
        intent.putExtra("terrain", h.getTerrain());
        intent.putExtra("weather", h.getWeather());
        startActivity(intent);
    }

    @Override
    public void onDelete(Hike h) {
        boolean deleted = db.deleteHike(h.getId());
        if (deleted) {
            hikeList.remove(h);
            hikeAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Hike deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete hike", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onAddObservation(Hike h) {
        // open AddObservationActivity
        Intent intent = new Intent(ListHikeActivity.this, AddObservationActivity.class);
        intent.putExtra("hikeId", h.getId());
        startActivity(intent);
    }
}
