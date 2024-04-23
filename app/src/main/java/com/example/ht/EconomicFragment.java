package com.example.ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ht.databinding.FragmentEconomicBinding;

public class EconomicFragment extends Fragment {

    private FragmentEconomicBinding binding;
    private MunicipalityData municipalityData;

    public EconomicFragment(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEconomicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (municipalityData != null) {
            updateUI();
        }
    }

    public void setMunicipalityData(MunicipalityData data) {
        municipalityData = data;
        if (getView() != null) {
            updateUI();
        }
    }

    private void updateUI() {
        if (municipalityData != null) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

