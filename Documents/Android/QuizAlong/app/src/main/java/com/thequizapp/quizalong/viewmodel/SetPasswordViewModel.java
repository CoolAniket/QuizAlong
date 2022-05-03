package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SetPasswordViewModel extends ViewModel {

    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    //private MutableLiveData<Boolean> onSuccess = new MutableLiveData<>();
    private MutableLiveData<CurrentUser> onSuccess = new MutableLiveData<>();
    private String email;
    private String password;
    private String retypePassword;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<CurrentUser> getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(MutableLiveData<CurrentUser> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public void afterTextChanged(CharSequence charSequence, int type) {
        if (type == 0) {
            email = charSequence.toString();
        }else if (type == 1) {
            password = charSequence.toString();
        }
        else if (type == 2) {
            retypePassword = charSequence.toString();
        }
    }
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void registerUser(HashMap<String, String> hashMap) {
        disposable.add(Global.initRetrofit().registerUser(BuildConfig.APIKEY, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((user, throwable) -> {
                    if (user != null) {
                        onSuccess.setValue(user);
                    } else if (throwable != null) {
                        toast.setValue(throwable.getLocalizedMessage());
                    }
                }));
    }
    public void forgotPassword() {
        if (email == null || email.isEmpty()) {
            toast.setValue("Please enter email");
            return;
        }
        Log.e("....",email+"");
        if (!isEmailValid(email)) {
            toast.setValue("Please enter valid email");
            return;
        }
        if (password == null || password.isEmpty()) {
            toast.setValue("Please enter password");
            return;
        }
        if (retypePassword == null || retypePassword.isEmpty()) {
            toast.setValue("Please enter confirm password");
            return;
        }
        if (!retypePassword.equals(password)) {
            toast.setValue("Password does not match");
            return;
        }
        isLoading.set(true);

        /*FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    isLoading.set(false);
                    if (task.isSuccessful()) {
                        toast.setValue("Email sent successfully.");
                        onSuccess.setValue(true);
                    } else if (task.getException() != null) {
                        toast.setValue(task.getException().getLocalizedMessage());
                    }
                });*/

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                isLoading.set(false);
                                toast.setValue("user email is verified...!");
                            } else {
                                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("fullname", email.split("@")[0]);
                                        hashMap.put("identity", email);
                                        hashMap.put("password", password);
                                        hashMap.put("social_login", "0");
                                        hashMap.put("firebase_auth", "1");

                                        registerUser(hashMap);
                                    }
                                });
                            }
                        }
                    } else {
                        if (task.getException() != null) {
                            isLoading.set(false);
                            toast.setValue(task.getException().getLocalizedMessage());
                        }
                    }

                });
    }
}
