package com.example.ht;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        EconomicData economicData = municipalityData.getEconomicData();
        if (economicData != null) {
            TextView debtRatio1TextView = binding.debtRatio1TextView;
            TextView debtRatio2TextView = binding.debtRatio2TextView;
            TextView debtRatio3TextView = binding.debtRatio3TextView;
            TextView debtRatio4TextView = binding.debtRatio4TextView;
            TextView debtRatio5TextView = binding.debtRatio5TextView;

            debtRatio1TextView.setText("Debt Ratio 1: " + economicData.getDebtRatio1());
            debtRatio2TextView.setText("Debt Ratio 2: " + economicData.getDebtRatio2());
            debtRatio3TextView.setText("Debt Ratio 3: " + economicData.getDebtRatio3());
            debtRatio4TextView.setText("Debt Ratio 4: " + economicData.getDebtRatio4());
            debtRatio5TextView.setText("Debt Ratio 5: " + economicData.getDebtRatio5());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
