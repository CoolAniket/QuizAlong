package com.retrytech.quizbox.view.leaderboard;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityLeaderBoardBinding;
import com.retrytech.quizbox.utils.ads.BannerAds;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.viewmodel.LeaderBoardViewModel;

public class LeaderBoardActivity extends BaseActivity {
    ActivityLeaderBoardBinding binding;
    LeaderBoardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leader_board);
        viewModel = new ViewModelProvider(this).get(LeaderBoardViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {
        viewModel.getLeaderBoard();
        new BannerAds(this, binding.bannerAds);
    }

    private void initObserve() {
        viewModel.getFirstUser().observe(this, quizesItem -> binding.setFirstUser(quizesItem));
        viewModel.getSecondUser().observe(this, quizesItem -> binding.setSecondUser(quizesItem));
        viewModel.getThirdUser().observe(this, quizesItem -> binding.setThirdUser(quizesItem));
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}