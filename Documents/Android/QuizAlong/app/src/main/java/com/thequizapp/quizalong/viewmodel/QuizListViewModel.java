package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.QuizesAdapter;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QuizListViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private CategoriesResponse.Category categoriesItem;
    private QuizesAdapter quizesAdapter = new QuizesAdapter();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private ObservableBoolean isEmpty = new ObservableBoolean(false);

    public CategoriesResponse.Category getCategoriesItem() {
        return categoriesItem;
    }

    public void setCategoriesItem(CategoriesResponse.Category categoriesItem) {
        this.categoriesItem = categoriesItem;
    }

    public QuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void setQuizesAdapter(QuizesAdapter quizesAdapter) {
        this.quizesAdapter = quizesAdapter;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(ObservableBoolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void getQuizesByCatId() {
        disposable.add(Global.initRetrofit().getQuizByCatId(BuildConfig.APIKEY, String.valueOf(categoriesItem.getKey()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((quizByCatId, throwable) -> {
                    if (quizByCatId != null && quizByCatId.getQuizes() != null) {
                        isEmpty.set(quizByCatId.getQuizes().isEmpty());
                        quizesAdapter.updateData(quizByCatId.getQuizes());
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
