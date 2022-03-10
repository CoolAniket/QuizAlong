package com.retrytech.quizbox.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.retrytech.quizbox.view.home.CategoriesFragment;
import com.retrytech.quizbox.view.home.HomeFragment;
import com.retrytech.quizbox.view.home.ProfileFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new CategoriesFragment();
        } else if (position == 2) {
            return new ProfileFragment();
        } else {
            return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
