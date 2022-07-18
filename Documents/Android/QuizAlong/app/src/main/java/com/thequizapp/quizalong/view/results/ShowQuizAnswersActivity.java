package com.thequizapp.quizalong.view.results;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.ShowResultsAdapter;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityShowQuizAnswersBinding;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.leaderboard.LeaderBoardActivity;
import com.thequizapp.quizalong.viewmodel.HistoryRedeemRequestViewModel;
import com.thequizapp.quizalong.viewmodel.ShowQuizResultsViewModel;

public class ShowQuizAnswersActivity extends BaseActivity {
    ActivityShowQuizAnswersBinding binding;
    ShowQuizResultsViewModel viewModel;
    private int p= 1;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_quiz_answers);
        viewModel = new ViewModelProvider(this).get(ShowQuizResultsViewModel.class);
        initView();
        initData();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView(){
        binding.vpQuizAns.setAdapter(new ShowResultsAdapter(this));
    }
    private void initData() {

        String quizType = getIntent().getStringExtra(Const.QUIZ_TYPE);
        String quizId = getIntent().getStringExtra(Const.QUIZ_ID);
        Log.e(":::: ",quizType+"jjjjjjj");
        viewModel.setQuizType(quizType);
        viewModel.setQuizId(quizId);
        viewModel.getQuizResults();

    }
    private void initObserve(){
        viewModel.getPaginationVal().observe(this, pageStr -> {
           Log.e("pageStr .. ",""+pageStr);
        });

        viewModel.getOnSuccess().observe(this, response -> {
            String str = p+ "/" + response.getQuestions().size();
            binding.tvPaginationstart.setText(str);
        });
    }

    private void initListener(){
        binding.rvQuestions.setHasFixedSize(true);
        mLayoutManager = (LinearLayoutManager) binding.rvQuestions.getLayoutManager();
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvQuestions);
        binding.rvQuestions.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(mLayoutManager);
                    p = mLayoutManager.getPosition(centerView);
                    String str = (p + 1) + "/" + binding.rvQuestions.getAdapter().getItemCount();
                    binding.tvPagination.setText(str);
                    viewModel.getPaginationVal().setValue(str);
                    binding.tvPaginationstart.setVisibility(View.GONE);
                    checkVisibility();
                }
            }
        });
        binding.btnPrev.setOnClickListener(view -> {
            View centerView = snapHelper.findSnapView(mLayoutManager);
            if (mLayoutManager.getPosition(centerView) > 0) {
                p = mLayoutManager.getPosition(centerView) -1;
                binding.rvQuestions.smoothScrollToPosition(p);
            }
        });
        binding.btnNext.setOnClickListener(view -> {
            View centerView = snapHelper.findSnapView(mLayoutManager);
            if (mLayoutManager.getPosition(centerView) < binding.rvQuestions.getAdapter().getItemCount() -1) {
                p = mLayoutManager.getPosition(centerView) + 1;
                binding.rvQuestions.smoothScrollToPosition(p);
            }
        });
        binding.btnViewLeaderboard.setOnClickListener(v -> {
            startActivity(new Intent(this, LeaderBoardActivity.class).
                    putExtra(Const.QUIZ_ID, String.valueOf(viewModel.getQuizId()))
                    .putExtra(Const.QUIZ_TYPE, viewModel.getQuizType()));
        });

    }

    public void checkVisibility() {
        if (p < 1) {
            binding.btnPrev.setVisibility(View.GONE);
            binding.btnNext.setVisibility(View.VISIBLE);
        } else if (p >= (binding.rvQuestions.getAdapter().getItemCount() - 1)) {
            binding.btnPrev.setVisibility(View.VISIBLE);
            binding.btnNext.setVisibility(View.GONE);
        } else {
            binding.btnPrev.setVisibility(View.VISIBLE);
            binding.btnNext.setVisibility(View.VISIBLE);
        }
    }
}