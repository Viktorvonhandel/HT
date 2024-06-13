package com.example.ht;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PropertytaxDataAdapter extends RecyclerView.Adapter<PropertytaxViewHolder> {

    private List<PropertytaxData> propertytaxDataList;

    public PropertytaxDataAdapter() {
        this.propertytaxDataList = new ArrayList<>();
    }

    public void setPropertytaxDataList(List<PropertytaxData> propertytaxDataList) {
        this.propertytaxDataList = propertytaxDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PropertytaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_propertytax, parent, false);
        return new PropertytaxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertytaxViewHolder holder, int position) {
        PropertytaxData propertytaxData = propertytaxDataList.get(position);
        holder.bind(propertytaxData);
    }

    @Override
    public int getItemCount() {
        return propertytaxDataList.size();
    }
}
