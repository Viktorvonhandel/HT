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

    // Metodi taloudellisten tietojen parsimiseen
    public static EconomicData parseData(String jsonData, String municipalityCode) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        double[] debtRatios = new double[5];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("region") == Integer.parseInt(municipalityCode)) {
                // Lisää viisi debtRatioa taulukkoon
                debtRatios[0] = jsonObject.getDouble("debtRatio1");
                debtRatios[1] = jsonObject.getDouble("debtRatio2");
                debtRatios[2] = jsonObject.getDouble("debtRatio3");
                debtRatios[3] = jsonObject.getDouble("debtRatio4");
                debtRatios[4] = jsonObject.getDouble("debtRatio5");
                break; // Lopeta silmukka kun tiedot on löydetty
            }
        }
        return new EconomicData(debtRatios[0], debtRatios[1], debtRatios[2], debtRatios[3], debtRatios[4]);
    }
}

