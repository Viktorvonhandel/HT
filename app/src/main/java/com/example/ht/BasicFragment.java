package com.example.ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class BasicFragment extends Fragment {

    public BasicFragment() {
        // Tyhjä julkisen rakentajan tarvitaan
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Haetaan kaupungin nimi, lämpötila ja sääsymboli Municipality-luokasta
        String cityName = municipality.getCityName();
        int temperature = municipality.getTemperature();
        int weatherIconResourceId = municipality.getWeatherIconResourceId();

        // Etsitään näkymät layoutista
        TextView cityTextView = view.findViewById(R.id.cityTextView);
        TextView temperatureTextView = view.findViewById(R.id.temperatureTextView);
        ImageView weatherIconImageView = view.findViewById(R.id.weatherIconImageView);

        // Asetetaan tiedot näkymiin
        cityTextView.setText(cityName);
        temperatureTextView.setText(String.valueOf(temperature)); // Huomaa, että tämä voi vaatia muotoilun lisäämistä lämpötilalle
        weatherIconImageView.setImageResource(weatherIconResourceId);
    }

