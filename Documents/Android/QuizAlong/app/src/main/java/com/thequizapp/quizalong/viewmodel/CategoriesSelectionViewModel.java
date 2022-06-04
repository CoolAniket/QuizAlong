package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.CourseSelectionAdapter;
import com.thequizapp.quizalong.utils.Global;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesSelectionViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private CourseSelectionAdapter categoriesAdapter = new CourseSelectionAdapter();

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public CourseSelectionAdapter getCategoriesAdapter() {
        return categoriesAdapter;
    }


    public void getHomeData() {
        disposable.add(Global.initRetrofit().getAllCategories(BuildConfig.APIKEY, 0, "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((categories, throwable) -> {
                    if (categories != null && categories.getYear() != null) {
                        categoriesAdapter.updateData(categories.getYear().get(0).getCategories());
                    }
                }));
    }
}
