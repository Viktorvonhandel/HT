package com.example.ht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
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
                });
            }
        }
    }
}
