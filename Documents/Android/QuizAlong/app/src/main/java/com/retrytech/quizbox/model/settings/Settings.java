package com.retrytech.quizbox.model.settings;

import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("app_settings")
    private AppSettings appSettings;

    @SerializedName("game_settings")
    private GameSettings gameSettings;

    @SerializedName("message")
    private String message;

    @SerializedName("facebook_settings")
    private FacebookSettings facebookSettings;

    @SerializedName("status")
    private boolean status;

    @SerializedName("admob_settings")
    private AdmobSettings admobSettings;

    public AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacebookSettings getFacebookSettings() {
        return facebookSettings;
    }

    public void setFacebookSettings(FacebookSettings facebookSettings) {
        this.facebookSettings = facebookSettings;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AdmobSettings getAdmobSettings() {
        return admobSettings;
    }

    public void setAdmobSettings(AdmobSettings admobSettings) {
        this.admobSettings = admobSettings;
    }

    public static class AdmobSettings {

        @SerializedName("admob_banner")
        private String admobBanner;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("admob_native")
        private String admobNative;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("admob_int")
        private String admobInt;

        @SerializedName("admob_rewarded")
        private String admobRewarded;

        public String getAdmobBanner() {
            return admobBanner;
        }

        public void setAdmobBanner(String admobBanner) {
            this.admobBanner = admobBanner;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getAdmobNative() {
            return admobNative;
        }

        public void setAdmobNative(String admobNative) {
            this.admobNative = admobNative;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdmobInt() {
            return admobInt;
        }

        public void setAdmobInt(String admobInt) {
            this.admobInt = admobInt;
        }

        public String getAdmobRewarded() {
            return admobRewarded;
        }

        public void setAdmobRewarded(String admobRewarded) {
            this.admobRewarded = admobRewarded;
        }
    }

    public static class AppSettings {

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("terms")
        private String terms;

        @SerializedName("more_apps_and")
        private String moreAppsAnd;

        @SerializedName("more_apps_ios")
        private String moreAppsIos;

        @SerializedName("privacy")
        private String privacy;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getMoreAppsAnd() {
            return moreAppsAnd;
        }

        public void setMoreAppsAnd(String moreAppsAnd) {
            this.moreAppsAnd = moreAppsAnd;
        }

        public String getMoreAppsIos() {
            return moreAppsIos;
        }

        public void setMoreAppsIos(String moreAppsIos) {
            this.moreAppsIos = moreAppsIos;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class FacebookSettings {

        @SerializedName("fb_app")
        private String fbApp;

        @SerializedName("fb_banner")
        private String fbBanner;

        @SerializedName("fb_native")
        private String fbNative;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("fb_rewarded")
        private String fbRewarded;

        @SerializedName("fb_int")
        private String fbInt;

        public String getFbApp() {
            return fbApp;
        }

        public void setFbApp(String fbApp) {
            this.fbApp = fbApp;
        }

        public String getFbBanner() {
            return fbBanner;
        }

        public void setFbBanner(String fbBanner) {
            this.fbBanner = fbBanner;
        }

        public String getFbNative() {
            return fbNative;
        }

        public void setFbNative(String fbNative) {
            this.fbNative = fbNative;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFbRewarded() {
            return fbRewarded;
        }

        public void setFbRewarded(String fbRewarded) {
            this.fbRewarded = fbRewarded;
        }

        public String getFbInt() {
            return fbInt;
        }

        public void setFbInt(String fbInt) {
            this.fbInt = fbInt;
        }
    }

    public static class GameSettings {

        @SerializedName("rapidfire_secs")
        private int rapidfireSecs;

        @SerializedName("silver_amount")
        private int silverAmount;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("coins_to_usd")
        private int coinsToUsd;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("gold_amount")
        private int goldAmount;

        @SerializedName("pro_amount")
        private int proAmount;

        @SerializedName("refer_amount")
        private int referAmount;

        public int getRapidfireSecs() {
            return rapidfireSecs;
        }

        public void setRapidfireSecs(int rapidfireSecs) {
            this.rapidfireSecs = rapidfireSecs;
        }

        public int getSilverAmount() {
            return silverAmount;
        }

        public void setSilverAmount(int silverAmount) {
            this.silverAmount = silverAmount;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getCoinsToUsd() {
            return coinsToUsd;
        }

        public void setCoinsToUsd(int coinsToUsd) {
            this.coinsToUsd = coinsToUsd;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoldAmount() {
            return goldAmount;
        }

        public void setGoldAmount(int goldAmount) {
            this.goldAmount = goldAmount;
        }

        public int getProAmount() {
            return proAmount;
        }

        public void setProAmount(int proAmount) {
            this.proAmount = proAmount;
        }

        public int getReferAmount() {
            return referAmount;
        }

        public void setReferAmount(int referAmount) {
            this.referAmount = referAmount;
        }
    }
}