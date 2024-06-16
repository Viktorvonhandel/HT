package com.example.ht;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EconomicData {
    private double debtRatio1;
    private double debtRatio2;
    private double debtRatio3;
    private double debtRatio4;
    private double debtRatio5;

    public EconomicData(double debtRatio1, double debtRatio2, double debtRatio3, double debtRatio4, double debtRatio5) {
        this.debtRatio1 = debtRatio1;
        this.debtRatio2 = debtRatio2;
        this.debtRatio3 = debtRatio3;
        this.debtRatio4 = debtRatio4;
        this.debtRatio5 = debtRatio5;
    }

    public double getDebtRatio1() {
        return debtRatio1;
    }

    public double getDebtRatio2() {
        return debtRatio2;
    }

    public double getDebtRatio3() {
        return debtRatio3;
    }

    public double getDebtRatio4() {
        return debtRatio4;
    }

    public double getDebtRatio5() {
        return debtRatio5;
    }

    public static EconomicData parseData(String jsonData, String municipalityCode) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        double[] debtRatios = new double[5];
        int count = 0;



        for (int i = 0; i < jsonArray.length(); i++) {
            if (count >= 5) {
                break;
            }

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("region") == Integer.parseInt(municipalityCode)) {
                debtRatios[count] = jsonObject.getDouble("value");
                count++;
            }
        }

        // Täytetään puuttuvat arvot nollilla, jos vähemmän kuin 5 arvoa löytyy
        for (int i = count; i < 5; i++) {
            debtRatios[i] = 0;
        }

        return new EconomicData(debtRatios[0], debtRatios[1], debtRatios[2], debtRatios[3], debtRatios[4]);
    }

    @Override
    public String toString() {
        return "EconomicData{" +
                "debtRatio1=" + debtRatio1 +
                ", debtRatio2=" + debtRatio2 +
                ", debtRatio3=" + debtRatio3 +
                ", debtRatio4=" + debtRatio4 +
                ", debtRatio5=" + debtRatio5 +
                '}';
    }
}

