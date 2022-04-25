package com.thequizapp.quizalong.view.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityTransactionHistoryBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.viewmodel.TransactionHistoryViewModel;

public class TransactionHistoryActivity extends AppCompatActivity {

    ActivityTransactionHistoryBinding binding;
    TransactionHistoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_history);
        viewModel = new ViewModelProvider(this).get(TransactionHistoryViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {
//        viewModel.setUser(new SessionManager(this).getUser());
        viewModel.getTransactionHistory(new SessionManager(this).getUser().getUser().getUser_id());
    }

    private void initObserve() {
//        viewModel.getHistoryData().observe(this, quizesItem -> binding.setFirstUser(quizesItem));
    }
    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

}