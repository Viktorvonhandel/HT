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


public class VehicleFragment extends Fragment {

    private MunicipalityData municipalityData;
    private RecyclerView recyclerView;
    private VehicleDataAdapter adapter;
    private TextView titleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        titleTextView = view.findViewById(R.id.titleTextView);
        recyclerView = view.findViewById(R.id.vehicleRecyclerView);
        adapter = new VehicleDataAdapter();

        if (municipalityData != null) {
            showVehicleData();
        }

        return view;
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
        if (recyclerView != null && adapter != null) {
            showVehicleData();
        }
    }

    private void showVehicleData() {
        List<VehicleData> vehicleDataList = municipalityData.getVehicleDataList();
        adapter.setVehicleDataList(vehicleDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
