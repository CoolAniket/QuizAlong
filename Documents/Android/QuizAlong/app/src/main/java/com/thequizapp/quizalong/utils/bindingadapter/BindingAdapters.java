package com.thequizapp.quizalong.utils.bindingadapter;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.image.GlideLoader;

public class BindingAdapters {
    BindingAdapters() {

    }

    @BindingAdapter({"app:circle_crop"})
    public static void loadProfileImage(ImageView view, String profileUrl) {
        if (!TextUtils.isEmpty(profileUrl)) {
            new GlideLoader(view.getContext()).loadWithCircleCrop(Const.ITEM_BASE_URL + profileUrl, view);
        }
    }

    @BindingAdapter({"app:normal_image"})
    public static void loadImage(ImageView view, String profileUrl) {
        if (!TextUtils.isEmpty(profileUrl)) {
            new GlideLoader(view.getContext()).loadImage(Const.ITEM_BASE_URL + profileUrl, view);
        }
    }

    @BindingAdapter({"app:scroll_text"})
    public static void scrollTextInTextView(TextView textView, boolean isScroll) {
        textView.setVerticalScrollBarEnabled(true);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    @BindingAdapter({"app:user_level"})
    public static void addUserLevelIcon(ImageView imageView, int userCoin) {
        SessionManager sessionManager = new SessionManager(imageView.getContext());
        if (sessionManager.getGameSettings() != null) {
            if (userCoin < sessionManager.getGameSettings().getSilverAmount()) {
                imageView.setImageResource(R.drawable.silver_star);
            } else if (userCoin < sessionManager.getGameSettings().getGoldAmount()) {
                imageView.setImageResource(R.drawable.gold_star);
            } else if (userCoin < sessionManager.getGameSettings().getProAmount()) {
                imageView.setImageResource(R.drawable.ic_diamond);
            }
        } else {
            imageView.setImageResource(R.drawable.silver_star);
        }
    }

    @BindingAdapter({"app:user_player"})
    public static void addUserLevelText(TextView textView, int userCoin) {
        SessionManager sessionManager = new SessionManager(textView.getContext());
        if (sessionManager.getGameSettings() != null) {
            if (userCoin < sessionManager.getGameSettings().getSilverAmount()) {
                textView.setText(R.string.silver_player);
            } else if (userCoin < sessionManager.getGameSettings().getGoldAmount()) {
                textView.setText(R.string.gold_player);
            } else if (userCoin < sessionManager.getGameSettings().getProAmount()) {
                textView.setText(R.string.pro_player);
            }
        } else {
            textView.setText(R.string.silver_player);
        }
    }
}
