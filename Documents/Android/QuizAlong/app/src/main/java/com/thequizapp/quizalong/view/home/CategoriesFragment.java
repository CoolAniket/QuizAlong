package com.thequizapp.quizalong.view.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.FragmentCategoriesBinding;
import com.thequizapp.quizalong.utils.ads.MultipleCustomNativeAds;
import com.thequizapp.quizalong.view.quizes.QuizListActivity;
import com.thequizapp.quizalong.viewmodel.CategoriesViewModel;

public class CategoriesFragment extends Fragment {


    FragmentCategoriesBinding binding;
    CategoriesViewModel viewModel;

    public CategoriesFragment() {
        Log.d("TAG", "");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        initListener();
        loadNativeAds();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initListener() {
        viewModel.getHomeData();
        viewModel.getCategoriesAdapter().setOnItemClick((pairs, categoriesItem) -> {
            Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
            intent.putExtra("name", (String) pairs[0].second);
            intent.putExtra("logo", (String) pairs[1].second);
            intent.putExtra("data", new Gson().toJson(categoriesItem));
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(intent, activityOptions.toBundle());
        });
    }

    private void loadNativeAds() {
        new MultipleCustomNativeAds(getActivity(), (adsData, position) -> {
            if (viewModel.getCategoriesAdapter() != null) {
                if (adsData instanceof UnifiedNativeAd) {
                    viewModel.getCategoriesAdapter().addNewAds(position, (UnifiedNativeAd) adsData);
                } else if (adsData instanceof NativeAd) {
                    viewModel.getCategoriesAdapter().addFBAds(position, (NativeAd) adsData);
                }
                return position < viewModel.getCategoriesAdapter().getCategories().size();
            }
            return true;
        }, 3);
    }
}