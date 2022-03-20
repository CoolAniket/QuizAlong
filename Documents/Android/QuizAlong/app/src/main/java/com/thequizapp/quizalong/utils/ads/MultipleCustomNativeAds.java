package com.thequizapp.quizalong.utils.ads;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdsManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.utils.SessionManager;

import static com.google.android.gms.ads.formats.NativeAdOptions.ADCHOICES_TOP_RIGHT;

@Keep
public class MultipleCustomNativeAds {
    private final Context context;
    private SessionManager sessionManager;
    private boolean loadMoreAds = true;
    int offset;
    int index;
    private OnLoadAds onLoadAds;

    public MultipleCustomNativeAds(Context context, OnLoadAds onLoadAds, int offset) {
        this.context = context;
        this.onLoadAds = onLoadAds;
        this.offset = offset;
        index = offset - 1;
        if (context != null) {
            sessionManager = new SessionManager(context);
            initAds();
        }
    }

    private void initAds() {
        loadNativeAds();
    }


    private void loadNativeAds() {
        if (loadMoreAds) {
            AdLoader.Builder builder = null;
            builder = new AdLoader.Builder(context, sessionManager.getAdmobNative().isEmpty() ? context.getResources().getString(R.string.admob_native) : sessionManager.getAdmobNative());
            AdLoader adLoader = builder.forUnifiedNativeAd(
                    unifiedNativeAd -> {
                        loadMoreAds = onLoadAds.onLoad(unifiedNativeAd, index);
                        index = index + offset;

                        loadNativeAds();
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                    }).withAdListener(
                    new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            loadFbNativeAds();
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            .setRequestCustomMuteThisAd(true)
                            .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
                            .build()).build();
            adLoader.loadAds(new AdRequest.Builder().build(), 1);
        }
    }


    private void loadFbNativeAds() {
        if (context != null && loadMoreAds) {
            NativeAdsManager mNativeAdsManager = new NativeAdsManager(context, sessionManager.getFBNative().isEmpty() ? context.getResources().getString(R.string.facebook_native) : sessionManager.getFBNative(), 1);
            mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
                @Override
                public void onAdsLoaded() {
                    loadMoreAds = onLoadAds.onLoad(mNativeAdsManager.nextNativeAd(), index);
                    index = index + offset;
                    loadFbNativeAds();
                }

                @Override
                public void onAdError(AdError adError) {
                    Log.d("TAG", "");
                }
            });

            mNativeAdsManager.loadAds(NativeAdBase.MediaCacheFlag.ALL);
        }
    }


    public interface OnLoadAds {
        boolean onLoad(Object adsData, int position);
    }
}
