package com.thequizapp.quizalong.view.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.TransactionHistoryViewPagerAdapter;
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
        viewModel.getTransactionHistory(new SessionManager(this).getUser().getUser().getId());
    }

    private void initObserve() {
        viewModel.getHistoryData().observe(this, transactionResponse -> {
            TransactionHistoryViewPagerAdapter mainViewPagerAdapter = new TransactionHistoryViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mainViewPagerAdapter.setData(transactionResponse);
            binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.d("TAG", "");
                }

                @Override
                public void onPageSelected(int position) {
                    binding.viewPager.setCurrentItem(position);
//                viewModel.onClickMenu(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.d("TAG", "");
                }
            });
//            binding.viewPager.setOffscreenPageLimit(2);
            binding.viewPager.setAdapter(mainViewPagerAdapter);
            binding.tabLayout.setupWithViewPager(binding.viewPager);

        });
    }
    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

}