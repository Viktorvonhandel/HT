package com.example.ht;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MunicipalityData municipalityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (searchText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Syötä kunta ennen hakua", Toast.LENGTH_SHORT).show();
                } else {
                    // Luo DataRetriever-olio ja hae data asynkronisesti
                    new FetchDataTask().execute(searchText);
                }
            }
        });
    }
    // AsyncTask tiedon hakemiseksi ja päivittämiseksi UI:ssa
    private class FetchDataTask extends AsyncTask<String, Void, MunicipalityData> {

        @Override
        protected MunicipalityData doInBackground(String... params) {
            String searchText = params[0];
            PopulationData populationData = null;
            WeatherData weatherData = null;
            VehicleData vehicleData = null;
            TrafficData trafficData = null;

            // Haetaan tiedot asynkronisesti
            try {
                DataRetriever dataRetriever = new DataRetriever();
                populationData = dataRetriever.getPopulationData(searchText);
                weatherData = dataRetriever.getWeatherData(searchText);
                vehicleData = dataRetriever.getVehicleData(searchText);
                trafficData = dataRetriever.getTrafficData(searchText);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new MunicipalityData(populationData, weatherData, vehicleData, trafficData);
        }

        @Override
        protected void onPostExecute(MunicipalityData result) {
            if (result != null) {
                municipalityData = result;
                // Näytä tabit tai päivitä UI muulla tavoin
                UI ui = new UI(MainActivity.this);
                ui.setupTabLayout();
            } else {
                Toast.makeText(MainActivity.this, "Tietoja ei löytynyt", Toast.LENGTH_SHORT).show();
            }
        }
    }
