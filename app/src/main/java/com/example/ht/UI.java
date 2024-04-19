package com.example.ht;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class UI {
    private Context context;

    public UI(Context context) {
        this.context = context;
    }

    public void setupTabLayout() {
        // Asetetaan näkymä tab_layout.xml:stä
        ((Activity) context).setContentView(R.layout.tab_layout);

        // Asetetaan TabLayout ja ViewPager
        TabLayout tabLayout = ((Activity) context).findViewById(R.id.tablayout);
        ViewPager viewPager = ((Activity) context).findViewById(R.id.viewpager);

        // Luodaan FragmentPagerAdapter ja liitetään se ViewPageriin
        ViewPagerAdapter adapter = new ViewPagerAdapter(((FragmentActivity) context).getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Liitetään ViewPager TabLayoutiin
        tabLayout.setupWithViewPager(viewPager);

        // Asetetaan kotinäppäimen toiminnallisuus
        ImageView homeButton = ((Activity) context).findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Luo uusi intent ja käynnistä MainActivity
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                // Sulje nykyinen activity
                ((Activity) context).finish();
            }
        });
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Valitse fragmentti sen perusteella, mikä välilehti on valittu
            switch (position) {
                case 0:
                    return new BasicFragment();
                case 1:
                    return new VehicleFragment();
                case 2:
                    return new EconomicFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Palauta välilehtien kokonaismäärä
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            // Palauta välilehden nimi sen perusteella, mikä välilehti on valittu
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
    }
}


