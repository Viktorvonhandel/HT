package com.example.ht;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MunicipalityData municipalityData;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: Activity created");

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (searchText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Syötä kunta ennen hakua", Toast.LENGTH_SHORT).show();
                } else {
                    new FetchDataTask().execute(searchText);
                }
            }
        });
    }

    private class FetchDataTask extends AsyncTask<String, Void, MunicipalityData> {
        @Override
        protected MunicipalityData doInBackground(String... params) {
            String searchText = params[0];
            PopulationData populationData = null;
            WeatherData weatherData = null;
            List<VehicleData> vehicleDataList = null;
            EconomicData economicData = null;

            Log.d(TAG, "doInBackground: Fetching data for " + searchText);

            try {
                DataRetriever dataRetriever = new DataRetriever(new DataRetriever.DataRetrieverListener() {
                    @Override
                    public void onDataRetrieved(Object data) {
                        // Not needed for now
                    }
                });

                populationData = dataRetriever.getPopulationData(searchText);
                Log.d(TAG, "Population Data Retrieved: " + populationData);

                weatherData = dataRetriever.getWeatherData(searchText);
                Log.d(TAG, "Weather Data Retrieved: " + weatherData);

                vehicleDataList = dataRetriever.getVehicleData(searchText);
                for (VehicleData vehicleData : vehicleDataList) {
                    Log.d(TAG, "Vehicle Data Retrieved: " + vehicleData);
                }

                economicData = dataRetriever.getEconomicData(searchText);
                Log.d(TAG, "Economic Data Retrieved: " + economicData);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new MunicipalityData(populationData, weatherData, vehicleDataList, economicData);
        }

        protected void onPostExecute(MunicipalityData result) {
            if (result != null) {
                municipalityData = result;
                Log.d(TAG, "Municipality Data Retrieved: " + municipalityData);
                setContentView(R.layout.tab_layout);
                viewPager = findViewById(R.id.viewPager);

                // Luodaan uusi UI-olio ja asetetaan se ylävaiheeseen
                Log.d(TAG, "Creating new UI instance");
                UI ui = new UI(MainActivity.this, viewPager);
                ui.setupTabLayout(municipalityData);

                // Päivitetään fragmentit
                Log.d(TAG, "Trying to update BasicFragment");
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Päivitetään BasicFragment
                String basicFragmentTag = "android:switcher:" + viewPager.getId() + ":0";
                BasicFragment basicFragment = (BasicFragment) fragmentManager.findFragmentByTag(basicFragmentTag);
                if (basicFragment != null) {
                    basicFragment.setMunicipalityData(municipalityData);
                    Log.d(TAG, "BasicFragment updated with MunicipalityData: " + municipalityData);
                } else {
                    Log.d(TAG, "BasicFragment is null or not found");
                }

                // Päivitetään VehicleFragment
                int currentItem = viewPager.getCurrentItem();
                String vehicleFragmentTag = "android:switcher:" + viewPager.getId() + ":" + currentItem;
                VehicleFragment vehicleFragment = (VehicleFragment) fragmentManager.findFragmentByTag(vehicleFragmentTag);
                if (vehicleFragment != null) {
                    vehicleFragment.setMunicipalityData(municipalityData);
                    Log.d(TAG, "VehicleFragment updated with MunicipalityData: " + municipalityData);
                } else {
                    Log.d(TAG, "VehicleFragment is null or not found");
                }

                // Päivitetään EconomicFragment
                String economicFragmentTag = "android:switcher:" + viewPager.getId() + ":2";
                EconomicFragment economicFragment = (EconomicFragment) fragmentManager.findFragmentByTag(economicFragmentTag);
                if (economicFragment != null) {
                    economicFragment.setMunicipalityData(municipalityData);
                    Log.d(TAG, "EconomicFragment updated with MunicipalityData: " + municipalityData);
                } else {
                    Log.d(TAG, "EconomicFragment is null or not found");
                }
            } else {
                Toast.makeText(MainActivity.this, "Tietoja ei löytynyt", Toast.LENGTH_SHORT).show();
            }
        }
    }
}