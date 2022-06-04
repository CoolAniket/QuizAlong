package com.thequizapp.quizalong.view.payment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.FragmentAllTransactionBinding;
import com.thequizapp.quizalong.databinding.FragmentPaymentHistoryBinding;
import com.thequizapp.quizalong.model.payment.TransactionResponse;
import com.thequizapp.quizalong.viewmodel.AllTransactionHistoryViewModel;
import com.thequizapp.quizalong.viewmodel.PaymentHistoryViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class PaymentsFragment extends Fragment {


    private final List<TransactionResponse.History> historyList;
    FragmentPaymentHistoryBinding binding;
    PaymentHistoryViewModel viewModel;



    public PaymentsFragment(List<TransactionResponse.History> list) {
        Log.d("UpcomingQuizFragment", "");
        historyList = list;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_history, container, false);
        viewModel = new ViewModelProvider(this).get(PaymentHistoryViewModel.class);
        initListener();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initListener() {
        viewModel.updateTransactionData(historyList);

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