package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.adapter.TransactionAdapter;
import com.thequizapp.quizalong.model.payment.TransactionResponse;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class PaymentHistoryViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private TransactionAdapter transactionAdapter = new TransactionAdapter();


    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setTransactionAdapter(TransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    public TransactionAdapter getTransactionAdapter() {
        return transactionAdapter;
    }

    public void updateTransactionData(List<TransactionResponse.History> quizData) {
        isEmpty.set(quizData.isEmpty());
        transactionAdapter.updateData(quizData);
    }
}
