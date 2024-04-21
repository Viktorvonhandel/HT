package com.example.ht;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
public class VehicleFragment extends Fragment {

    private MunicipalityData municipalityData;

    public VehicleFragment() {
        // Tyhjä julkisen rakentajan tarvitaan
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (municipalityData != null) {
            List<VehicleData> vehicleDataList = municipalityData.getVehicleDataList();

            // Näytä autotiedot RecyclerView:n avulla
            RecyclerView recyclerView = view.findViewById(R.id.vehicleRecyclerView);
            VehicleDataAdapter adapter = new VehicleDataAdapter(vehicleDataList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
    }
}
