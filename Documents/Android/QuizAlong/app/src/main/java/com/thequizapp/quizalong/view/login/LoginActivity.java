package com.thequizapp.quizalong.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityLoginBinding;
import com.thequizapp.quizalong.databinding.ItemForgotPassBinding;
import com.thequizapp.quizalong.databinding.ItemSetPasswordBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.loginmaneger.FaceBookLoginManager;
import com.thequizapp.quizalong.utils.loginmaneger.GoogleLoginManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.home.CourseSelectionActivity;
import com.thequizapp.quizalong.view.main.MainActivity;
import com.thequizapp.quizalong.viewmodel.ForgotPasswordViewModel;
import com.thequizapp.quizalong.viewmodel.LoginViewModel;
import com.thequizapp.quizalong.viewmodel.SetPasswordViewModel;

import org.json.JSONException;

import java.util.HashMap;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    LoginViewModel viewModel;
    private FaceBookLoginManager faceBookLoginManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Inside login view ","");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sessionManager = new SessionManager(this);
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initObserve() {
        viewModel.getToast().observe(this, toastMsg -> {
            if (toastMsg != null && !toastMsg.isEmpty()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                Snackbar.make(binding.getRoot(), toastMsg, 2000)
                        .setBackgroundTint(getColorById(R.color.black))
                        .setTextColor(getColorById(R.color.white))
                        .show();
            }
        });
        viewModel.getOnSuccess().observe(this, user -> {
            Log.e("....",""+user);
            sessionManager.saveUser(user);

            Toast.makeText(this, getResources().getString(R.string.log_in_successfully), Toast.LENGTH_SHORT).show();
            /*startActivity(new Intent(this, MainActivity.class));
            finishAffinity();*/
            if(sessionManager.getUser().getAdditional_info() == 1) {
                startActivity(new Intent(this, AdditionalInfoActivity.class));
            }else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finishAffinity();
        });

        viewModel.getNoRecord().observe(this, email ->{
            Log.e("NoRecord...",""+email);
            /*if(email){*/
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                ItemSetPasswordBinding setPasswordBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_set_password, null, false);
                SetPasswordViewModel setPasswordViewModel = new ViewModelProvider(this).get(SetPasswordViewModel.class);
                setPasswordViewModel.getToast().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
                setPasswordViewModel.getOnSuccess().observe(this, isSuccess -> bottomSheetDialog.dismiss());
                setPasswordViewModel.setEmail(email);
                setPasswordViewModel.getOnSuccess().observe(this, user -> {
                    Toast.makeText(this, getResources().getString(R.string.sign_up_successfully), Toast.LENGTH_LONG).show();
                    //onBackPressed();
                });
                setPasswordBinding.edtEmail.setText(email);
                setPasswordBinding.ivClose.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
                setPasswordBinding.setViewModel(setPasswordViewModel);

                bottomSheetDialog.setContentView(setPasswordBinding.getRoot());
                bottomSheetDialog.show();

            /*}*/
        });

    }

    private void initListener() {
        binding.lytForgot.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            ItemForgotPassBinding forgotPassBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_forgot_pass, null, false);
            ForgotPasswordViewModel passwordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
            passwordViewModel.getToast().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
            passwordViewModel.getOnSuccess().observe(this, isSuccess -> bottomSheetDialog.dismiss());
            forgotPassBinding.ivClose.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            forgotPassBinding.setViewModel(passwordViewModel);
            bottomSheetDialog.setContentView(forgotPassBinding.getRoot());
            bottomSheetDialog.show();
        });
        binding.lytNewHere.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        GoogleLoginManager googleLoginManager = new GoogleLoginManager(this);
        faceBookLoginManager = new FaceBookLoginManager(this, jsonObject -> {
            try {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("fullname", jsonObject.getString("name"));
                hashMap.put("identity", jsonObject.getString("id"));
                hashMap.put("password", "");
                hashMap.put("social_login", "1");
                hashMap.put("firebase_auth", "0");
                viewModel.getIsLoading().set(true);
                viewModel.registerUser(hashMap);
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }
        });
        binding.lytGoogle.setOnClickListener(v -> googleLoginManager.onLogin());
        binding.lytFb.setOnClickListener(v -> faceBookLoginManager.onClickLogin());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(">>> ",requestCode+" "+resultCode+" "+data);
        faceBookLoginManager.callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e(">>>> 1",""+account.getDisplayName());
            if (account != null) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("fullname", account.getDisplayName());
                hashMap.put("identity", account.getEmail());
                hashMap.put("password", "");
                hashMap.put("social_login", "1");
                hashMap.put("firebase_auth", "0");
                viewModel.getIsLoading().set(true);
                viewModel.registerUser(hashMap);
            }

        } catch (ApiException ignored) {
            Log.d("TAG", "");
        }
    }
}