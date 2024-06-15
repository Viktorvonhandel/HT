package com.example.ht;

import org.json.JSONException;
import org.json.JSONObject;


import org.json.JSONArray;
public class WeatherData {
    private String cityName;
    private double temperature;
    private String iconCode;

    public WeatherData(String cityName, double temperature, String iconCode) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.iconCode = iconCode;
    }

    public static WeatherData parseData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String cityName = jsonObject.getString("name");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            double temperature = mainObject.getDouble("temp");
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String iconCode = weatherObject.getString("icon");
            return new WeatherData(cityName, temperature, iconCode);
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // Palautetaan null virhetilanteessa
        }

    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getIconCode() {
        return iconCode;
    }


    @Override
    public String toString() {
        return "WeatherData{" +
                cityName + '\'' +
                 temperature +
                iconCode + '\'' +
                '}';
    }
}