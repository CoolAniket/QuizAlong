package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.RedeemRequestAdapter;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryRedeemChildViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private RedeemRequestAdapter redeemRequestAdapter = new RedeemRequestAdapter();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private ObservableBoolean isLoading = new ObservableBoolean(true);

    public RedeemRequestAdapter getRedeemRequestAdapter() {
        return redeemRequestAdapter;
    }

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void getHistoryOfRedeem(int position) {
        disposable.add(Global.initRetrofit().fetchRedeemsRequests(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((redeemRequest, throwable) -> {
                    if (redeemRequest != null) {
                        redeemRequestAdapter.updateData(redeemRequest.getRedeems());
                        isEmpty.set(redeemRequest.getRedeems().isEmpty());
                    } else if (throwable != null) {
//
                    }
                }));
    }
}
