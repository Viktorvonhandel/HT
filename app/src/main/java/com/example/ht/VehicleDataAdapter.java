package com.example.ht;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleDataAdapter extends RecyclerView.Adapter<VehicleDataAdapter.VehicleViewHolder> {

    private List<VehicleData> vehicleDataList;

    public VehicleDataAdapter(List<VehicleData> vehicleDataList) {
        this.vehicleDataList = vehicleDataList;
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

    static class VehicleViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.vehicleRecyclerView);
        }

        public void bind(VehicleData vehicleData) {
            textView.setText(vehicleData.toString());
        }
    }
}
