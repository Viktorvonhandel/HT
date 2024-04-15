package com.example.ht;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class UI {
    private Context context;

    public UI(Context context) {
        this.context = context;
    }

    public void setupHomeView() {
        // Asetetaan kotinäkymä activity_main.xml:stä
        ((Activity) context).setContentView(R.layout.activity_main);
    }

    public void setupBasicInfoView() {
        // Asetetaan perustietonäkymä activity_basic_info.xml:stä
        ((Activity) context).setContentView(R.layout.activity_basic_info);

        // Asetetaan yläpalkin painikkeiden toiminnot
        Button buttonVehicles = ((Activity) context).findViewById(R.id.buttonVehicles);
        Button buttonTraffic = ((Activity) context).findViewById(R.id.buttonTraffic);

        buttonVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään autonäkymään
                setupVehicleView();
            }
        });

        buttonTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään kelikameranäkymään
                setupTrafficView();
            }
        });
    }

    public void setupVehicleView() {
        // Asetetaan autonäkymä activity_vehicle.xml:stä
        ((Activity) context).setContentView(R.layout.activity_vehicle);

        // Asetetaan yläpalkin painikkeiden toiminnot
        Button buttonBasicInfo = ((Activity) context).findViewById(R.id.buttonBasicInfo);
        Button buttonTraffic = ((Activity) context).findViewById(R.id.buttonTraffic);

        buttonBasicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään perustietonäkymään
                setupBasicInfoView();
            }
        });

        buttonTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään kelikameranäkymään
                setupTrafficView();
            }
        });
    }

    public void setupTrafficView() {
        // Asetetaan kelikameranäkymä activity_traffic.xml:stä
        ((Activity) context).setContentView(R.layout.activity_traffic);

        // Asetetaan yläpalkin painikkeiden toiminnot
        Button buttonBasicInfo = ((Activity) context).findViewById(R.id.buttonBasicInfo);
        Button buttonVehicles = ((Activity) context).findViewById(R.id.buttonVehicles);

        buttonBasicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään perustietonäkymään
                setupBasicInfoView();
            }
        });

        buttonVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siirrytään autonäkymään
                setupVehicleView();
            }
        });
    }
}

