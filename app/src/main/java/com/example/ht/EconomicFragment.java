package com.example.ht;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
            // Update LineChart with debt ratio data
            updateLineChart(economicData);
        }
    }

    private void updateLineChart(EconomicData economicData) {
        LineChart lineChart = binding.lineChart;

        List<Entry> entries = new ArrayList<>();
        List<EconomicData.DataPoint> dataPoints = economicData.getDataPoints();

        // Use a TreeMap to maintain order and uniqueness of years
        Map<Integer, Float> uniqueYearsMap = new TreeMap<>();

        for (int i = 0; i < dataPoints.size(); i++) {
            EconomicData.DataPoint dataPoint = dataPoints.get(i);
            uniqueYearsMap.put(dataPoint.getYear(), (float) dataPoint.getValue());
        }

        int index = 0;
        for (Map.Entry<Integer, Float> entry : uniqueYearsMap.entrySet()) {
            entries.add(new Entry(index++, entry.getValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "The development of relative indebtedness over a 5-year period");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setCircleColor(Color.GREEN);
        dataSet.setCircleRadius(5f);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);



        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.BLACK);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.BLACK);

        // Convert unique years to an array of strings for the x-axis labels
        final String[] xLabels = uniqueYearsMap.keySet().stream().map(String::valueOf).toArray(String[]::new);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(xLabels.length);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setTextColor(Color.BLACK);
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        lineChart.animateY(1000);
        lineChart.invalidate(); // refresh the chart
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


