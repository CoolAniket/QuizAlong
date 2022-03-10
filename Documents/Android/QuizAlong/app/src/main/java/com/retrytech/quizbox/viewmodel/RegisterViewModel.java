package com.retrytech.quizbox.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.retrytech.quizbox.BuildConfig;
import com.retrytech.quizbox.model.user.CurrentUser;
import com.retrytech.quizbox.utils.Global;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<CurrentUser> onSuccess = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private CompositeDisposable disposable = new CompositeDisposable();
    private String fullName;
    private String email;
    private String password;
    private String reTypePassword;
    private String referralCode;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public void afterTextChanged(CharSequence charSequence, int type) {
        switch (type) {
            case 0:
                fullName = charSequence.toString();
                break;
            case 1:
                email = charSequence.toString();
                break;
            case 2:
                password = charSequence.toString();
                break;
            case 3:
                reTypePassword = charSequence.toString();
                break;
            case 4:
                referralCode = charSequence.toString();
                break;
            default:
                break;
        }
    }

    public void onRegisterClick() {
        if (fullName == null || fullName.isEmpty()) {
            toast.setValue("Please enter fullName...!");
            return;
        }
        if (email == null || email.isEmpty()) {
            toast.setValue("Please enter email...!");
            return;
        }
        if (!isEmailValid(email)) {
            toast.setValue("Please enter valid email...!");
            return;
        }
        if (password == null || password.isEmpty()) {
            toast.setValue("Please enter password...!");
            return;
        }
        if (reTypePassword == null || reTypePassword.isEmpty()) {
            toast.setValue("Please enter confirm password...!");
            return;
        }
        if (!reTypePassword.equals(password)) {
            toast.setValue("Password does not match...!");
            return;
        }
        isLoading.set(true);
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
                                        hashMap.put("fullname", fullName);
                                        hashMap.put("identity", email);
                                        if (referralCode != null && !referralCode.isEmpty()) {
                                            hashMap.put("refercode", referralCode);
                                        }
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

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public MutableLiveData<CurrentUser> getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(MutableLiveData<CurrentUser> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }
}
