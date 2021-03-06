package com.retrytech.quizbox.view.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityWelcomeBinding;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.view.login.LoginActivity;

public class WelcomeActivity extends BaseActivity {
    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        initListener();
    }

    private void initListener() {
        binding.btnStart.setOnClickListener(v -> {
            Pair[] pair = new Pair[1];
            pair[0] = new Pair<View, String>(binding.ivLogo, "logo");
            Intent intent = new Intent(this, LoginActivity.class);
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, pair);
            startActivity(intent, activityOptions.toBundle());
        });
    }
}