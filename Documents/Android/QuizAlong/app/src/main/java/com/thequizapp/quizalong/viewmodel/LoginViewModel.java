package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.model.user.RegisterUser;
import com.thequizapp.quizalong.utils.Global;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<CurrentUser> onSuccess = new MutableLiveData<>();
    private MutableLiveData<String> noRecord = new MutableLiveData<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private String email;
    private String password;

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

    public MutableLiveData<String> getNoRecord() {
        return noRecord;
    }

    public void setNoRecord(MutableLiveData<String> noRecord) {
        this.noRecord = noRecord;
    }

    public void afterTextChanged(CharSequence charSequence, int type) {
        if (type == 0) {
            email = charSequence.toString();
        } else if (type == 1) {
            password = charSequence.toString();
        }
    }

    public void onLoginClick() {
        if (email == null || email.isEmpty()) {
            toast.setValue("Please enter email");
            return;
        }
        if (password == null || password.isEmpty()) {
            toast.setValue("Please enter password");
            return;
        }
        isLoading.set(true);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();

                        if (user != null) {
                            if (!user.isEmailVerified()) {
                                isLoading.set(false);
                                toast.setValue("Please verify email first");
                            } else {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("fullname", email.split("@")[0]);
                                hashMap.put("identity", email);
                                hashMap.put("password", password);
                                hashMap.put("social_login", "0");
                                hashMap.put("firebase_auth", "1");
                                registerUser(hashMap);
                            }
                        }
                    } else {
                        Log.e("Login......",""+task.getException().getLocalizedMessage());
                        if (task.getException() != null) {
                            if(task.getException().getLocalizedMessage().contains("There is no user record corresponding to this identifier. The user may have been deleted.")){
                                noRecord.setValue(email);
                                isLoading.set(false);
                                toast.setValue(task.getException().getLocalizedMessage());
                            }else {
                                isLoading.set(false);
                                toast.setValue(task.getException().getLocalizedMessage());
                            }
                        }
                    }

                });

    }

    public void registerUser(HashMap<String, String> hashMap) {
        disposable.add(Global.initRetrofit().registerUser(BuildConfig.APIKEY, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((user, throwable) -> {
                    Log.e(">>>> +",""+ user.isStatus()+".."+throwable);

                    if (user != null) {
                        if(user.isStatus()) {
                            Log.e(">>>> +",""+ user+".."+throwable);
                            onSuccess.setValue(user);
                        }else {
                            toast.setValue(user.getMessage());
                        }

                    } else if (throwable != null) {
                        toast.setValue(throwable.getLocalizedMessage());
                    }

                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
