package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.model.quiz.QuizByCatId;
import com.thequizapp.quizalong.utils.Global;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MyQuizViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<QuizByCatId> upcomingQuizes = new MutableLiveData<>();
    private MutableLiveData<Integer> mutableSelectedMenu = new MutableLiveData<>();
    private ObservableInt selectedMenu = new ObservableInt(0);




    public MutableLiveData<QuizByCatId> getUpcomingQuizes() {
        return upcomingQuizes;
    }


    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public void getMyQuizes() {
        disposable.add(Global.initRetrofit().getMyQuizes(BuildConfig.APIKEY, String.valueOf(Global.userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((quizByCatId, throwable) -> {
                    if (quizByCatId != null && quizByCatId.getPastQuizes() != null && quizByCatId.getUpcomingQuizes() != null) {
//                        isEmpty.set(quizByCatId.getPastQuizes().isEmpty());
                        upcomingQuizes.postValue(quizByCatId);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
