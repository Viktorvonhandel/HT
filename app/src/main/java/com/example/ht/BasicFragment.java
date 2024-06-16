package com.example.ht;

import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "BasicFragment";

    private TextView populationTitleTextView;
    private TextView populationValueTextView;
    private TextView populationChangeTitleTextView;
    private TextView populationChangeValueTextView;
    private TextView jobSelfSufficiencyTitleTextView;
    private TextView jobSelfSufficiencyValueTextView;
    private TextView employmentRateTitleTextView;
    private TextView employmentRateValueTextView;
    private TextView weatherTextView;
    private TextView temperatureTextView;
    private ImageView weatherIconImageView;
    private MunicipalityData municipalityData;

    public static BasicFragment newInstance() {
        return new BasicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        populationTitleTextView = view.findViewById(R.id.populationTitleTextView);
        populationValueTextView = view.findViewById(R.id.populationValueTextView);
        populationChangeTitleTextView = view.findViewById(R.id.populationChangeTitleTextView);
        populationChangeValueTextView = view.findViewById(R.id.populationChangeValueTextView);
        jobSelfSufficiencyTitleTextView = view.findViewById(R.id.jobSelfSufficiencyTitleTextView);
        jobSelfSufficiencyValueTextView = view.findViewById(R.id.jobSelfSufficiencyValueTextView);
        employmentRateTitleTextView = view.findViewById(R.id.employmentRateTitleTextView);
        employmentRateValueTextView = view.findViewById(R.id.employmentRateValueTextView);
        weatherTextView = view.findViewById(R.id.weatherTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        weatherIconImageView = view.findViewById(R.id.weatherIconImageView);
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
        Log.d(TAG, "setMunicipalityData");

        if (municipalityData != null && weatherTextView != null && temperatureTextView != null && weatherIconImageView != null) {
            PopulationData populationData = municipalityData.getPopulationData();
            WeatherData weatherData = municipalityData.getWeatherData();

            if (populationData != null) {
                populationValueTextView.setText(String.valueOf(populationData.getPopulation()));
                populationChangeValueTextView.setText(populationData.getPopulationChange() + "%");
                jobSelfSufficiencyValueTextView.setText(populationData.getJobSelfSufficiency() + "%");
                employmentRateValueTextView.setText(populationData.getEmploymentRate() + "%");
            }

            if (weatherData != null) {
                String weatherText = weatherData.getCityName();
                weatherTextView.setText(weatherText);

                String temperatureText = weatherData.getTemperature() + "°C";
                temperatureTextView.setText(temperatureText);

                String iconUrl = "https://openweathermap.org/img/wn/" + weatherData.getIconCode() + "@2x.png";
                Picasso.get().load(iconUrl).into(weatherIconImageView);
            }
        }
    }
}





