package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.QuizesAdapter;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.model.rest.RestResponse;
import com.thequizapp.quizalong.utils.Global;

import java.util.HashMap;
import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UpcomingQuizViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    public final MutableLiveData<RestResponse> onSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private QuizesAdapter quizesAdapter = new QuizesAdapter(true);

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public QuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void updateQuizData(List<QuizItem> quizData) {
        isEmpty.set(quizData.isEmpty());
        quizesAdapter.updateData(quizData);
    }

    public void subscribeForFreeApi(QuizItem quizesItem) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Const.QUIZ_ID_NEW, String.valueOf(quizesItem.getQuizId()));
        hashMap.put(Const.AMOUNT, String.valueOf(0));
        hashMap.put(Const.USER_ID, Global.userId.get());
        hashMap.put(Const.STATUS, "1");

        addpaymentdata(hashMap);
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
                        if (orderResponse.isStatus()) {
                            onSuccess.setValue(orderResponse);
                        } else {
                            toast.setValue(orderResponse.getMessage());
                        }
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
