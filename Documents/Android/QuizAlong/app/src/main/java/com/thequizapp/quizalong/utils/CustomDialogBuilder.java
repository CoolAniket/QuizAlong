package com.thequizapp.quizalong.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.razorpay.Checkout;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.DialogAnswerResultBinding;
import com.thequizapp.quizalong.databinding.DialogCategoryHelpBinding;
import com.thequizapp.quizalong.databinding.DialogEnrollForFreeBinding;
import com.thequizapp.quizalong.databinding.DialogLifeLineBinding;
import com.thequizapp.quizalong.databinding.DialogPaymentAmountBinding;
import com.thequizapp.quizalong.databinding.DialogRapidFireBinding;
import com.thequizapp.quizalong.databinding.DialogSimpleBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.view.splash.SplashActivity;

import java.text.MessageFormat;
import java.util.List;

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

    public void showCategoryHelpDialog() {
        if (mContext == null)
            return;
        DialogCategoryHelpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_category_help, null, false);
        binding.lyrNext.setOnClickListener(v -> {
            mBuilder.dismiss();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();

    }

    public void showLifeLineDialog(boolean isUseDoubleDeep, boolean isUseFiftyFifty, boolean isUseSkip, OnLifeLineListener onLifeLineListener) {
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
        Log.e("Dialog....",""+isUseSkip);
        if (isUseSkip) {
            binding.ivSkipDisable.setVisibility(View.VISIBLE);
        }
        binding.cardDoubleDip.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onDoubleDipClick();
        });
        binding.cardFiftyFifty.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onFiftyFiftyClick();
        });
        binding.cardSkip.setOnClickListener(v -> {
            mBuilder.dismiss();
            onLifeLineListener.onSkipClick();
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

    public void showPaymentAmountDialog(List<Integer> entry, OnPaymentAmountSelectListener onDismissListener) {
        if (mContext == null)
            return;
        DialogPaymentAmountBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_payment_amount, null, false);

        binding.btn50.setText(MessageFormat.format("INR {0}", entry.get(1)));
        binding.btn50.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onAmountClick(entry.get(1));
        });

        binding.btn100.setText(MessageFormat.format("INR {0}", entry.get(2)));
        binding.btn100.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onAmountClick(entry.get(2));
        });
        
        binding.tvCancel.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onDismissClick();
        });
        mBuilder.setContentView(binding.getRoot());
        mBuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mBuilder.show();
    }

    public void showEnrollForFreeDialog(QuizItem quizesItem, OnEnrollOptionSelectListener onDismissListener) {
        if (mContext == null)
            return;
//        String note = ;
        DialogEnrollForFreeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_enroll_for_free, null, false);
        binding.tvNote.setText(HtmlCompat.fromHtml(String.format(mContext.getResources().getString(R.string.note_top_50_of), quizesItem.getWinPercent(), quizesItem.getPrize()) , HtmlCompat.FROM_HTML_MODE_LEGACY));
        binding.btnFree.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onClick(OnEnrollOptionSelectListener.Type.FREE);
        });
        binding.btnPay.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onClick(OnEnrollOptionSelectListener.Type.PAY);
        });
        binding.tvCancel.setOnClickListener(v -> {
            mBuilder.dismiss();
            onDismissListener.onDismissClick();
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
            // RazorPar clear data
            Checkout.clearUserData(mContext.getApplicationContext());
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

        void onSkipClick();

        void onDismissClick();
    }

    public interface OnQuitTypeListener {
        void onCancelDismiss();

        void onStartDismiss();
    }

    public interface OnPaymentAmountSelectListener {

        void onAmountClick(int amount);

        void onDismissClick();
    }

    public interface OnEnrollOptionSelectListener {
        enum Type {
            PAY, FREE
        }

        void onClick(Type enrollmentType);

        void onDismissClick();
    }
}
