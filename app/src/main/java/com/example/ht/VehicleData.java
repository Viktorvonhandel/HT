// VehicleData.java
package com.example.ht;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VehicleData {
    private String brand;
    private int quantity;

    public VehicleData(String brand, int quantity) {
        this.brand = brand;
        this.quantity = quantity;
    }

    public static List<VehicleData> parseData(String rawData) throws JSONException {
        List<VehicleData> vehicleDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(rawData);
        JSONArray valueArray = jsonObject.getJSONArray("value");

        JSONObject brandObject = jsonObject.getJSONObject("Merkki");
        JSONArray brandLabels = brandObject.getJSONArray("category").getJSONObject(0).getJSONArray("label");
        JSONArray brandValues = brandObject.getJSONArray("category").getJSONObject(0).getJSONArray("index");

        for (int i = 1; i < brandLabels.length(); i++) { // Start from 1 to skip the "YH" label
            String brandLabel = brandLabels.getString(i);
            int index = brandValues.getInt(i);
            int quantity = valueArray.getInt(index);
            vehicleDataList.add(new VehicleData(brandLabel, quantity));
        }

        // Sort the list based on quantity (descending order)
        vehicleDataList.sort((v1, v2) -> Integer.compare(v2.getQuantity(), v1.getQuantity()));

        // Keep only the top 10 brands
        if (vehicleDataList.size() > 10) {
            vehicleDataList = vehicleDataList.subList(0, 10);
        }

        return vehicleDataList;
    }
    public String getBrand() {
        return brand;
    }

    public int getQuantity() {
        return quantity;
    }
}
