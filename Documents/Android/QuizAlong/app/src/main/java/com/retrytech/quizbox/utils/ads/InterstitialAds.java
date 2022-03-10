package com.retrytech.quizbox.utils.ads;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.utils.SessionManager;

public class InterstitialAds {

    private final Context context;
    private final SessionManager sessionManager;
    private InterstitialAd interstitialAd;
    private com.facebook.ads.InterstitialAd fbInterstitialAd;

    public InterstitialAds(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);
        if (context != null) {
            initAds();
        }
    }

    private void initAds() {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(sessionManager.getAdmobInt().isEmpty() ? context.getResources().getString(R.string.admob_interestial) : sessionManager.getAdmobInt());
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        fbInterstitialAd = new com.facebook.ads.InterstitialAd(context, sessionManager.getFBInt().isEmpty() ? context.getResources().getString(R.string.facebook_interstial) : sessionManager.getFBInt());
        fbInterstitialAd.loadAd();
    }

    public void showAds() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else if (fbInterstitialAd != null && fbInterstitialAd.isAdLoaded()) {
            fbInterstitialAd.show();
        }
    }
}
