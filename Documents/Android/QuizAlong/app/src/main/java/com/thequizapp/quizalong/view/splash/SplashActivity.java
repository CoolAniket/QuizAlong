package com.thequizapp.quizalong.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.messaging.FirebaseMessaging;
import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.main.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        FirebaseMessaging.getInstance().subscribeToTopic("quizalong");
        SessionManager sessionManager = new SessionManager(this);
        disposable.add(
                Global.initRetrofit()
                        .getAllSettings(BuildConfig.APIKEY, "1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe((settings, throwable) -> {
                            if (settings != null) {
                                sessionManager.saveSettings(settings);
                            }
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                if (sessionManager.getUser() == null) {
                                    startActivity(new Intent(this, WelcomeActivity.class));
                                } else {
                                    Global.userId.set(String.valueOf(sessionManager.getUser().getUser().getId()));
                                    startActivity(new Intent(this, MainActivity.class));
                                }
                                finishAffinity();
                            }, 1000);
                        })
        );

    }
}