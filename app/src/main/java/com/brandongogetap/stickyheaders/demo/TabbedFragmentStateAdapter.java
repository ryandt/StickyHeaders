package com.brandongogetap.stickyheaders.demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabbedFragmentStateAdapter extends FragmentStateAdapter {

    public List<String> tabTitles = new ArrayList<>();

    public TabbedFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void submitList(List<String> newTitles) {
        if (tabTitles != newTitles) {
            tabTitles = newTitles;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TabFragment();
    }

    @Override
    public int getItemCount() {
        return tabTitles.size();
    }

    @Override
    public long getItemId(int position) {
        return tabTitles.get(position).hashCode();
    }
}
