package com.example.ht;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BasicFragment extends Fragment {
    private static final String TAG = "BasicFragment";

    private TextView populationTextView;
    private TextView weatherTextView;
    private MunicipalityData municipalityData;

    public static BasicFragment newInstance() {
        return new BasicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate-OLLAAN BASICFRAGMENTISSA PERKELE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        populationTextView = view.findViewById(R.id.populationTextView);
        weatherTextView = view.findViewById(R.id.weatherTextView);
        Log.d(TAG, "onCreateView");

        // Tarkistetaan, onko datan asettamista varten
        if (municipalityData != null) {
            setMunicipalityData(municipalityData);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
        Log.d(TAG, "Tätä ei kyllä vissiin kutsuta");
        if (municipalityData != null && populationTextView != null && weatherTextView != null) {
            PopulationData populationData = municipalityData.getPopulationData();
            WeatherData weatherData = municipalityData.getWeatherData();

            if (populationData != null) {
                String populationText = "Population: " + populationData.getPopulation() +
                        "\nPopulation Change: " + populationData.getPopulationChange() + "%" +
                        "\nJob Self Sufficiency: " + populationData.getJobSelfSufficiency() + "%" +
                        "\nEmployment Rate: " + populationData.getEmploymentRate() +"%";
                populationTextView.setText(populationText);
            }
//NIINKÖ
            if (weatherData != null) {
                String weatherText = "City: " + weatherData.getCityName() +
                        "\nTemperature: " + weatherData.getTemperature() +"°C"+
                        "\nIcon Code: " + weatherData.getIconCode() ;
                weatherTextView.setText(weatherText);
            }
        }
    }
}




