package com.example.ht;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EconomicData {
    private List<DataPoint> dataPoints;

    public EconomicData() {
        this.dataPoints = new ArrayList<>();
    }

    public void addDataPoint(int year, double value) {
        dataPoints.add(new DataPoint(year, value));
    }

    public List<DataPoint> getDataPoints() {
        // Lajitellaan datapisteet vuosien mukaan ennen palauttamista
        Collections.sort(dataPoints, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint o1, DataPoint o2) {
                return Integer.compare(o1.getYear(), o2.getYear());
            }
        });
        return dataPoints;
    }

    public static EconomicData parseData(String jsonData, String municipalityCode) throws JSONException {
        EconomicData economicData = new EconomicData();
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("region") == Integer.parseInt(municipalityCode)) {
                int year = jsonObject.getInt("year");
                double value = jsonObject.getDouble("value");
                economicData.addDataPoint(year, value);
            }
        }
        return economicData;
    }

    public String toString() {
        return "EconomicData{" +
                "dataPoints=" + dataPoints +
                '}';
    }

    public static class DataPoint {
        private int year;
        private double value;

        public DataPoint(int year, double value) {
            this.year = year;
            this.value = value;
        }

        public int getYear() {
            return year;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{" +
                    "year=" + year +
                    ", value=" + value +
                    '}';
        }
    }
}


