package com.example.ht;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.databinding.FragmentEconomicBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            // Update TextViews with debt ratios
            updateDebtRatioTextViews(economicData);

            // Update LineChart with debt ratio data
            updateLineChart(economicData);
        }
    }

    private void updateDebtRatioTextViews(EconomicData economicData) {
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

    private void updateLineChart(EconomicData economicData) {
        LineChart lineChart = binding.lineChart;

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, (float) economicData.getDebtRatio1()));
        entries.add(new Entry(2, (float) economicData.getDebtRatio2()));
        entries.add(new Entry(3, (float) economicData.getDebtRatio3()));
        entries.add(new Entry(4, (float) economicData.getDebtRatio4()));
        entries.add(new Entry(5, (float) economicData.getDebtRatio5()));

        LineDataSet dataSet = new LineDataSet(entries, "Debt Ratios over Years");
        dataSet.setColor(0xFFC6FF00);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setCircleRadius(5f);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);



        lineChart.animateY(1000);
        lineChart.invalidate(); // refresh chart
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

