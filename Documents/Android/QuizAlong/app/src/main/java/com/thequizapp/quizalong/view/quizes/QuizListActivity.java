package com.thequizapp.quizalong.view.quizes;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityQuizListBinding;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.utils.ads.MultipleCustomNativeAds;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.viewmodel.QuizListViewModel;

public class QuizListActivity extends BaseActivity {
    ActivityQuizListBinding binding;
    QuizListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_list);
        viewModel = new ViewModelProvider(this).get(QuizListViewModel.class);
        binding.tvTitle.setTransitionName(getIntent().getStringExtra("name"));
        binding.ivLogo.setTransitionName(getIntent().getStringExtra("logo"));
        initView();
        initListener();
        loadNativeAds();
        binding.setViewModel(viewModel);
    }

    private void initView() {
        String catStr = getIntent().getStringExtra("data");
        viewModel.setCategoriesItem(new Gson().fromJson(catStr, CategoriesResponse.Category.class));
        viewModel.getQuizesByCatId();
    }

    private void initListener() {
        RewardAds rewardAds = new RewardAds(this);
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        viewModel.getQuizesAdapter().setOnItemClicks(quizesItem -> {
            if (rewardAds != null && quizesItem.getIsPermium() == 1) {
                rewardAds.setOnRewarded(() -> startActivity(new Intent(QuizListActivity.this, QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))));
                rewardAds.showAds();
            } else {
                startActivity(new Intent(QuizListActivity.this, QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem)));
            }
        });
    }

    private void loadNativeAds() {
        new MultipleCustomNativeAds(this, (adsData, position) -> {
            if (viewModel.getQuizesAdapter() != null) {
                if (adsData instanceof UnifiedNativeAd) {
                    viewModel.getQuizesAdapter().addNewAds(position, (UnifiedNativeAd) adsData);
                } else if (adsData instanceof NativeAd) {
                    viewModel.getQuizesAdapter().addFBAds(position, (NativeAd) adsData);
                }
                return position < viewModel.getQuizesAdapter().getQuizes().size();
            }
            return true;
        }, 4);
    }
}