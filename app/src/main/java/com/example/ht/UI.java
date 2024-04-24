package com.example.ht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import android.util.Log;


public class UI {
    private static final String TAG = "UI";
    private Context context;
    private ViewPager viewPager;

    public UI(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    public void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: Setting up tab layout");
        if (context instanceof Activity) {
            if (context instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                ((Activity) context).setContentView(R.layout.tab_layout);

                TabLayout tabLayout = ((Activity) context).findViewById(R.id.tablayout);
                if (viewPager != null && fragmentManager != null) {
                    ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, viewPager);
                    viewPager.setAdapter(adapter);

                    // Logita setupWithViewPager()-metodin kutsu
                    Log.d(TAG, "setupTabLayout: Setting up TabLayout with ViewPager");
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
                } else {
                    Log.e(TAG, "ViewPager or FragmentManager is null");
                }
            } else {
                Log.e(TAG, "Context is not an instance of FragmentActivity");
            }
        } else {
            Log.e(TAG, "Context is not an instance of Activity");
        }
    }
}
