package com.example.ht;

import java.util.List;

import java.io.Serializable;
import java.util.List;

public class MunicipalityData implements Serializable {
    private PopulationData populationData;
    private WeatherData weatherData;
    private List<PropertytaxData> propertytaxDataList;
    private EconomicData economicData;

    public MunicipalityData(PopulationData populationData, WeatherData weatherData, List<PropertytaxData> propertytaxDataList, EconomicData economicData) {
        this.populationData = populationData;
        this.weatherData = weatherData;
        this.propertytaxDataList = propertytaxDataList;
        this.economicData = economicData;
    }

    public PopulationData getPopulationData() {
        return populationData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public List<PropertytaxData> getPropertytaxDataList() {
        return propertytaxDataList;
    }

    public EconomicData getEconomicData() {
        return economicData;
    }
}


