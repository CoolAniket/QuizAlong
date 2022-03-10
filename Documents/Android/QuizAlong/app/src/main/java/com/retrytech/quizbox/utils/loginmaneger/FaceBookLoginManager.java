package com.retrytech.quizbox.utils.loginmaneger;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

public class FaceBookLoginManager {
    public final CallbackManager callbackManager;
    private final Activity context;

    public FaceBookLoginManager(Activity context, OnFacebookLogin onFacebookLogin) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
        FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), (object, response) -> {
                            if (object != null) {
                                onFacebookLogin.onLoginSuccess(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "onError: " + error.getLocalizedMessage());
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager, mFacebookCallback);
    }

    public void onClickLogin() {
        LoginManager.getInstance().logInWithReadPermissions(context, Collections.singletonList("public_profile"));
        LoginManager.getInstance().logInWithReadPermissions(
                context,
                Arrays.asList("email", "public_profile")
        );
    }

    public interface OnFacebookLogin {
        void onLoginSuccess(JSONObject jsonObject);
    }
}
