package com.thequizapp.quizalong.viewmodel;

import android.util.Pair;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.model.rest.RestResponse;
import com.thequizapp.quizalong.utils.Global;

import java.util.HashMap;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaymentViewModel extends ViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<String> orderId = new MutableLiveData<>();
    private MutableLiveData<RestResponse> onSuccess = new MutableLiveData<>();
    private MutableLiveData<Pair<String, Boolean>> onStatus = new MutableLiveData<>();
    private int amount = 25;
    private CompositeDisposable disposable = new CompositeDisposable();
    private QuizItem quiz;

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<RestResponse> getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(MutableLiveData<RestResponse> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public MutableLiveData<Pair<String, Boolean>> getOnStatus() {
        return onStatus;
    }

    public void setOnStatus(MutableLiveData<Pair<String, Boolean>> onStatus) {
        this.onStatus = onStatus;
    }

    public MutableLiveData<String> getOrderId() {
        return orderId;
    }

    public void setOrderId(MutableLiveData<String> orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public void setQuiz(QuizItem quiz) {
        this.quiz = quiz;
    }

    public QuizItem getQuiz() {
        return quiz;
    }

    public void getOrderDetails() {
        disposable.add(Global.initRetrofit().getRzpOrderId(BuildConfig.APIKEY, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((orderResponse, throwable) -> {
                    if (orderResponse != null) {
                        orderId.postValue(orderResponse.getOrderId());
                    }
                    onStatus.postValue(new Pair<>(orderResponse.getMessage(), orderResponse.isStatus()));
                }));
    }

    public void addpaymentdata(HashMap<String, String> hashMap) {
        disposable.add(Global.initRetrofit().addPaymentData(BuildConfig.APIKEY, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((orderResponse, throwable) -> {
                    if (orderResponse != null && orderResponse.isStatus()) {
                        onSuccess.postValue(orderResponse);
                    }
                    onStatus.postValue(new Pair<>(orderResponse.getMessage(), orderResponse.isStatus()));
                }));
    }
}
