package com.thequizapp.quizalong.view.web;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityWebViewBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebViewActivity extends BaseActivity {
    ActivityWebViewBinding binding;
    private ObservableBoolean isLoading = new ObservableBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        binding.wwLoader.setIsLoading(isLoading);
        WebSettings ws = binding.webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);

        SessionManager sessionManager = new SessionManager(this);
        binding.webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                // Return the app name after finish loading
                if(progress == 100)
                    binding.wwLoader.getIsLoading().set(false);
            }
        });
        int type = getIntent().getIntExtra("type", 0);
        String pdfUrl = type == 0 ? sessionManager.getPrivacyUrl() : sessionManager.getTermsUrl();
        binding.webview.loadUrl(pdfUrl);
        binding.tvTitle.setText(type == 0 ? "Privacy Policy" : "Terms of Use");
        binding.ivBack.setOnClickListener(v -> onBackPressed());
//        binding.wwLoader.getIsLoading().set(true);
//        new RetrivePDFfromUrl().execute(pdfUrl);
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
//            binding.webview.fromStream(inputStream).load();
        }
    }
}