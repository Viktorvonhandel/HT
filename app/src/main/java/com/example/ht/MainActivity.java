package com.example.ht;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private UI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Luodaan UI-olio ja asetetaan kotinäkymä
        ui = new UI(this);
        ui.setupHomeView();

        // Asetetaan hakupainikkeen toiminto
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään perustietonäkymään
                ui.setupBasicInfoView();
            }
        });
    }
}



