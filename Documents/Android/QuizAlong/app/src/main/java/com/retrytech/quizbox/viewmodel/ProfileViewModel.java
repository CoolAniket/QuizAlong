package com.retrytech.quizbox.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.retrytech.quizbox.BuildConfig;
import com.retrytech.quizbox.model.user.CurrentUser;
import com.retrytech.quizbox.utils.Global;

import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<CurrentUser> user = new MutableLiveData<>();
    private int currentPrice = 0;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<CurrentUser> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<CurrentUser> user) {
        this.user = user;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void getUserProfile() {
        disposable.add(Global.initRetrofit().getUserProfile(BuildConfig.APIKEY, Global.userId.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((userProfile, throwable) -> {
                    if (userProfile != null) {
                        user.setValue(userProfile);
                    } else if (throwable != null) {
//
                    }
                }));
    }

    public void addRewardedPoints() {
        disposable.add(Global.initRetrofit().addPointsToWallet(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(ThreadLocalRandom.current().nextInt(50, 100)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) -> getUserProfile()));
    }
}
