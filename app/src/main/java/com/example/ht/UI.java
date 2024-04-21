package com.example.ht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class UI {
    private Context context;
    private ViewPager viewPager;

    public UI(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    public void setupTabLayout() {
        if (context instanceof Activity) {
            ((Activity) context).setContentView(R.layout.tab_layout);

            TabLayout tabLayout = ((Activity) context).findViewById(R.id.tablayout);
            if (viewPager != null) {
                ViewPagerAdapter adapter = new ViewPagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), viewPager);
                viewPager.setAdapter(adapter);

                tabLayout.setupWithViewPager(viewPager);

                ImageView homeButton = ((Activity) context).findViewById(R.id.homeButton);
                homeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
        });}}
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ViewPager viewPager;
        private MunicipalityData municipalityData;

        ViewPagerAdapter(FragmentManager fm, ViewPager viewPager) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.viewPager = viewPager;
        }

        public void setMunicipalityData(MunicipalityData municipalityData) {
            this.municipalityData = municipalityData;
        }

        public MunicipalityData getMunicipalityData() {
            return municipalityData;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BasicFragment();
                case 1:
                    VehicleFragment vehicleFragment = new VehicleFragment();
                    vehicleFragment.setMunicipalityData(municipalityData);
                    return vehicleFragment;
                case 2:
                    if (viewPager != null && viewPager.getAdapter() instanceof ViewPagerAdapter) {
                        ViewPagerAdapter pagerAdapter = (ViewPagerAdapter) viewPager.getAdapter();
                        return new EconomicFragment(pagerAdapter.getMunicipalityData());
                    }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Perustiedot";
                case 1:
                    return "Autot";
                case 2:
                    return "Taloustiedot";
                default:
                    return null;
            }

    }
}}

