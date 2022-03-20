package com.thequizapp.quizalong.utils.loginmaneger;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import static com.thequizapp.quizalong.view.BaseActivity.RC_SIGN_IN;

public class GoogleLoginManager {

    private final GoogleSignInClient mGoogleSignInClient;
    private final Activity context;

    public GoogleLoginManager(Activity context) {
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void onLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
