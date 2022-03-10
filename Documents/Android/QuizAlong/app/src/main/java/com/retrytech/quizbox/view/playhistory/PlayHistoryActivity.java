package com.retrytech.quizbox.view.playhistory;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityPlayHistoryBinding;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.viewmodel.PlayHistoryViewModel;

public class PlayHistoryActivity extends BaseActivity {
    ActivityPlayHistoryBinding binding;
    PlayHistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_history);
        viewModel = new ViewModelProvider(this).get(PlayHistoryViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}