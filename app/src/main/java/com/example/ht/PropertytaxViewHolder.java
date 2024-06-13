package com.example.ht;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class PropertytaxViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;

    public PropertytaxViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
    }

    public void bind(PropertytaxData propertytaxData) {
        String propertytaxInfo = propertytaxData.getGroup() + propertytaxData.getValue();
        titleTextView.setText(propertytaxInfo);
    }
}

