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
                    // Luo DataRetriever-olio ja hae data
                    DataRetriever dataRetriever = new DataRetriever();
                    WeatherData weatherData = dataRetriever.retrieveWeatherData(searchText);
                    PopulationData populationData = dataRetriever.getPopulation(searchText);
                    TrafficData trafficData = dataRetriever.getTraffic(searchText);
                    VehicleData vehicleData = dataRetriever.getVehicles(searchText);

                    // Luo MunicipalityData-olio ja aseta siihen haetut tiedot
                    municipalityData = new MunicipalityData();
                    municipalityData.setWeatherData(weatherData);
                    municipalityData.setPopulationData(populationData);
                    municipalityData.setTrafficData(trafficData);
                    municipalityData.setVehicleData(vehicleData);

                    // Näytä tabit
                    UI ui = new UI(MainActivity.this);
                    ui.setupTabLayout();
                }
            }
        });
    }
}
