package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.CatCategoriesAdapter;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private CatCategoriesAdapter categoriesAdapter = new CatCategoriesAdapter();

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public CatCategoriesAdapter getCategoriesAdapter() {
        return categoriesAdapter;
    }


    public void getHomeData() {
        disposable.add(Global.initRetrofit().getAllCategories(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((categories, throwable) -> {
                    if (categories != null && categories.getCategoriesItemList() != null) {
                        categoriesAdapter.updateData(categories.getCategoriesItemList());
                    }
                }));
    }
}
