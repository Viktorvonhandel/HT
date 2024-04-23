package com.example.ht;

import java.util.List;

public class MunicipalityData {
    private PopulationData populationData;
    private WeatherData weatherData;
    private List<VehicleData> vehicleDataList;
    private EconomicData economicData;

    public MunicipalityData(PopulationData populationData, WeatherData weatherData, List<VehicleData> vehicleDataList, EconomicData economicData) { // Muutettu tässä riviä
        this.populationData = populationData;
        this.weatherData = weatherData;
        this.vehicleDataList = vehicleDataList;
        this.economicData = economicData;
    }

    public PopulationData getPopulationData() {
        return populationData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public List<VehicleData> getVehicleDataList() {
        return vehicleDataList;
    }

    public EconomicData getEconomicData() {
        return economicData;
    }


}


