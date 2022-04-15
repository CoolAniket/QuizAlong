package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.adapter.ShowQuestionAnsAdapter;
import com.thequizapp.quizalong.adapter.ShowResultsAdapter;
import com.thequizapp.quizalong.model.results.ShowResultsRequest;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.view.results.ShowQuizAnswersActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ShowQuizResultsViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<ShowResultsRequest> onSuccess = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ShowResultsAdapter showResultsAdapter = new ShowResultsAdapter();
    private ShowQuestionAnsAdapter showQuestionAnsAdapter = new ShowQuestionAnsAdapter();
    private MutableLiveData<String> paginationVal = new MutableLiveData<>();
    public MutableLiveData<ShowResultsRequest> getOnSuccess() {
        return onSuccess;
    }

    private ShowResultsRequest showResultsRequest;

    public MutableLiveData<String> getPaginationVal() {
        return paginationVal;
    }

    public void setPaginationVal(MutableLiveData<String> paginationVal) {
        this.paginationVal = paginationVal;
    }

    public ShowResultsRequest getShowResultsRequest() {
        return showResultsRequest;
    }

    public void setUser(ShowResultsRequest showResultsRequest) {
        this.showResultsRequest = showResultsRequest;
    }

    public void setOnSuccess(MutableLiveData<ShowResultsRequest> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public void setShowQuestionAnsAdapter(ShowQuestionAnsAdapter showQuestionAnsAdapter) {
        this.showQuestionAnsAdapter = showQuestionAnsAdapter;
    }

    public ShowQuestionAnsAdapter getShowQuestionAnsAdapter() {
        return showQuestionAnsAdapter;
    }

    public void getQuizResults(String quizId) {
        Log.e("ShowResult... "," "+quizId+" "+Global.userId.get());
        disposable.add(Global.initRetrofit().getQuizAnsLive(BuildConfig.APIKEY, quizId, Global.userId.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((ShowResult, throwable) -> {
                    Log.e("ShowResult...",""+ShowResult+" "+throwable);
                    if (ShowResult != null) {
                        onSuccess.setValue(ShowResult);
                        //showResultsAdapter.updateData(ShowResult.getQuestions(),ShowResult.getUserAnswers());
                        showQuestionAnsAdapter.updateData(ShowResult);
                        /*String str = "1"+"/"+ShowResult.getQuestions().size();
                        getPaginationVal().setValue(str);*/
                        this.setUser(ShowResult);
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
