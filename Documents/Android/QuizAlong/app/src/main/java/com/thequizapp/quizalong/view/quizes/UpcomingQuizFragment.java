package com.thequizapp.quizalong.view.quizes;

import android.app.Activity;
import android.app.ActivityOptions;
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
import com.thequizapp.quizalong.databinding.FragmentCategoriesBinding;
import com.thequizapp.quizalong.databinding.FragmentUpcomingQuizBinding;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.quiz.QuizActivity;
import com.thequizapp.quizalong.viewmodel.CategoriesViewModel;
import com.thequizapp.quizalong.viewmodel.UpcomingQuizViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class UpcomingQuizFragment extends Fragment {


    private final List<HomePage.QuizesItem> upcomingQuizes;
    FragmentUpcomingQuizBinding binding;
    UpcomingQuizViewModel viewModel;



    public UpcomingQuizFragment(List<HomePage.QuizesItem> quizData) {
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
        viewModel.getQuizesAdapter().setOnItemClicks(quizesItem -> {

            startActivity(new Intent(binding.getRoot().getContext(), QuizActivity.class)
                        .putExtra("data", new Gson().toJson(quizesItem)));
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