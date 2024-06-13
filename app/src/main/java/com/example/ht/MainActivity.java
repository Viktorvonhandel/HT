package com.example.ht;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MunicipalityData municipalityData;
    private ViewPager2 viewPager;

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

        // Lisätään homeButtonin toiminnallisuus

    }


    private class FetchDataTask extends AsyncTask<String, Void, MunicipalityData> {
        @Override
        protected MunicipalityData doInBackground(String... params) {
            String searchText = params[0];
            PopulationData populationData = null;
            WeatherData weatherData = null;
            List<PropertytaxData> propertytaxDataList = null;
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
                Log.d(TAG, "PopulationData Retrieved: " + populationData);
                weatherData = dataRetriever.getWeatherData(searchText);
                Log.d(TAG, "WeatherData Retrieved: " + weatherData);
                propertytaxDataList = dataRetriever.getPropertytaxData(searchText);
                for (PropertytaxData propertytaxData : propertytaxDataList) {
                    Log.d(TAG, "PropertytaxData Retrieved: " + propertytaxData);
                }
                economicData = dataRetriever.getEconomicData(searchText);
                Log.d(TAG, "EconomicData Retrieved: " + economicData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new MunicipalityData(populationData, weatherData, propertytaxDataList, economicData);
        }

        @Override
        protected void onPostExecute(MunicipalityData result) {
            if (result != null) {
                municipalityData = result;
                Log.d(TAG, "MunicipalityData Retrieved: " + municipalityData);

                setContentView(R.layout.tab_layout);
                ImageButton homeButton = findViewById(R.id.homeButton); // Lisätty homeButton
                viewPager = findViewById(R.id.viewPager);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), municipalityData);
//NÖH
                viewPager.setAdapter(adapter);

                TabLayout tabLayout = findViewById(R.id.tablayout);
                new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                    tab.setText(adapter.getPageTitle(position));
                }).attach();

                // Update fragments with data
                updateFragmentsWithData();

                homeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Tietoja ei löytynyt", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateFragmentsWithData() {
        BasicFragment basicFragment = (BasicFragment) getSupportFragmentManager().findFragmentByTag("f" + 0);
        if (basicFragment != null) {
            basicFragment.setMunicipalityData(municipalityData);
            Log.d(TAG, "BasicFragment updated with MunicipalityData: " + municipalityData);
        }

        PropertytaxFragment propertytaxFragment = (PropertytaxFragment) getSupportFragmentManager().findFragmentByTag("f" + 1);
        if (propertytaxFragment != null) {
            propertytaxFragment.setMunicipalityData(municipalityData);
            Log.d(TAG, "PropertytaxFragment updated with MunicipalityData: " + municipalityData);
        }

        EconomicFragment economicFragment = (EconomicFragment) getSupportFragmentManager().findFragmentByTag("f" + 2);
        if (economicFragment != null) {
            economicFragment.setMunicipalityData(municipalityData);
            Log.d(TAG, "EconomicFragment updated with MunicipalityData: " + municipalityData);
        }
    }
}

