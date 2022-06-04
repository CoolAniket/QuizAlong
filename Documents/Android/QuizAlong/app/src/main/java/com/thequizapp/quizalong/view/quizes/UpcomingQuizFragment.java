package com.thequizapp.quizalong.view.quizes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.FragmentUpcomingQuizBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.view.payment.PaymentActivity;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.viewmodel.UpcomingQuizViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class UpcomingQuizFragment extends Fragment {


    private final List<QuizItem> upcomingQuizes;
    FragmentUpcomingQuizBinding binding;
    UpcomingQuizViewModel viewModel;



    public UpcomingQuizFragment(List<QuizItem> quizData) {
        Log.d("UpcomingQuizFragment", "");
        upcomingQuizes = quizData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_quiz, container, false);
        viewModel = new ViewModelProvider(this).get(UpcomingQuizViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initListener() {
        viewModel.updateQuizData(upcomingQuizes);
        viewModel.getQuizesAdapter().setOnItemClick((pairs, quizesItem) -> {
            if (pairs.length == 3) {
                startPaymentProcess(quizesItem);
            } else if (pairs.length == 1 && pairs[0].second.equals("Free")) {
                enrollForFree(quizesItem);
            } else {
                startActivity(new Intent(binding.getRoot().getContext(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.UPCOMING));
            }
        });

        viewModel.getToast().observe(this, toastMsg -> {
            if (toastMsg != null && !toastMsg.isEmpty()) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                Snackbar.make(binding.getRoot(), toastMsg, 2000)
                        .setBackgroundTint(getColorById(R.color.black))
                        .setTextColor(getColorById(R.color.white))
                        .show();
            }
        });

        viewModel.onSuccess.observe(this, response -> {
            Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_LONG).show();
//            refreshData();
        });
    }
    public int getColorById(int colorId) {
        return ContextCompat.getColor(requireContext(), colorId);
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