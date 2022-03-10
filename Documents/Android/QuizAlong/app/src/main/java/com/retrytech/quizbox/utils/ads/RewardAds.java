package com.retrytech.quizbox.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.utils.SessionManager;

public class RewardAds {

    private final Context context;
    private OnRewarded onRewarded;
    private SessionManager sessionManager;
    private RewardedAd mRewardedAd;
    private RewardedVideoAd rewardedVideoAd;

    public RewardAds(Context context) {
        this.context = context;

        if (context != null) {
            sessionManager = new SessionManager(context);
            initAds();
        }
    }

    public void setOnRewarded(OnRewarded onRewarded) {
        this.onRewarded = onRewarded;
    }

    private void initAds() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, sessionManager.getAdmobRewardAds().isEmpty() ? context.getResources().getString(R.string.admob_rewarded) : sessionManager.getAdmobRewardAds(),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
        rewardedVideoAd = new RewardedVideoAd(context,
                sessionManager.getFBRewardAds().isEmpty() ? context.getResources().getString(R.string.admob_rewarded) : sessionManager.getFBRewardAds());
        rewardedVideoAd.loadAd();
    }

    public void showAds() {
        if (mRewardedAd != null) {
            mRewardedAd.show((Activity) context, rewardItem -> onRewarded.getReward());
        } else if (rewardedVideoAd.isAdLoaded()) {
            rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                @Override
                public void onRewardedVideoCompleted() {
                    onRewarded.getReward();
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Log.i("TAG", "onLoggingImpression: " + ad);
                }

                @Override
                public void onRewardedVideoClosed() {
                    Log.i("TAG", "onRewardedVideoClosed: ");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Log.i("TAG", "onError: ");
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Log.i("TAG", "onAdLoaded: ");
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Log.i("TAG", "onAdClicked: ");
                }
            });
            rewardedVideoAd.show();
        }
    }

    public interface OnRewarded {
        void getReward();
    }
}
