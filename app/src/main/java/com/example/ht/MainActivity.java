package com.example.ht;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private UI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ui = new UI(this);

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (searchText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Syötä kunta ennen hakua", Toast.LENGTH_SHORT).show();
                } else {
                    // Luo DataRetriever-olio ja lähetä kaupunki hakuna
                    DataRetriever dataRetriever = new DataRetriever();
                    dataRetriever.getWeatherData(searchText);
                    dataRetriever.getPopulationData(searchText);
                    dataRetriever.getTrafficData(searchText);
                    dataRetriever.getVehicleData(searchText);

                    // Näytä tabit
                    ui.setupTabLayout();
                }
            }
        });
    }
}
