package com.thequizapp.quizalong.view.redeem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityRedeemRequestBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.utils.ads.InterstitialAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.RedeemRequestViewModel;

import java.util.Locale;

public class RedeemRequestActivity extends BaseActivity {

    ActivityRedeemRequestBinding binding;
    RedeemRequestViewModel viewModel;
    SessionManager sessionManager;
    private InterstitialAds interstitialAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_redeem_request);
        viewModel = new ViewModelProvider(this).get(RedeemRequestViewModel.class);
        initData();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initData() {
        sessionManager = new SessionManager(this);
        viewModel.setUser(sessionManager.getUser().getUser());
        float currentPrice = sessionManager.getGameSettings().getCoinsToUsd();
        viewModel.setCurrentPrice(currentPrice);
        viewModel.setPerCoinRate(1 / currentPrice);
        viewModel.setAmount(String.format(Locale.ENGLISH, "%.2f", viewModel.getUser().getWallet() * viewModel.getPerCoinRate()));
        new BannerAds(this, binding.bannerAds);
        interstitialAds = new InterstitialAds(this);
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
        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                if (interstitialAds != null) {
                    interstitialAds.showAds();
                }
                onBackPressed();
            }
        });
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.spPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] paymentMethods = getResources().getStringArray(R.array.payment);
                if (position != 0) {
                    viewModel.setPaymentMethod(paymentMethods[position]);
                } else {
                    viewModel.setPaymentMethod(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//
            }
        });
    }
}