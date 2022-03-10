package com.retrytech.quizbox.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.retrytech.quizbox.BuildConfig;
import com.retrytech.quizbox.model.user.CurrentUser;
import com.retrytech.quizbox.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RedeemRequestViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private CurrentUser.User user;
    private float perCoinRate = 0f;
    private float currentPrice = 0f;
    private String amount;
    private String paymentMethod;
    private String account;

    public void afterTextChanged(CharSequence charSequence, int type) {
        if (type == 0) {
            account = charSequence.toString();
        }
    }

    public void addRedeemRequest() {
        if (user.getWallet() < 10000) {
            toast.setValue("Not enough points for redeem...");
            return;
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            toast.setValue("Please select payment method...");
            return;
        }
        if (account == null || account.isEmpty()) {
            toast.setValue("Please enter account Credential...");
            return;
        }
        disposable.add(Global.initRetrofit().addRedeemRequest(BuildConfig.APIKEY, amount, paymentMethod, account, String.valueOf(Global.userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((restResponse, throwable) -> {
                    if (restResponse != null && restResponse.isStatus()) {
                        isSuccess.setValue(true);
                    } else if (throwable != null) {
//
                    }
                }));
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(MutableLiveData<Boolean> isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public CurrentUser.User getUser() {
        return user;
    }

    public void setUser(CurrentUser.User user) {
        this.user = user;
    }

    public float getPerCoinRate() {
        return perCoinRate;
    }

    public void setPerCoinRate(float perCoinRate) {
        this.perCoinRate = perCoinRate;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
