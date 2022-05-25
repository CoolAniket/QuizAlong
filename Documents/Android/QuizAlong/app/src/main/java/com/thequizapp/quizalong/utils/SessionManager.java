package com.thequizapp.quizalong.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.settings.Settings;
import com.thequizapp.quizalong.model.user.CurrentUser;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(Const.APPLICATION_ID, MODE_PRIVATE);
        this.editor = this.pref.edit();
    }

    public void saveStringValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return pref.getString(key, "");
    }

    public void saveBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        return pref.getBoolean(key, false);
    }

    public void saveUser(CurrentUser user) {
        Global.userId.set(String.valueOf(user.getUser().getId()));
        editor.putString(Const.USER, new Gson().toJson(user));
        editor.apply();
    }
    public void saveNotificationToken(String token) {
        Log.d("saveNotificationToken ", "Refreshed token: " + token);
        editor.putString(Const.NOTIFICATION_TOKEN, token);
        editor.apply();
    }

    public String getNotificationToken() {

        String userString = pref.getString(Const.NOTIFICATION_TOKEN, "");
        Log.d("getNotificationToken ", "Refreshed token: " + userString);
        if (!userString.isEmpty()) {
            return userString;
        }
        return "";
    }

    public CurrentUser getUser() {
        String userString = pref.getString(Const.USER, "");
        if (!userString.isEmpty()) {
            return new Gson().fromJson(userString, CurrentUser.class);
        }
        return null;
    }

    public void saveAdditionalDetails(int val) {
        editor.putInt(Const.USER_ADDITION_DETAILS, val);
        editor.apply();
    }

    public int getAdditionalDetails() {
        return pref.getInt(Const.USER_ADDITION_DETAILS, -1);
    }

   public void saveCourseSelection(int val) {
        editor.putInt(Const.USER_COURSE_SELECTION, val);
        editor.apply();
    }

    public int getCourseSelection() {
        return pref.getInt(Const.USER_COURSE_SELECTION, -1);

    }

    public void saveSettings(Settings settings) {
        editor.putString(Const.APP_SETTINGS, new Gson().toJson(settings));
        editor.apply();
    }

    public Settings getSettings() {
        String userString = pref.getString(Const.APP_SETTINGS, "");
        if (!userString.isEmpty()) {
            return new Gson().fromJson(userString, Settings.class);
        }
        return null;
    }

    public Settings.GameSettings getGameSettings() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getGameSettings();
        }
        return null;
    }

    public Settings.AdmobSettings getAdmobSettings() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getAdmobSettings();
        }
        return null;
    }

    public Settings.FacebookSettings getFBSettings() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getFacebookSettings();
        }
        return null;
    }

    public Settings.AppSettings getAppSettings() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getAppSettings();
        }
        return null;
    }

    public int getRapidFireTime() {
        int time = 15;
        Settings.GameSettings gameSettings = getGameSettings();
        if (gameSettings != null) {
            time = gameSettings.getRapidfireSecs();
        }
        return time;
    }

    public String getAdmobNative() {
        Settings.AdmobSettings admobSettings = getAdmobSettings();
        if (admobSettings != null) {
            return admobSettings.getAdmobNative();
        }
        return "";
    }

    public String getFBNative() {
        Settings.FacebookSettings admobSettings = getFBSettings();
        if (admobSettings != null) {
            return admobSettings.getFbNative();
        }
        return "";
    }

    public String getAdmobRewardAds() {
        Settings.AdmobSettings admobSettings = getAdmobSettings();
        if (admobSettings != null) {
            return admobSettings.getAdmobRewarded();
        }
        return "";
    }

    public String getFBRewardAds() {
        Settings.FacebookSettings admobSettings = getFBSettings();
        if (admobSettings != null) {
            return admobSettings.getFbRewarded();
        }
        return "";
    }

    public String getAdmobInt() {
        Settings.AdmobSettings admobSettings = getAdmobSettings();
        if (admobSettings != null) {
            return admobSettings.getAdmobInt();
        }
        return "";
    }

    public String getFBInt() {
        Settings.FacebookSettings admobSettings = getFBSettings();
        if (admobSettings != null) {
            return admobSettings.getFbInt();
        }
        return "";
    }

    public String getAdmobBanner() {
        Settings.AdmobSettings admobSettings = getAdmobSettings();
        if (admobSettings != null) {
            return admobSettings.getAdmobBanner();
        }
        return "";
    }

    public String getFBBanner() {
        Settings.FacebookSettings admobSettings = getFBSettings();
        if (admobSettings != null) {
            return admobSettings.getFbBanner();
        }
        return "";
    }

    public void clear() {
        editor.clear().apply();
        Global.userId.set("");
    }

    public String getPrivacyUrl() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getPrivacyPolicy();
        }
        return "";
    }

    public String getTermsUrl() {
        Settings settings = getSettings();
        if (settings != null) {
            return settings.getTermsConditions();
        }
        return "";
    }
}
