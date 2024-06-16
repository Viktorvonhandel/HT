package com.example.ht;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private MunicipalityData municipalityData;

    ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MunicipalityData municipalityData) {
        super(fragmentManager, lifecycle);
        this.municipalityData = municipalityData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Log.d(TAG, "createFragment: Creating BasicFragment");
                BasicFragment basicFragment = BasicFragment.newInstance();
                basicFragment.setMunicipalityData(municipalityData);
                return basicFragment;
            case 1:
                Log.d(TAG, "createFragment: Creating PropertytaxFragment");
                PropertytaxFragment propertytaxFragment = new PropertytaxFragment();
                propertytaxFragment.setMunicipalityData(municipalityData);
                return propertytaxFragment;
            case 2:
                Log.d(TAG, "createFragment: Creating EconomicFragment");
                EconomicFragment economicFragment = new EconomicFragment(municipalityData);
                return economicFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Population";
            case 1:
                return "Propertytax";
            case 2:
                return "Economy";
            default:
                return null;
        }
    }
}


