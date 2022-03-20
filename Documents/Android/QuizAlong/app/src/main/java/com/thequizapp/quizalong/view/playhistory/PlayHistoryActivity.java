package com.thequizapp.quizalong.view.playhistory;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityPlayHistoryBinding;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.PlayHistoryViewModel;

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