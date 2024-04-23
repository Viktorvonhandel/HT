
// PopulationData.java
package com.example.ht;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class PopulationData {
    private int population;
    private double populationChange;
    private double jobSelfSufficiency;
    private double employmentRate;

    public PopulationData(int population, double populationChange, double jobSelfSufficiency, double employmentRate) {
        this.population = population;
        this.populationChange = populationChange;
        this.jobSelfSufficiency = jobSelfSufficiency;
        this.employmentRate = employmentRate;
    }

    public int getPopulation() {
        return population;
    }

    public double getPopulationChange() {
        return populationChange;
    }

    public double getJobSelfSufficiency() {
        return jobSelfSufficiency;
    }

    public double getEmploymentRate() {
        return employmentRate;
    }

    public static PopulationData parseData(String rawData) throws JSONException {
        JSONObject jsonObject = new JSONObject(rawData);
        JSONArray values = jsonObject.getJSONArray("value");
        int population = values.getInt(0);
        double populationChange = values.getDouble(1);
        double jobSelfSufficiency = values.getDouble(2);
        double employmentRate = values.getDouble(3);
        return new PopulationData(population, populationChange, jobSelfSufficiency, employmentRate);
    }
    @Override
    public String toString() {
        return "PopulationData{" +
                "population=" + population +
                ", populationChange=" + populationChange +
                ", jobSelfSufficiency=" + jobSelfSufficiency +
                ", employmentRate=" + employmentRate +
                '}';
    }

}
