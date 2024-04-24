package com.example.ht;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;

public class BasicFragment extends Fragment {

    private static final String TAG = "BasicFragment";
    private MunicipalityData municipalityData;

    public BasicFragment() {

    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        Log.d(TAG, "MunicipalityData set in BasicFragment: " + municipalityData);
        this.municipalityData = municipalityData;
        updateUI();
    }

    private void updateUI() {
        if (getView() != null && municipalityData != null) {
            TextView populationTextView = getView().findViewById(R.id.populationTextView);
            TextView weatherTextView = getView().findViewById(R.id.weatherTextView);

            //  pdata
            PopulationData populationData = municipalityData.getPopulationData();
            if (populationData != null) {
                String populationText = "Population: " + populationData.getPopulation() +
                        "\nPopulation Change: " + populationData.getPopulationChange() +
                        "\nJob Self Sufficiency: " + populationData.getJobSelfSufficiency() +
                        "\nEmployment Rate: " + populationData.getEmploymentRate();
                populationTextView.setText(populationText);
            }

            //  wdata
            WeatherData weatherData = municipalityData.getWeatherData();
            if (weatherData != null) {
                String weatherText = "City: " + weatherData.getCityName() +
                        "\nTemperature: " + weatherData.getTemperature() +
                        "\nIcon Code: " + weatherData.getIconCode();
                weatherTextView.setText(weatherText);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }
}