package com.thequizapp.quizalong.view.web;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;

import androidx.databinding.DataBindingUtil;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityWebViewBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;

import java.lang.reflect.Method;

public class WebViewActivity extends BaseActivity {
    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        WebSettings ws = binding.webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            try {
                Log.d("tag", "Enabling HTML5-Features");
                Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", Boolean.TYPE);
                m1.invoke(ws, Boolean.TRUE);

                Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", Boolean.TYPE);
                m2.invoke(ws, Boolean.TRUE);

                Method m3 = WebSettings.class.getMethod("setDatabasePath", String.class);
                m3.invoke(ws, "/data/data/" + getPackageName() + "/databases/");

                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", Long.TYPE);
                m4.invoke(ws, 1024 * 1024 * 8);

                Method m5 = WebSettings.class.getMethod("setAppCachePath", String.class);
                m5.invoke(ws, "/data/data/" + getPackageName() + "/cache/");

                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", Boolean.TYPE);
                m6.invoke(ws, Boolean.TRUE);

                Log.d("TAG", "Enabled HTML5-Features");
            } catch (Exception e) {
                Log.e("TAG", "Reflection fail", e);
            }
        }
        SessionManager sessionManager = new SessionManager(this);

        binding.webview.loadUrl(getIntent().getIntExtra("type", 0) == 0 ? sessionManager.getPrivacyUrl() : sessionManager.getTermsUrl());
        binding.tvTitle.setText(getIntent().getIntExtra("type", 0) == 0 ? "Privacy Policy" : "Terms of Use");
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}