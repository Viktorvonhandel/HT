package com.example.ht;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class VehicleViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;

    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
    }

    public void bind(VehicleData vehicleData) {
        String vehicleInfo = vehicleData.getGroup() + vehicleData.getValue();
        titleTextView.setText(vehicleInfo);
    }
}

