package com.thequizapp.quizalong.utils.ads;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.Keep;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.utils.SessionManager;

@Keep
public class BannerAds {
    SessionManager sessionManager;
    private Context context;
    private FrameLayout adsContainer;

    public BannerAds(Context context, FrameLayout adsContainer) {
        this.context = context;
        this.adsContainer = adsContainer;
        if (context != null) {
            sessionManager = new SessionManager(context);
            initAds();
        }
    }

    private void initAds() {

        AdView adView = new AdView(context);
        adView.setAdUnitId(sessionManager.getAdmobBanner().isEmpty() ? context.getResources().getString(R.string.admob_banner) : sessionManager.getAdmobBanner());

        adView.setAdSize(AdSize.BANNER);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadFbBanner();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("TAG", "onAdLoaded: ");
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
        if (adsContainer != null) {
            adsContainer.removeAllViews();
            adsContainer.addView(adView);
        }

    }

    private void loadFbBanner() {
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, sessionManager.getFBBanner().isEmpty() ? context.getResources().getString(R.string.fbbanner) : sessionManager.getFBBanner(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        if (adsContainer != null) {
            adsContainer.removeAllViews();
            adsContainer.addView(adView);
        }
        adView.loadAd();
    }
}
