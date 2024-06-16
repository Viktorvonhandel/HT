package com.example.ht;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PropertytaxData {
    private String group;
    private double value;

    public PropertytaxData(String group, double value) {
        this.group = group;
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static List<PropertytaxData> parseData(JSONObject jsonObject) throws JSONException {
        List<PropertytaxData> propertytaxDataList = new ArrayList<>();

        JSONObject categoryObject = jsonObject.getJSONObject("dataset")
                .getJSONObject("dimension")
                .getJSONObject("Asiakkaan oikeudellinen muoto")
                .getJSONObject("category");


        JSONArray valuesArray = jsonObject.getJSONObject("dataset").getJSONArray("value");


        JSONObject labelsObject = categoryObject.getJSONObject("label");


        Iterator<String> keys = labelsObject.keys();
        int index = 0;
        while (keys.hasNext()) {
            String key = keys.next();
            String label = labelsObject.getString(key);
            Object valueObject = valuesArray.get(index++);
            double value;
            if (valueObject instanceof Double) {
                value = (Double) valueObject;
            } else {
                value = 0;
            }
            propertytaxDataList.add(new PropertytaxData(label, value));
        }

        return propertytaxDataList;
    }

}
