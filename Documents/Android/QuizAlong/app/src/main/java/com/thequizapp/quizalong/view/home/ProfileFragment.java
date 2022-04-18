package com.thequizapp.quizalong.view.home;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.FragmentProfileBinding;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.edit.EditProfileActivity;
import com.thequizapp.quizalong.view.redeem.RedeemRequestActivity;
import com.thequizapp.quizalong.viewmodel.MainViewModel;
import com.thequizapp.quizalong.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private RewardAds rewardAds;

    public ProfileFragment() {
        // Required empty public constructor
    }

    FragmentProfileBinding binding;
    ProfileViewModel viewModel;
    MainViewModel parentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        if (getActivity() != null) {
            parentViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        }
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void initView() {
        viewModel.getUserProfile();
    }

    private void initObserve() {
        if (getActivity() != null) {
            rewardAds = new RewardAds(getActivity());
            SessionManager sessionManager = new SessionManager(getActivity());
            if (sessionManager.getGameSettings() != null) {
                viewModel.setCurrentPrice(sessionManager.getGameSettings().getCoinsToUsd());
            }
            /*viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                binding.setViewModel(viewModel);
                sessionManager.saveUser(user);
            });*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initListener() {
        binding.cardRedeem.setOnClickListener(v -> {
            if (viewModel.getUser().getValue().getUser().getWallet() > 0) {
                startActivity(new Intent(getActivity(), RedeemRequestActivity.class));
            } else {
                Toast.makeText(getActivity(), "Win more quiz...!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivEdit.setOnClickListener(v -> {
            Pair<View, String>[] pair = new Pair[1];
            pair[0] = new Pair<>(binding.ivProfile, binding.ivProfile.getTransitionName());
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);
            startActivity(new Intent(getActivity(), EditProfileActivity.class), activityOptions.toBundle());
        });
        binding.lytLogOut.setOnClickListener(v -> new CustomDialogBuilder(getActivity()).showLogOutDialog());
        binding.ivCopy.setOnClickListener(v -> {
            if (getActivity() != null) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                if (viewModel.getUser().getValue() != null) {
                    ClipData clip = ClipData.newPlainText("Copied", viewModel.getUser().getValue().getUser().getReferCode());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getActivity(), "Copied successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.ivShare.setOnClickListener(v -> {
            if (getActivity() != null && viewModel.getUser().getValue() != null) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "Hey there, I play many quiz games and earn rewards in this app "
                        + viewModel.getUser().getValue().getUser().getTotalPoints()
                        + ". User this refer code "
                        + viewModel.getUser().getValue().getUser().getReferCode()
                        + " and have fun.\n https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getActivity().getResources().getString(R.string.app_name));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }
}