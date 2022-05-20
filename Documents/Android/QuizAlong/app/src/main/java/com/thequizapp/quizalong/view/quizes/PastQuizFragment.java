package com.thequizapp.quizalong.view.quizes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.FragmentPastQuizBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.viewmodel.PastQuizViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class PastQuizFragment extends Fragment {


    private final List<QuizItem> quizesItems;
    FragmentPastQuizBinding binding;
    PastQuizViewModel viewModel;



    public PastQuizFragment(List<QuizItem> quizData) {
        Log.d("UpcomingQuizFragment", "");
        quizesItems = quizData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_past_quiz, container, false);
        viewModel = new ViewModelProvider(this).get(PastQuizViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initListener() {
        viewModel.updateQuizData(quizesItems);
        viewModel.getQuizesAdapter().setOnItemClicks(quizesItem -> {

            startActivity(new Intent(binding.getRoot().getContext(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem))
                        .putExtra(Const.QUIZ_TYPE, QuizActivity.Type.PAST));
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
    }
    public int getColorById(int colorId) {
        return ContextCompat.getColor(requireContext(), colorId);
    }
}