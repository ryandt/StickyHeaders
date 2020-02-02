package com.brandongogetap.stickyheaders.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabbedFragmentStateAdapter tabAdapter = new TabbedFragmentStateAdapter(this);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOffscreenPageLimit(2);

        List<String> titles = new ArrayList<>();
        titles.add("Tue 21");
        titles.add("Mon 20");
        titles.add("Wed 22");
        titles.add("Thu 23");
        titles.add("Fri 24");
        tabAdapter.submitList(titles);
        viewPager.setAdapter(tabAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabAdapter.tabTitles.get(position));
        });
        tabLayoutMediator.attach();
    }
}
