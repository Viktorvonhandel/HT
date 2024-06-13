package com.example.ht;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataRetriever extends AsyncTask<String, Void, Object> {
    private static final String API_KEY = "7d0c5fe9d064e6866204e39457bf9efe";
    private static final String CLASSIFICATION_ITEMS_API_URL = "https://data.stat.fi/api/classifications/v2/classifications/kunta_1_20240101/classificationItems?content=data&meta=max&lang=fi";

    private static final String PROPERTYTAX_API_URL = "https://vero2.stat.fi:443/PXWeb/api/v1/fi/Vero/Kiinteistoverot/kive_101.px";
    private static final String POPULATION_API_URL = "https://pxdata.stat.fi/PxWeb/api/v1/fi/Kuntien_avainluvut/2023/kuntien_avainluvut_2023_aikasarja.px";
    private static final String ECONOMIC_API_URL = "https://sotkanet.fi/rest/1.1/json?indicator=3856&years=2021&years=2020&genders=total";

    private static final int MAX_RETRIES = 10; // Maksimimäärä uudelleenyrityksiä
    private static final long RETRY_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1); // Uudelleenyritysten väli millisekunteina

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
                case "propertytax":
                    return getPropertytaxData(searchText);
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
            int retries = 0;
            while (retries < MAX_RETRIES) {
                String jsonData = fetchData(requestData, null, false);
                if (jsonData != null) {
                    weatherData = WeatherData.parseData(jsonData);
                    Log.d("DataRetriever", "Weather Data: " + weatherData);
                    break;
                } else {
                    retries++;
                    Log.e("DataRetriever", "Failed to fetch weather data, retrying... Attempt: " + retries);
                    try {
                        Thread.sleep(RETRY_INTERVAL_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.e("DataRetriever", "municipalityName is null");
        }
        return weatherData;
    }

    public List<PropertytaxData> getPropertytaxData(String municipalityName) throws IOException, JSONException {
        List<PropertytaxData> propertytaxDataList = null;
        try {
            String classificationCode = getClassificationCode(municipalityName);
            if (classificationCode != null) {

                String requestData = "{\"query\":[{\"code\":\"Alue\",\"selection\":{\"filter\":\"item\",\"values\":[\"" + classificationCode + "\"]}},{\"code\":\"Asiakkaan oikeudellinen muoto\",\"selection\":{\"filter\":\"item\",\"values\":[\"10\",\"30\",\"20\",\"40\",\"50\",\"60\",\"70\"]}},{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2023\"]}},{\"code\":\"Kiinteistövero\",\"selection\":{\"filter\":\"item\",\"values\":[\"SSSSS\"]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"Arvo_osuus_vero\"]}}],\"response\":{\"format\":\"json-stat\"}}";

                int retries = 0;
                while (retries < MAX_RETRIES) {
                    String propertytaxRawData = fetchData(PROPERTYTAX_API_URL, requestData, true); // Käytetään POST-metodia PROPERTYTAX_API_URL:n ollessa käytössä
                    if (propertytaxRawData != null) {
                        JSONObject jsonObject = new JSONObject(propertytaxRawData);
                        propertytaxDataList = PropertytaxData.parseData(jsonObject);

                        Log.d("DataRetriever", "Propertytax Data: " + propertytaxDataList);
                        break;
                    } else {
                        retries++;
                        Log.e("DataRetriever", "Failed to fetch propertytax data, retrying... Attempt: " + retries);
                        try {
                            Thread.sleep(RETRY_INTERVAL_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return propertytaxDataList;
    }

    public PopulationData getPopulationData(String municipalityName) throws IOException, JSONException {
        PopulationData populationData = null;
        try {
            String classificationCode = getClassificationCode(municipalityName);
            if (classificationCode != null) {
                String modifiedCode = "KU" + classificationCode;
                String requestData = "{\"query\":[{\"code\":\"Alue\",\"selection\":{\"filter\":\"agg:_- Kunnat aakkosjärjestyksessä 2023.agg\",\"values\":[\"" + modifiedCode + "\"]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"M411\",\"M476\",\"M151\",\"M536\"]}},{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2021\"]}}],\"response\":{\"format\":\"json-stat2\"}}";
                int retries = 0;
                while (retries < MAX_RETRIES) {
                    String populationRawData = fetchData(POPULATION_API_URL, requestData, true); // Käytetään POST-metodia POPULATION_API_URL:n ollessa käytössä
                    if (populationRawData != null) {
                        populationData = PopulationData.parseData(populationRawData);
                        Log.d("DataRetriever", "Population Data: " + populationData);
                        break;
                    } else {
                        retries++;
                        Log.e("DataRetriever", "Failed to fetch population data, retrying... Attempt: " + retries);
                        try {
                            Thread.sleep(RETRY_INTERVAL_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return populationData;
    }

    public EconomicData getEconomicData(String municipalityName) throws IOException, JSONException {
        String municipalityCode = getClassificationCode(municipalityName);
        if (municipalityCode != null) {
            int retries = 0;
            while (retries < MAX_RETRIES) {
                String economicData = fetchData(ECONOMIC_API_URL,null, false);
                if (economicData != null) {
                    EconomicData economicDataObject = EconomicData.parseData(economicData, municipalityCode);
                    Log.d("DataRetriever", "Economic Data: " + economicDataObject);
                    return economicDataObject;
                } else {
                    retries++;
                    Log.e("DataRetriever", "Failed to fetch economic data, retrying... Attempt: " + retries);
                    try {
                        Thread.sleep(RETRY_INTERVAL_MS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
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

    private String fetchData(String requestUrl, String requestData, boolean usePost) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(usePost ? "POST" : "GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(usePost);

        if (usePost && requestData != null) {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

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
