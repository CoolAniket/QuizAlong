package com.retrytech.quizbox.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.DialogAnswerResultBinding;
import com.retrytech.quizbox.databinding.DialogLifeLineBinding;
import com.retrytech.quizbox.databinding.DialogRapidFireBinding;
import com.retrytech.quizbox.databinding.DialogSimpleBinding;
import com.retrytech.quizbox.view.splash.SplashActivity;

public class CustomDialogBuilder {
    private final Context mContext;
    private Dialog mBuilder = null;

    public CustomDialogBuilder(Context context) {
        this.mContext = context;
        if (mContext != null) {
            mBuilder = new Dialog(mContext);
            mBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mBuilder.setCancelable(false);
            mBuilder.setCanceledOnTouchOutside(false);

            if (mBuilder.getWindow() != null) {
                mBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    public void showAnswerResultDialog(boolean isAnswerTrue, String points, OnAnswerDismissListener onAnswerDismissListener) {
        if (mContext == null)
            return;
        DialogAnswerResultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_answer_result, null, false);
        binding.ivAnsResult.setImageResource(isAnswerTrue ? R.drawable.correct : R.drawable.wrong);
        binding.tvPoints.setText(String.valueOf(points));
        binding.tvPoints.setTextSize(22);
        if (!isAnswerTrue) {
            binding.tvTitle.setText(R.string.correct_answer_is);
        }
        if (!isAnswerTrue) {
            binding.tvTxtPoints.setVisibility(View.GONE);
        }
        binding.lyrNext.setOnClickListener(v -> {
            onAnswerDismissListener.onDismiss();
            mBuilder.dismiss();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();

    }

    public void showLifeLineDialog(boolean isUseDoubleDeep, boolean isUseFiftyFifty, OnLifeLineListener onLifeLineListener) {
        //usedLifeLine 0 = Double Dip, 1 = Fifty Fifty

        if (mContext == null)
            return;
        DialogLifeLineBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_life_line, null, false);
        if (isUseDoubleDeep) {
            binding.ivDoubleDisable.setVisibility(View.VISIBLE);
        }
        if (isUseFiftyFifty) {
            binding.ivFiftyDisable.setVisibility(View.VISIBLE);
        }
        binding.cardDoubleDip.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onDoubleDipClick();
        });
        binding.cardFiftyFifty.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onFiftyFiftyClick();
        });
        binding.tvCancel.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onDismissClick();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();
    }

    public void showQuizTypeDialog(boolean isRapidFire, OnQuitTypeListener onDismissListener) {
        if (mContext == null)
            return;
        DialogRapidFireBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_rapid_fire, null, false);
        if (!isRapidFire) {
            binding.ivThumb.setImageResource(R.drawable.ic_warning);
            binding.tvTitle.setText("Negative Marking");
            binding.tvDes.setText("This quiz is negative marking,\nIf you answer incorrectly,\ndouble points will be\ndeducted from your wallet.");
        }
        binding.btnStart.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onStartDismiss();
        });
        binding.tvCancel.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onCancelDismiss();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();
    }

    public void showLogOutDialog() {
        if (mContext == null)
            return;
        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);
        DialogSimpleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_simple, null, false);
        binding.btnLogOut.setOnClickListener(v -> {
            // logout google
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(mContext, gso);
            googleSignInClient.signOut();

            // logout facebook
            LoginManager.getInstance().logOut();
            mBuilder.dismiss();
            SessionManager sessionManager = new SessionManager(mContext);
            sessionManager.clear();
            mContext.startActivity(new Intent(mContext, SplashActivity.class));
        });
        binding.tvCancel.setOnClickListener(v -> mBuilder.dismiss());
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();
    }

    public void showSimpleDialog(int resId, String title, String des, String positive, String negative, OnDismissListener onDismissListener) {
        if (mContext == null)
            return;
        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);
        DialogSimpleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_simple, null, false);
        binding.ivThumb.setImageResource(resId);
        binding.tvTitle.setText(title);
        binding.tvDes.setText(des);
        binding.btnLogOut.setText(positive);
        binding.tvCancel.setText(negative);
        binding.btnLogOut.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onPositiveDismiss();
        });
        binding.tvCancel.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onNegativeDismiss();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();
    }

    public interface OnAnswerDismissListener {
        void onDismiss();
    }

    public interface OnDismissListener {
        void onPositiveDismiss();

        void onNegativeDismiss();
    }

    public interface OnLifeLineListener {
        void onDoubleDipClick();

        void onFiftyFiftyClick();

        void onDismissClick();
    }

    public interface OnQuitTypeListener {
        void onCancelDismiss();

        void onStartDismiss();
    }
}
