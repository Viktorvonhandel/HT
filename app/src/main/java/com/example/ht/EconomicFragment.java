package com.example.ht;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;



import java.util.ArrayList;
import java.util.List;

public class EconomicFragment extends Fragment {

    private LineChart lineChart;
    private TextView wfclientsTextView;
    private MunicipalityData municipalityData;

    public EconomicFragment(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_economic, container, false);

        wfclientsTextView = view.findViewById(R.id.wfclientsTextView);
        lineChart = view.findViewById(R.id.lineChart);

        // Voit hakea tarvittavat tiedot ja asettaa kaavioon täällä
        setupLineChart();

        return view;
    }

    private void setupLineChart() {
        if (municipalityData != null && municipalityData.getEconomicData() != null) {
            EconomicData economicData = municipalityData.getEconomicData();

            // Näytä tekstinä kaikki debtRatiot
            wfclientsTextView.setText("Debt Ratios:\n" +
                    "1: " + economicData.getDebtRatio1() + "\n" +
                    "2: " + economicData.getDebtRatio2() + "\n" +
                    "3: " + economicData.getDebtRatio3() + "\n" +
                    "4: " + economicData.getDebtRatio4() + "\n" +
                    "5: " + economicData.getDebtRatio5());

            List<Entry> entries = new ArrayList<>();
            // Lisää kaavioon halutut tiedot tässä
            entries.add(new Entry(1, (float) economicData.getDebtRatio1()));
            entries.add(new Entry(2, (float) economicData.getDebtRatio2()));
            entries.add(new Entry(3, (float) economicData.getDebtRatio3()));
            entries.add(new Entry(4, (float) economicData.getDebtRatio4()));
            entries.add(new Entry(5, (float) economicData.getDebtRatio5()));

            LineDataSet dataSet = new LineDataSet(entries, "Economic Data");
            dataSet.setColor(Color.BLUE);
            dataSet.setValueTextColor(Color.RED);

            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);

            Description description = new Description();
            description.setText("Economic Data Chart");
            lineChart.setDescription(description);

            lineChart.invalidate(); // Päivitä kaavio
        }
    }
}

