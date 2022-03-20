package com.thequizapp.quizalong.view.redeem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.FragmentRedeemBinding;
import com.thequizapp.quizalong.viewmodel.HistoryRedeemChildViewModel;
import com.thequizapp.quizalong.viewmodel.HistoryRedeemRequestViewModel;


public class RedeemFragment extends Fragment {

    FragmentRedeemBinding binding;

    HistoryRedeemRequestViewModel parentViewModel;
    HistoryRedeemChildViewModel viewModel;

    public RedeemFragment() {
//
    }

    public static Fragment getInstance(int position) {
        RedeemFragment fragment = new RedeemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem, container, false);
        if (getActivity() != null) {
            parentViewModel = new ViewModelProvider(getActivity()).get(HistoryRedeemRequestViewModel.class);
            viewModel = new ViewModelProvider(this).get(HistoryRedeemChildViewModel.class);
            initView();
            binding.setViewModel(viewModel);
        }
        return binding.getRoot();
    }

    private void initView() {
        if (getArguments() != null) {
            viewModel.getHistoryOfRedeem(getArguments().getInt("position", -1));
        }
    }
}