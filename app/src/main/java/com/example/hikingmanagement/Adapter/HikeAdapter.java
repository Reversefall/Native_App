package com.example.hikingmanagement.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Hike;

import java.util.ArrayList;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.VH> {

    private HikeListener listener;
    private ArrayList<Hike> HikeList;
    private Context context;

    public interface HikeListener {
        void onEdit(Hike h);
        void onDelete(Hike h);
        void onAddObservation(Hike h);
    }

    public HikeAdapter(ArrayList<Hike> hikeList, Context context, HikeListener listener) {
        this.context = context;
        this.HikeList = hikeList;
        this.listener = listener;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvLocationDate, tvDistance;
        Button btnEdit, btnDelete, btnObservation;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocationDate = itemView.findViewById(R.id.tvLocationDate);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnObservation = itemView.findViewById(R.id.btnObservation);
        }
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hike, parent, false);
        return new VH(view); // ✅ FIXED: return the ViewHolder, not null
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Hike hike = HikeList.get(position);

        holder.tvName.setText(hike.getName() + " • " + hike.getDifficulty());
        holder.tvLocationDate.setText(hike.getLocation() + " • " + hike.getDate());
        holder.tvDistance.setText("Distance: " + hike.getLength() + " km");

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(hike);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(hike);
        });
        holder.btnObservation.setOnClickListener(v -> {
            if (listener != null) listener.onAddObservation(hike);
        });



    }



    @Override
    public int getItemCount() {
        return HikeList.size();
    }
}
