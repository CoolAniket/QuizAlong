package com.thequizapp.quizalong.view.redeem;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.RedeemViewPagerAdapter;
import com.thequizapp.quizalong.databinding.ActivityRequestRedeemBinding;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.HistoryRedeemRequestViewModel;

public class HistoryRedeemRequestActivity extends BaseActivity {
    ActivityRequestRedeemBinding binding;
    HistoryRedeemRequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_redeem);
        viewModel = new ViewModelProvider(this).get(HistoryRedeemRequestViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {
        RedeemViewPagerAdapter viewPagerAdapter = new RedeemViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPagerAdapter);
        new BannerAds(this, binding.bannerAds);
    }

    private void initObserve() {
        viewModel.getMutableSelectPosition().observe(this, binding.viewPager::setCurrentItem);
    }

    private void initListener() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("TAG", "");
            }

            @Override
            public void onPageSelected(int position) {
                viewModel.getSelectPosition().set(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("TAG", "");
            }
        });
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}