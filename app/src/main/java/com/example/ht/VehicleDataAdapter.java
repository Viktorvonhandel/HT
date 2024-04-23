package com.example.ht;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VehicleDataAdapter extends RecyclerView.Adapter<VehicleViewHolder> {

    private List<VehicleData> vehicleDataList;

    public VehicleDataAdapter() {
        this.vehicleDataList = new ArrayList<>();
    }

    public void setVehicleDataList(List<VehicleData> vehicleDataList) {
        this.vehicleDataList = vehicleDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        VehicleData vehicleData = vehicleDataList.get(position);
        holder.bind(vehicleData);
    }

    @Override
    public int getItemCount() {
        return vehicleDataList.size();
    }
}
