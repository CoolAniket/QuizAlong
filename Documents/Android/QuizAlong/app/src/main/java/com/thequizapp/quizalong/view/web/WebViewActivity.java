package com.thequizapp.quizalong.view.web;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityWebViewBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebViewActivity extends BaseActivity {
    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
//        WebSettings ws = binding.webview.getSettings();
//        ws.setJavaScriptEnabled(true);
//        ws.setAllowFileAccess(true);

        SessionManager sessionManager = new SessionManager(this);
        String pdfUrl = getIntent().getIntExtra("type", 0) == 0 ? sessionManager.getPrivacyUrl() : sessionManager.getTermsUrl();
//        binding.webview.loadUrl();
        binding.tvTitle.setText(getIntent().getIntExtra("type", 0) == 0 ? "Privacy Policy" : "Terms of Use");
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.wwLoader.getIsLoading().set(true);
        new RetrivePDFfromUrl().execute(pdfUrl);
    }

    // create an async task class for loading pdf file from URL.
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            binding.wwLoader.getIsLoading().set(false);
            // task we are loading our pdf in our pdf view.
            binding.webview.fromStream(inputStream).load();
        }
    }
}