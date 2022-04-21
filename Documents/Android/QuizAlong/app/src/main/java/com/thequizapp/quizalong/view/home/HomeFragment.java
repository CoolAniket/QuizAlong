package com.thequizapp.quizalong.view.home;

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
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.FragmentHomeBinding;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.MultipleCustomNativeAds;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.payment.PaymentActivity;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.view.quizes.QuizListActivity;
import com.thequizapp.quizalong.viewmodel.HomeViewModel;


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
        /*viewModel.getHomeCategoriesAdapter().setOnItemClick((pairs, categoriesItem) -> {
            Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
            intent.putExtra("name", (String) pairs[0].second);
            intent.putExtra("logo", (String) pairs[1].second);
            intent.putExtra("data", new Gson().toJson(categoriesItem));
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(intent, activityOptions.toBundle());
        });*/
        viewModel.getTwistQuizesAdapter().setOnItemClick((pairs, quizesItem) -> {
            if (pairs.length == 3) {
                startPaymentProcess(quizesItem);
//                startActivity(new Intent(getActivity(), PaymentActivity.class)
//                        .putExtra("data", new Gson().toJson(quizesItem)));
            } else {
                Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
                intent.putExtra("name", (String) pairs[0].second);
                intent.putExtra("logo", (String) pairs[1].second);
                intent.putExtra("data", new Gson().toJson(quizesItem));
            /*ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(intent, activityOptions.toBundle());*/
                startActivity(new Intent(getActivity(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra("quiz_type","twist"));
            }
        });
        viewModel.getUpcomingQuizesAdapter().setOnItemClick((pairs, quizesItem) -> {
            if (pairs.length == 3) {
                startPaymentProcess(quizesItem);
//                startActivity(new Intent(getActivity(), PaymentActivity.class)
//                        .putExtra("data", new Gson().toJson(quizesItem)));
            } else {
                Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
                intent.putExtra("name", (String) pairs[0].second);
                //intent.putExtra("logo", (String) pairs[1].second);
                intent.putExtra("data", new Gson().toJson(quizesItem));
                startActivity(new Intent(getActivity(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra("quiz_type","upcoming"));
            }
        });

        viewModel.getPastQuizesAdapter().setOnItemClicks((quizesItem) -> {
            Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
            //intent.putExtra("name", (String) pairs[0].second);
            //intent.putExtra("logo", (String) pairs[1].second);
            //intent.putExtra("data", new Gson().toJson(quizesItem));
            startActivity(new Intent(getActivity(), QuizActivity.class)
                    .putExtra("data", new Gson().toJson(quizesItem))
                    .putExtra("quiz_type","past"));
        });
    }

    private void startPaymentProcess(TwistQuizPage.Quize quizesItem) {
        new CustomDialogBuilder(requireContext()).showPaymentAmountDialog(new CustomDialogBuilder.OnPaymentAmountSelectListener() {
            @Override
            public void onAmountClick(int amount) {
                startActivity(new Intent(getActivity(), PaymentActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra("amount", amount));
            }

            @Override
            public void onDismissClick() {

            }
        });
    }

    private void loadNativeAds() {
        rewardAds = new RewardAds(getActivity());
        /*new MultipleCustomNativeAds(getActivity(), (adsData, position) -> {
            if (viewModel.getQuizesAdapter() != null) {
                if (adsData instanceof UnifiedNativeAd) {
                    viewModel.getQuizesAdapter().addNewAds(position, (UnifiedNativeAd) adsData);
                } else if (adsData instanceof NativeAd) {
                    viewModel.getQuizesAdapter().addFBAds(position, (NativeAd) adsData);
                }
                return position < viewModel.getQuizesAdapter().getQuizes().size();
            }
            return true;
        }, 4);*/

        /*new MultipleCustomNativeAds(getActivity(), (adsData, position) -> {
            if (viewModel.getHomeCategoriesAdapter() != null) {
                if (adsData instanceof UnifiedNativeAd) {
                    viewModel.getHomeCategoriesAdapter().addNewAds(position, (UnifiedNativeAd) adsData);
                } else if (adsData instanceof NativeAd) {
                    viewModel.getHomeCategoriesAdapter().addFBAds(position, (NativeAd) adsData);
                }
                return position < viewModel.getHomeCategoriesAdapter().getCategories().size();
            }
            return true;
        }, 3);*/
    }
}