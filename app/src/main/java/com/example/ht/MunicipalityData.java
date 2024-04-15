package com.example.ht;

public class MunicipalityData {
    private PopulationData populationData;
    private WeatherData weatherData;
    private VehicleData vehicleData;
    private TrafficData trafficData;

    public MunicipalityData(PopulationData populationData, WeatherData weatherData, VehicleData vehicleData, TrafficData traficData) {
        this.populationData = populationData;
        this.weatherData = weatherData;
        this.vehicleData = vehicleData;
        this.trafficData = trafficData;
    }

    // Getters and setters
    public PopulationData getPopulationData() {
        return populationData;
    }

    public void setPopulationData(PopulationData populationData) {
        this.populationData = populationData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public VehicleData getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(VehicleData vehicleData) {
        this.vehicleData = vehicleData;
    }

    public TrafficData getTrafficData() {
        return trafficData;
    }

    public void setTraficData(TrafficData traficData) {
        this.trafficData = traficData;
    }
}

