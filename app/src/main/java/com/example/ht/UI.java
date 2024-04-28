package com.example.ht;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UI {
    private static final String TAG = "UI";
    private Context context;
    private ViewPager2 viewPager;

    public UI(Context context, ViewPager2 viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    public void setupTabLayout(MunicipalityData municipalityData) {
        Log.d(TAG, "setupTabLayout: Setting up tab layout");
        Log.d(TAG, "PERKELE NO TÄSSÄ : " + municipalityData);

        if (viewPager != null && context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Log.d(TAG, "FragmentManager: " + fragmentManager);

            activity.setContentView(R.layout.tab_layout);
            TabLayout tabLayout = activity.findViewById(R.id.tablayout);

            if (viewPager != null && fragmentManager != null) {
                Lifecycle lifecycle = activity.getLifecycle();
                ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, lifecycle, municipalityData);
                viewPager.setAdapter(adapter);

                new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                    tab.setText(adapter.getPageTitle(position));
                }).attach();

                ImageView homeButton = activity.findViewById(R.id.homeButton);
                homeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        activity.finish();
                    }
                });
            } else {
                Log.e(TAG, "ViewPager or FragmentManager is null");
            }
        } else {
            Log.e(TAG, "Context is not an instance of AppCompatActivity or ViewPager is null");
        }
    }
}






