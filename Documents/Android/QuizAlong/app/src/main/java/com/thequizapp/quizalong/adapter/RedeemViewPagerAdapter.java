package com.thequizapp.quizalong.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.thequizapp.quizalong.view.redeem.RedeemFragment;

public class RedeemViewPagerAdapter extends FragmentPagerAdapter {
    public RedeemViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RedeemFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
