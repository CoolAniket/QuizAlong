package com.thequizapp.quizalong.utils.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.thequizapp.quizalong.R;

public class GlideLoader {
    private final Context mContext;

    public GlideLoader(Context context) {
        this.mContext = context;
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(20f);
        circularProgressDrawable.setColorSchemeColors(
                getColor(R.color.napier_green),
                getColor(R.color.india_red),
                getColor(R.color.kellygreen),
                getColor(R.color.tufs_blue),
                getColor(R.color.tiffanyblue),
                getColor(R.color.Sanddtorm),
                getColor(R.color.salmonpink_1)
        );
        circularProgressDrawable.start();
    }

    public void loadWithCircleCrop(String imageUrl, ImageView imageView) {
        if (mContext != null && imageView != null) {
            Glide.with(mContext).load(imageUrl).apply(
                    new RequestOptions().circleCrop().error(
                            R.color.transparent
                    ).priority(Priority.HIGH)
            ).into(imageView);
        }
    }

    public void loadImage(String imageUrl, ImageView imageView) {
        if (mContext != null && imageView != null) {
            Glide.with(mContext).load(imageUrl).apply(
                    new RequestOptions().error(
                            R.color.transparent
                    ).priority(Priority.HIGH)
            ).into(imageView);
        }
    }

    private int getColor(int color) {
        return ContextCompat.getColor(mContext, color);
    }
}
