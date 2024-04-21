
// PopulationData.java
package com.example.ht;

import org.json.JSONException;
import org.json.JSONObject;

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
        int population = jsonObject.getInt("population");
        double populationChange = jsonObject.getDouble("populationChange");
        double jobSelfSufficiency = jsonObject.getDouble("jobSelfSufficiency");
        double employmentRate = jsonObject.getDouble("employmentRate");
        return new PopulationData(population, populationChange, jobSelfSufficiency, employmentRate);
    }
}
