package com.retrytech.quizbox.view.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.gson.Gson;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.FragmentHomeBinding;
import com.retrytech.quizbox.utils.SessionManager;
import com.retrytech.quizbox.utils.ads.MultipleCustomNativeAds;
import com.retrytech.quizbox.utils.ads.RewardAds;
import com.retrytech.quizbox.view.quiz.QuizActivity;
import com.retrytech.quizbox.view.quizes.QuizListActivity;
import com.retrytech.quizbox.viewmodel.HomeViewModel;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;
    private RewardAds rewardAds;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        initView();
        initListener();
        loadNativeAds();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    private void initView() {
        if (getActivity() != null) {
            SessionManager sessionManager = new SessionManager(getActivity());
            viewModel.getUser().setValue(sessionManager.getUser());
        }
        viewModel.getHomeData();
    }

    private void initListener() {
        viewModel.getHomeCategoriesAdapter().setOnItemClick((pairs, categoriesItem) -> {
            Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
            intent.putExtra("name", (String) pairs[0].second);
            intent.putExtra("logo", (String) pairs[1].second);
            intent.putExtra("data", new Gson().toJson(categoriesItem));
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(intent, activityOptions.toBundle());
        });
        viewModel.getQuizesAdapter().setOnItemClicks(quizesItem -> {
            if (rewardAds != null && quizesItem.getIsPermium() == 1) {
                rewardAds.setOnRewarded(() -> startActivity(new Intent(getActivity(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))));
                rewardAds.showAds();
                rewardAds = new RewardAds(getActivity());
            } else {
                startActivity(new Intent(getActivity(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem)));
            }
        });
    }

    private void loadNativeAds() {
        rewardAds = new RewardAds(getActivity());
        new MultipleCustomNativeAds(getActivity(), (adsData, position) -> {
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
        new MultipleCustomNativeAds(getActivity(), (adsData, position) -> {
            if (viewModel.getHomeCategoriesAdapter() != null) {
                if (adsData instanceof UnifiedNativeAd) {
                    viewModel.getHomeCategoriesAdapter().addNewAds(position, (UnifiedNativeAd) adsData);
                } else if (adsData instanceof NativeAd) {
                    viewModel.getHomeCategoriesAdapter().addFBAds(position, (NativeAd) adsData);
                }
                return position < viewModel.getHomeCategoriesAdapter().getCategories().size();
            }
            return true;
        }, 3);
    }
}