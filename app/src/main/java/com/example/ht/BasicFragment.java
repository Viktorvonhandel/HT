package com.example.ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

public class BasicFragment extends Fragment {

    private MunicipalityData municipalityData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (municipalityData != null) {
            PopulationData populationData = municipalityData.getPopulationData();
            WeatherData weatherData = municipalityData.getWeatherData();

            // Näytetään sää tiedot
            TextView cityTextView = view.findViewById(R.id.cityTextView);
            TextView temperatureTextView = view.findViewById(R.id.temperatureTextView);
            ImageView weatherIconImageView = view.findViewById(R.id.weatherIconImageView);

            if (weatherData != null) {
                cityTextView.setText(weatherData.getCityName());
                temperatureTextView.setText(String.valueOf(weatherData.getTemperature()));

                // Lataa sääsymboli käyttäen Picasso-kirjastoa
                String iconUrl = "http://openweathermap.org/img/w/" + weatherData.getIconCode() + ".png";
                Picasso.get().load(iconUrl).into(weatherIconImageView);
            }

            // Näytetään väestötiedot
            if (populationData != null) {
                TextView populationTextView = view.findViewById(R.id.populationTextView);
                populationTextView.setText(String.valueOf(populationData.getPopulation()));

                TextView populationChangeTextView = view.findViewById(R.id.populationChangeTextView);
                populationChangeTextView.setText(String.valueOf(populationData.getPopulationChange()));

                TextView jobSelfSufficiencyTextView = view.findViewById(R.id.jobSelfSufficiencyTextView);
                jobSelfSufficiencyTextView.setText(String.valueOf(populationData.getJobSelfSufficiency()));

                TextView employmentRateTextView = view.findViewById(R.id.employmentRateTextView);
                employmentRateTextView.setText(String.valueOf(populationData.getEmploymentRate()));
            }
        }
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
    }
}

