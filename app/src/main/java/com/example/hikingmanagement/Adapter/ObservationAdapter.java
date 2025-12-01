package com.example.hikingmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikingmanagement.R;
import com.example.hikingmanagement.model.Observation;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.VH> {

    private ArrayList<Observation> list;
    private Context context;

    public ObservationAdapter(ArrayList<Observation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvObservation, tvTime, tvComments;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvObservation = itemView.findViewById(R.id.tvObservation);
            tvTime = itemView.findViewById(R.id.tvObservationTime);
            tvComments = itemView.findViewById(R.id.tvObservationComments);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_observation, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Observation obs = list.get(position);
        holder.tvObservation.setText(obs.getObservation());
        holder.tvTime.setText(obs.getTime());

        if (obs.getComments() != null && !obs.getComments().isEmpty()) {
            holder.tvComments.setVisibility(View.VISIBLE);
            holder.tvComments.setText(obs.getComments());
        } else {
            holder.tvComments.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
