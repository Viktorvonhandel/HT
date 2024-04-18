import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

            // Näytetään väestötiedot
            TextView populationTextView = view.findViewById(R.id.populationTextView);
            populationTextView.setText(String.valueOf(populationData.getPopulation()));

            TextView populationChangeTextView = view.findViewById(R.id.populationChangeTextView);
            populationChangeTextView.setText(String.valueOf(populationData.getPopulationChange()));

            TextView jobSelfSufficiencyTextView = view.findViewById(R.id.jobSelfSufficiencyTextView);
            jobSelfSufficiencyTextView.setText(String.valueOf(populationData.getJobSelfSufficiency()));

            TextView employmentRateTextView = view.findViewById(R.id.employmentRateTextView);
            employmentRateTextView.setText(String.valueOf(populationData.getEmploymentRate()));

            // Näytetään sää tiedot
            TextView cityTextView = view.findViewById(R.id.cityTextView);
            TextView temperatureTextView = view.findViewById(R.id.temperatureTextView);
            ImageView weatherIconImageView = view.findViewById(R.id.weatherIconImageView);

            cityTextView.setText(weatherData.getCityName());
            temperatureTextView.setText(String.valueOf(weatherData.getTemperature()));
            // Tässä asetetaan sään kuva, voit käyttää esim. Glide-kirjastoa kuvan lataamiseen verkosta
            // weatherIconImageView.setImageResource(R.drawable.weather_icon);
        }
    }

    public void setMunicipalityData(MunicipalityData municipalityData) {
        this.municipalityData = municipalityData;
    }
}
