package com.thequizapp.quizalong.view.home;

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
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.quizes.QuizListActivity;
import com.thequizapp.quizalong.viewmodel.CategoriesViewModel;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class CategoriesFragment extends Fragment {


    FragmentCategoriesBinding binding;
    CategoriesViewModel viewModel;

    public CategoriesFragment() {
        Log.d("CategoriesFragment", "");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initListener() {
        viewModel.getHomeData(Const.COURSE_TYPE_MEDICINE, Integer.parseInt(Global.userId.get()));
        //viewModel.getHomeData(Const.COURSE_TYPE_MEDICINE, new SessionManager(requireContext()).getUser().getUser().getId());
        viewModel.setFavouriteCheck();
        viewModel.getCategoriesAdapter().setOnItemClick((pairs, categoriesItem) -> {
            Intent intent = new Intent(binding.getRoot().getContext(), QuizListActivity.class);
            intent.putExtra("name", (String) pairs[0].second);
            intent.putExtra("logo", (String) pairs[1].second);
            intent.putExtra("data", new Gson().toJson(categoriesItem));
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
            startActivity(intent, activityOptions.toBundle());
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