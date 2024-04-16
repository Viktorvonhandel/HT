package com.example.ht;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstance){
    super.onCreate(savedInstance);
    setContentView(R.layout.activity_main);

    tabLayout = findViewById(R.id.tablayout);
    viewPager = findViewById(R.id.viewpager);
    tabLayout.setupWithViewPager(viewPager);

    VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    vpAdapter.addFragment(new FragmentBasic(), "Perusjutut");
    vpAdapter.addFragment(new FragmentVehicle(), "Autot");
    vpAdapter.addFragment(new FragmentCam(), "Keli");
    viewPager.setAdapter(vpAdapter);
    }
}





