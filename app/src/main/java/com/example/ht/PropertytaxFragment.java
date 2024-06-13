package com.example.ht;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.TextView;


public class PropertytaxFragment extends Fragment {

    private MunicipalityData municipalityData;
    private RecyclerView recyclerView;
    private PropertytaxDataAdapter adapter;
    private TextView titleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_propertytax, container, false);
        titleTextView = view.findViewById(R.id.titleTextView);
        recyclerView = view.findViewById(R.id.propertytaxRecyclerView);

        adapter = new PropertytaxDataAdapter();

        if (municipalityData != null) {
            showPropertytaxData();
        }

        return view;
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
        if (recyclerView != null && adapter != null) {
            showPropertytaxData();
        }
    }

    private void showPropertytaxData() {
        List<PropertytaxData> propertytaxDataList = municipalityData.getPropertytaxDataList();
        adapter.setPropertytaxDataList(propertytaxDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
