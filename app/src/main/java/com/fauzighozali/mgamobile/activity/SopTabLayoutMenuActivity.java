package com.fauzighozali.mgamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.fauzighozali.mgamobile.R;
import com.fauzighozali.mgamobile.adapter.SopViewPagerAdapter;
import com.fauzighozali.mgamobile.fragment.SopCrossfunctionFragment;
import com.fauzighozali.mgamobile.fragment.SopDivisionFragment;
import com.google.android.material.tabs.TabLayout;
import com.riontech.calendar.adapter.ViewPagerAdapter;

public class SopTabLayoutMenuActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sop_tab_layout_menu);

        tabLayout = findViewById(R.id.tab_layout_sop);
        viewPager = findViewById(R.id.view_pager_sop);
        getTabs();
    }

    public void getTabs(){
        final SopViewPagerAdapter viewPagerAdapter = new SopViewPagerAdapter(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragmentList(SopDivisionFragment.getInstance(),"SOP Division");
                viewPagerAdapter.addFragmentList(SopCrossfunctionFragment.getInstance(),"SOP Crossfunction");

                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);

                tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_card_travel_24);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_extension_24);
            }
        });
    }
}