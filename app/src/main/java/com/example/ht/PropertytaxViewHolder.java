package com.example.ht;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PropertytaxViewHolder extends RecyclerView.ViewHolder {

    public TextView groupTextView;
    public TextView valueTextView;

    public PropertytaxViewHolder(@NonNull View itemView) {
        super(itemView);
        groupTextView = itemView.findViewById(R.id.groupTextView);
        valueTextView = itemView.findViewById(R.id.valueTextView);
    }

    public void bind(PropertytaxData propertytaxData) {
        groupTextView.setText(propertytaxData.getGroup());
        valueTextView.setText(String.valueOf(propertytaxData.getValue()) + "%");
    }
}

