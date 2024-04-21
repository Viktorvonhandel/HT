// DataRetriever.java
package com.example.ht;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataRetriever extends AsyncTask<String, Void, Object> {
    private static final String API_KEY = "7d0c5fe9d064e6866204e39457bf9efe";
    private static final String CLASSIFICATION_ITEMS_API_URL = "https://data.stat.fi/api/classifications/v2/classifications/kunta_1_20240101/classificationItems?content=data&meta=max&lang=fi";

    private static final String VEHICLE_API_URL = "https://trafi2.stat.fi:443/PXWeb/api/v1/fi/TraFi/Liikennekaytossa_olevat_ajoneuvot/010_kanta_tau_101.px";
    private static final String POPULATION_API_URL = "https://pxdata.stat.fi/PxWeb/api/v1/fi/Kuntien_avainluvut/2023/kuntien_avainluvut_2023_aikasarja.px";
    private static final String ECONOMIC_API_URL = "https://sotkanet.fi/rest/1.1/json?indicator=3856&years=2018,2019,2020,2021,2022&genders=total";

    private DataRetrieverListener mListener;

    public DataRetriever(DataRetrieverListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Object doInBackground(String... params) {
        if (params.length < 2) {
            return null;
        }

        String queryType = params[0];
        String searchText = params[1];

        try {
            switch (queryType) {
                case "population":
                    return getPopulationData(searchText);
                case "weather":
                    return getWeatherData(searchText);
                case "vehicle":
                    return getVehicleData(searchText);
                case "economic":
                    return getEconomicData(searchText);
                default:
                    return null;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        mListener.onDataRetrieved(result);
    }

    public WeatherData getWeatherData(String municipalityName) throws IOException {
        WeatherData weatherData = null;
        if (municipalityName != null) {
            String requestData = "https://api.openweathermap.org/data/2.5/weather?q=" + municipalityName + ",FI&appid=" + API_KEY + "&units=metric";
            String jsonData = fetchData(requestData);
            weatherData = WeatherData.parseData(jsonData);
        } else {
            // Voit käsitellä tässä tilanteen, kun municipalityName on null
            System.out.println("municipalityName is null");
        }
        return weatherData;
    }

    public PopulationData getPopulationData(String municipalityName) throws IOException, JSONException {
        PopulationData populationData = null;
        try {
            String classificationCode = getClassificationCode(municipalityName);
            if (classificationCode != null) {
                String modifiedCode = "KU" + classificationCode;
                String requestData = "{\"query\":[{\"code\":\"Alue\",\"selection\":{\"filter\":\"agg:_- Kunnat aakkosjärjestyksessä 2023.agg\",\"values\":[\"" + modifiedCode + "\"]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"M411\",\"M476\",\"M151\",\"M536\"]}},{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2021\"]}}],\"response\":{\"format\":\"json-stat2\"}}";
                String populationRawData = fetchData(requestData);
                populationData = PopulationData.parseData(populationRawData);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return populationData;
    }

    public List<VehicleData> getVehicleData(String municipalityName) throws IOException, JSONException {
        List<VehicleData> vehicleDataList = null;
        try {
            String classificationCode = getClassificationCode(municipalityName);
            if (classificationCode != null) {
                String modifiedCode = "KU" + classificationCode;
                String requestData = "{\"query\":[{\"code\":\"Alue\",\"selection\":{\"filter\":\"agg:TRAFICOM Kunnat aakkosjärjestyksessä 2022.agg\",\"values\":[\"" + modifiedCode + "\"]}}, {\"code\":\"Merkki\",\"selection\":{\"filter\":\"item\",\"values\":[\"YH\",\"401\",\"1205\",\"202\",\"110\",\"1930\",\"201\",\"896\",\"647\",\"2003\",\"206\",\"2264\",\"009\",\"2911\",\"3118\",\"2141\",\"012\",\"014\",\"015\",\"313\",\"3540\",\"366\",\"594\",\"911\",\"151\",\"9778\",\"026\",\"2386\",\"550\",\"410\",\"135\",\"701\",\"013\",\"1049\",\"908\",\"870\",\"861\",\"016\",\"1532\",\"901\",\"150\",\"055\",\"762\",\"714\",\"418\",\"181\",\"438\",\"063\",\"165\",\"241\",\"419\",\"3634\",\"902\",\"243\",\"068\",\"167\",\"171\",\"903\",\"169\",\"702\",\"668\",\"2059\",\"900\",\"247\",\"076\",\"249\",\"1985\",\"361\",\"083\",\"3553\",\"084\",\"251\",\"AAD\",\"085\",\"180\",\"365\",\"197\",\"179\",\"606\",\"413\",\"373\",\"805\",\"782\",\"640\",\"9779\",\"914\",\"187\",\"912\",\"316\",\"2055\",\"905\",\"1141\",\"191\",\"195\",\"708\",\"267\",\"560\",\"897\",\"1157\",\"MUUT\",\"MATK\"]}},{\"code\":\"Käyttöönottovuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"YH\"]}},{\"code\":\"Käyttövoima\",\"selection\":{\"filter\":\"item\",\"values\":[\"YH\"]}}],\"response\":{\"format\":\"json-stat2\"}}";
                String vehicleRawData = fetchData(requestData);
                vehicleDataList = VehicleData.parseData(vehicleRawData);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return vehicleDataList;
    }

    public EconomicData getEconomicData(String municipalityName) throws IOException, JSONException {
        String municipalityCode = getClassificationCode(municipalityName);
        if (municipalityCode != null) {
            String economicData = fetchData(ECONOMIC_API_URL);
            return EconomicData.parseData(economicData, municipalityCode);
        }
        return null; // Palauta tyhjä EconomicData-olio tai null, jos tietoja ei löydy
    }


    private String getClassificationCode(String municipalityName) throws IOException, JSONException {
        URL url = new URL(CLASSIFICATION_ITEMS_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        JSONArray items = new JSONArray(response.toString());
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONArray names = item.getJSONArray("classificationItemNames");
            for (int j = 0; j < names.length(); j++) {
                JSONObject name = names.getJSONObject(j);
                if (name.getString("name").equalsIgnoreCase(municipalityName)) {
                    return item.getString("code");
                }
            }
        }
        return null;
    }

    private String fetchData(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        Log.d("DataRetriever", "Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } else {
            Log.e("DataRetriever", "Invalid response: " + responseCode);
            return null;
        }
    }


    public interface DataRetrieverListener {
        void onDataRetrieved(Object data);
    }
}
