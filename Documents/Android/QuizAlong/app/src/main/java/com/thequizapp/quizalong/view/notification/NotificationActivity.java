package com.thequizapp.quizalong.view.notification;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityNotificationBinding;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.NotificationViewModel;

public class NotificationActivity extends BaseActivity {
    ActivityNotificationBinding binding;
    NotificationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        initView();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {
        viewModel.getNotifications();
        new BannerAds(this, binding.bannerAds);
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        LinearLayoutManager mLayoutManager = (LinearLayoutManager) binding.rvNotification.getLayoutManager();
        binding.rvNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getY() > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!viewModel.isLoading() && !viewModel.isLast() && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        viewModel.getNotifications();

                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.d("TAG", "");
            }
        });
    }
}