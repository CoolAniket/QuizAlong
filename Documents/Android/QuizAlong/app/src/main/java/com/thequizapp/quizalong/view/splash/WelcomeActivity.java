package com.thequizapp.quizalong.view.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityWelcomeBinding;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.login.AdditionalInfoActivity;
import com.thequizapp.quizalong.view.login.LoginActivity;
import com.thequizapp.quizalong.view.main.MainActivity;

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