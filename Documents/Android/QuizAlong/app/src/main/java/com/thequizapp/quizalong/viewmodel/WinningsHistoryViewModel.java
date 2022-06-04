package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.adapter.WinningsAdapter;
import com.thequizapp.quizalong.model.payment.TransactionResponse;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class WinningsHistoryViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private WinningsAdapter winningsAdapter = new WinningsAdapter();


    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setWinningsAdapter(WinningsAdapter winningsAdapter) {
        this.winningsAdapter = winningsAdapter;
    }

    public WinningsAdapter getWinningsAdapter() {
        return winningsAdapter;
    }

    public void updateTransactionData(List<TransactionResponse.History> quizData) {
        isEmpty.set(quizData.isEmpty());
        winningsAdapter.updateData(quizData);
    }
}
