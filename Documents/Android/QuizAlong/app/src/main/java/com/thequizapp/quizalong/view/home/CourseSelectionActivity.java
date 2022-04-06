package com.thequizapp.quizalong.view.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityCourseSelectionBinding;
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.main.MainActivity;
import com.thequizapp.quizalong.viewmodel.CourseSelectionViewModel;

public class CourseSelectionActivity extends BaseActivity {
    ActivityCourseSelectionBinding binding;
    CourseSelectionViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_selection);
        viewModel = new ViewModelProvider(this).get(CourseSelectionViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initListener() {
        Global.userId.set(String.valueOf(new SessionManager(this).getUser().getUser().getId()));

        viewModel.getCourseData(Const.COURSE_TYPE_MEDICINE, Integer.parseInt(Global.userId.get()));
//        viewModel.getCourseData(Const.COURSE_TYPE_MEDICINE, 35);

        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(this, getResources().getString(R.string.cat_add_successfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            }
        });
        viewModel.getToast().observe(this, toastMsg -> {
            if (toastMsg != null && !toastMsg.isEmpty()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                Snackbar.make(binding.getRoot(), toastMsg, 2000)
                        .setBackgroundTint(getColorById(R.color.black))
                        .setTextColor(getColorById(R.color.white))
                        .show();
            }
        });
    }
}