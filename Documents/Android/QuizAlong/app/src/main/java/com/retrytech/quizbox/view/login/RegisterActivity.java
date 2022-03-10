package com.retrytech.quizbox.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityRegisterBinding;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.viewmodel.RegisterViewModel;

public class RegisterActivity extends BaseActivity {


    private RegisterViewModel viewModel;
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        initObserve();
        binding.setViewModel(viewModel);
    }

    private void initObserve() {
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
        viewModel.getOnSuccess().observe(this, user -> {
            Toast.makeText(this, getResources().getString(R.string.sign_up_successfully), Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }
}