package com.thequizapp.quizalong.view.results;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.ShowResultsAdapter;
import com.thequizapp.quizalong.databinding.ActivityShowQuizAnswersBinding;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.HistoryRedeemRequestViewModel;
import com.thequizapp.quizalong.viewmodel.ShowQuizResultsViewModel;

public class ShowQuizAnswersActivity extends BaseActivity {
    ActivityShowQuizAnswersBinding binding;
    ShowQuizResultsViewModel viewModel;
    private int p= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_quiz_answers);
        viewModel = new ViewModelProvider(this).get(ShowQuizResultsViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView(){
        binding.vpQuizAns.setAdapter(new ShowResultsAdapter(this));
    }

    private void initObserve(){
        viewModel.getPaginationVal().observe(this, pageStr -> {
            Log.e("pageStr .. ",""+pageStr);
            binding.tvPagination.setText("");
            binding.tvPagination.setText(pageStr);
        });
    }

    private void initListener(){
        Log.e(">>>> ?? ",""+getIntent().getStringExtra("quizId"));
        viewModel.getQuizResults(""+getIntent().getStringExtra("quizId"));
        LinearLayoutManager manager= (LinearLayoutManager) binding.rvQuestions.getLayoutManager();

        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = manager.findLastVisibleItemPosition() - 1;
                binding.rvQuestions.smoothScrollToPosition(p);
                //viewModel.getPaginationVal().setValue(""+p);
                //Log.e("PPPP" ,""+p);
                checkVisibility();
                String str = (p+1)+"/"+binding.rvQuestions.getAdapter().getItemCount();
                viewModel.getPaginationVal().setValue(str);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = manager.findFirstVisibleItemPosition() + 1;
                binding.rvQuestions.smoothScrollToPosition(p);
                //Log.e("NNNN" ,""+p);
                checkVisibility();
                String str = (p+1)+"/"+binding.rvQuestions.getAdapter().getItemCount();
                viewModel.getPaginationVal().setValue(str);
            }
        });


    }

    public void checkVisibility() {
        Log.e("PPPP" ,(p+1)+""+binding.rvQuestions.getAdapter().getItemCount());

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