package com.thequizapp.quizalong.view.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.FragmentHomeBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.leaderboard.LeaderBoardActivity;
import com.thequizapp.quizalong.view.payment.PaymentActivity;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.view.quizes.QuizListActivity;
import com.thequizapp.quizalong.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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

        viewModel.getTwistQuizesAdapter().setOnItemClick((pairs, quizesItem) -> {

            if (pairs.length == 3) {
                startPaymentProcess(quizesItem);
            } else if (pairs.length == 1 && pairs[0].second.equals("Free")) {
                enrollForFree(quizesItem);
            } else try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                Date startDate = new Date();
                Date endDate = simpleDateFormat.parse(quizesItem.getStartTime());

                long afterStart = startDate.getTime() - endDate.getTime();
                Log.d(">.... ", ">.... " + afterStart);
                if (afterStart > 1500) {
                    Toast.makeText(getContext(), R.string.quiz_already_started, Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), QuizActivity.class)
                            .putExtra("data", new Gson().toJson(quizesItem))
                            .putExtra("user_name", viewModel.getUser().getValue().getUser().getFullname() != null ? viewModel.getUser().getValue().getUser().getFullname() : "Player")
                            .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.TWIST));
                }
            } catch (Exception e) {
                Log.e("lobby afterStart ", "" + e);
                e.printStackTrace();
            }

        });
        viewModel.getUpcomingQuizesAdapter().setOnItemClick((pairs, quizesItem) -> {

            if (pairs.length == 3) {
                startPaymentProcess(quizesItem);
            } else if (pairs.length == 1 && pairs[0].second.equals("Free")) {
                enrollForFree(quizesItem);
            } else try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                Date startDate = new Date();
                Date endDate = simpleDateFormat.parse(quizesItem.getStartTime());

                long afterStart = startDate.getTime() - endDate.getTime();
                Log.d(">.... ", ">.... " + afterStart);
                if (afterStart > 1500) {
                    Toast.makeText(getContext(), R.string.quiz_already_started, Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), QuizActivity.class)
                            .putExtra("data", new Gson().toJson(quizesItem))
                            .putExtra("user_name", viewModel.getUser().getValue().getUser().getFullname() != null ? viewModel.getUser().getValue().getUser().getFullname() : "Player")
                            .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.UPCOMING));
                }
            } catch (Exception e) {
                Log.e("lobby afterStart ", ""+e);
                e.printStackTrace();
            }

        });

        viewModel.getPastQuizesAdapter().setOnItemClicks((quizesItem) -> {

            //Log.e("CLCIKKKK ","1 "+quizesItem.getStartTime());
            //Log.e("CLCIKKKK ","1 "+new Gson().toJson(quizesItem));
            if(quizesItem.getPlayed() == 0) {
                startActivity(new Intent(getActivity(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra("user_name", viewModel.getUser().getValue().getUser().getFullname() != null ? viewModel.getUser().getValue().getUser().getFullname() : "Player")
                        .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.PAST));
            }else{
                startActivity(new Intent(getActivity(), LeaderBoardActivity.class)
                        .putExtra(Const.QUIZ_ID, String.valueOf(quizesItem.getQuizId()))
                        .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.PAST));
            }

        });

        binding.pullToRefresh.setOnRefreshListener(() -> {
            refreshData(); // your code
            binding.pullToRefresh.setRefreshing(false);
        });

        viewModel.toast.observe(this, toastMsg -> {
            if (toastMsg != null && !toastMsg.isEmpty()) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                Snackbar.make(binding.getRoot(), toastMsg, 2000)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show();
            }
        });
        viewModel.onSuccess.observe(this, response -> {
            Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_LONG).show();
            refreshData();
        });
    }

    private void refreshData() {
        viewModel.getHomeData();
    }

    private void startPaymentProcess(QuizItem quizesItem) {
        new CustomDialogBuilder(requireContext()).showPaymentAmountDialog(quizesItem.getEntry(), new CustomDialogBuilder.OnPaymentAmountSelectListener() {
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

    private void enrollForFree(QuizItem quizesItem) {
        new CustomDialogBuilder(requireContext()).showEnrollForFreeDialog(quizesItem, new CustomDialogBuilder.OnEnrollOptionSelectListener() {

            @Override
            public void onClick(Type enrollmentType) {
                switch (enrollmentType) {
                    case PAY:
                        // start Payment Process
                        startPaymentProcess(quizesItem);
                        break;
                    default:
                        viewModel.subscribeForFreeApi(quizesItem);
                        break;
                }

            }

            @Override
            public void onDismissClick() {

            }
        });
    }

}