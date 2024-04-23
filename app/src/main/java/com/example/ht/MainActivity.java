package com.example.ht;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import java.util.List;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MunicipalityData municipalityData;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        viewPager = findViewById(R.id.viewPager);

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

            try {
                DataRetriever dataRetriever = new DataRetriever(new DataRetriever.DataRetrieverListener() {
                    @Override
                    public void onDataRetrieved(Object data) {

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

        @Override
        protected void onPostExecute(MunicipalityData result) {
            if (result != null) {
                municipalityData = result;
                // Näytä tabit
                UI ui = new UI(MainActivity.this, viewPager);
                ui.setupTabLayout();

                // Päivitä BF
                BasicFragment basicFragment = (BasicFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 0);
                if (basicFragment != null) {
                    basicFragment.setMunicipalityData(municipalityData);
                }

                // Päivitä VF
                VehicleFragment vehicleFragment = (VehicleFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                if (vehicleFragment != null) {
                    vehicleFragment.setMunicipalityData(municipalityData);
                }

                // Päivitä EF
                EconomicFragment economicFragment = (EconomicFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 2);
                if (economicFragment != null) {
                    economicFragment.setMunicipalityData(municipalityData);
                }
            } else {
                Toast.makeText(MainActivity.this, "Tietoja ei löytynyt", Toast.LENGTH_SHORT).show();
            }
        }
    }

}