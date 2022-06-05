package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.TransactionAdapter;
import com.thequizapp.quizalong.model.payment.TransactionResponse;
import com.thequizapp.quizalong.utils.Global;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionHistoryViewModel extends ViewModel {
//    public final TransactionAdapter transactionAdapter = new TransactionAdapter();

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<TransactionResponse> historyData = new MutableLiveData<>();
    private MutableLiveData<Integer> mutableSelectedMenu = new MutableLiveData<>();
    private ObservableInt selectedMenu = new ObservableInt(0);

    public MutableLiveData<TransactionResponse> getHistoryData() {
        return historyData;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public void getTransactionHistory(int userId) {
        disposable.add(Global.initRetrofit().getTransactionHistory(BuildConfig.APIKEY, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((transactionResponse, throwable) -> {
                    if (transactionResponse != null && transactionResponse.getHistory() != null) {
//                        isEmpty.set(transactionResponse.getPastQuizes().isEmpty());
                        historyData.postValue(transactionResponse);
//                        transactionAdapter.updateData(transactionResponse.getHistory());
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
