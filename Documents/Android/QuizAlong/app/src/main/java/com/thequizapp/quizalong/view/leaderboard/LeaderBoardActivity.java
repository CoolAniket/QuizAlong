package com.thequizapp.quizalong.view.leaderboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityLeaderBoardBinding;
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.LeaderBoardViewModel;

public class LeaderBoardActivity extends BaseActivity {
    ActivityLeaderBoardBinding binding;
    LeaderBoardViewModel viewModel;
    private static final int MAX_ATTEMPTS = 6; // Max number of api calls
    private static final int M_INTERVAL = 5000; // 5 seconds
    private Handler handler;
    private int counter;

    private Runnable leaderboardChecker = new Runnable()
    {
        @Override
        public void run() {
            viewModel.getLeaderBoard();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leader_board);
        viewModel = new ViewModelProvider(this).get(LeaderBoardViewModel.class);
        handler = new Handler(Looper.getMainLooper());
        counter = 0;
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
        initData();
    }

    private void initView() {
        viewModel.setUser(new SessionManager(this).getUser());

    }

    private void initData() {
        String quizType = getIntent().getStringExtra(Const.QUIZ_TYPE);
        String quizId = getIntent().getStringExtra(Const.QUIZ_ID);
        viewModel.setQuizType(quizType);
        viewModel.setQuizId(quizId);
        handler.postDelayed(leaderboardChecker, 0);
    }

    private void initObserve() {
        viewModel.getLeaderBoardResult().observe(this, leaderBoardResponse -> {
            if (leaderBoardResponse.getMessage().equals(Const.MESSAGE_WAITING) && counter < MAX_ATTEMPTS) {
                counter ++;
                handler.postDelayed(leaderboardChecker, M_INTERVAL);
            } else {
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(LeaderBoardActivity.this, android.R.layout.simple_spinner_dropdown_item, Global.prettyAmountDropdown(leaderBoardResponse.getLeaderboardItem().getGroups()));
                binding.spType.setAdapter(stringArrayAdapter);
                binding.spType.setSelection(0);
            }
        });
        viewModel.getFirstUser().observe(this, quizesItem -> binding.setFirstUser(quizesItem));
        viewModel.getSecondUser().observe(this, quizesItem -> binding.setSecondUser(quizesItem));
        viewModel.getThirdUser().observe(this, quizesItem -> binding.setThirdUser(quizesItem));
        viewModel.getMyUser().observe(this, quizesItem -> binding.setMyUser(quizesItem));
        viewModel.getMyUserPosition().observe(this, position -> binding.setMyUserPosition(position));
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.updateAdapter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Leaderboard", "nothing selected");
            }
        });

    }
}