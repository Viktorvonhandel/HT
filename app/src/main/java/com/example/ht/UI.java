package com.example.ht;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class UI {
    private static final String TAG = "UI";
    private Context context;
    private ViewPager viewPager;

    public UI(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    public void setupTabLayout(MunicipalityData municipalityData) {
        Log.d(TAG, "setupTabLayout: Setting up tab layout");
        if (context instanceof AppCompatActivity) {
            if (context instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                ((AppCompatActivity) context).setContentView(R.layout.tab_layout);
                TabLayout tabLayout = ((AppCompatActivity) context).findViewById(R.id.tablayout);
                if (viewPager != null && fragmentManager != null) {
                    ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, municipalityData);
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                    ImageView homeButton = ((AppCompatActivity) context).findViewById(R.id.homeButton);
                    homeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            ((AppCompatActivity) context).finish();
                        }
                    });
                } else {
                    Log.e(TAG, "ViewPager or FragmentManager is null");
                }
            } else {
                Log.e(TAG, "Context is not an instance of FragmentActivity");
            }
        } else {
            Log.e(TAG, "Context is not an instance of AppCompatActivity");
        }
    }
}



