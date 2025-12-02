package com.example.hikingmanagement.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.Database.DatabaseHelper;
import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Observation;
import com.example.hikingmanagement.ui.AddEditObservationActivity;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder> {

    private ArrayList<Observation> observationList;
    private Context context;
    private DatabaseHelper db;

    public ObservationAdapter(Context context, ArrayList<Observation> observationList) {
        this.context = context;
        this.observationList = observationList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_observation, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        Observation observation = observationList.get(position);
        holder.tvObservation.setText(observation.getObservation());
        holder.tvTime.setText(observation.getTime());

        if (observation.getComments() != null && !observation.getComments().isEmpty()) {
            holder.tvComments.setText(observation.getComments());
            holder.tvComments.setVisibility(View.VISIBLE);
        } else {
            holder.tvComments.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditObservationActivity.class);
            intent.putExtra("hikeId", observation.getHikeId());
            intent.putExtra("observationId", observation.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(observation, position);
        });
    }


    private void showDeleteConfirmationDialog(Observation observation, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Observation")
                .setMessage("Are you sure you want to delete this observation?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted = db.deleteObservation(observation.getId());

                    if (isDeleted) {
                        observationList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, observationList.size());
                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }

    public static class ObservationViewHolder extends RecyclerView.ViewHolder {
        TextView tvObservation, tvTime, tvComments;
        Button btnEdit, btnDelete;

        public ObservationViewHolder(View itemView) {
            super(itemView);
            tvObservation = itemView.findViewById(R.id.tvObservation);
            tvTime = itemView.findViewById(R.id.tvObservationTime);
            tvComments = itemView.findViewById(R.id.tvObservationComments);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
