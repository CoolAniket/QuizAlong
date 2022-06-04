package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.YearAdapter;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.utils.Global;

import java.util.HashMap;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<String> toast = new MutableLiveData<>();
    private YearAdapter categoriesAdapter = new YearAdapter();

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public YearAdapter getCategoriesAdapter() {
        return categoriesAdapter;
    }




    public void getHomeData(int courseId, String userId) {
        disposable.add(Global.initRetrofit().getAllCategories(BuildConfig.APIKEY, courseId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((categories, throwable) -> {
                    if (categories != null && categories.getYear() != null) {
                        categoriesAdapter.setYearList(categories.getYear());
                    }
                }));
    }
    public void setFavouriteCheck() {
        categoriesAdapter.setOnFavouriteCheck((categoriesItem, checked) -> {
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(Const.CATEGORY_ID, categoriesItem.getKey());
            hashMap.put(Const.STATUS, checked ? 1 : 0);
            hashMap.put(Const.USER_ID, Integer.valueOf(Global.userId.get()));

            disposable.add(Global.initRetrofit().saveUserCategory(BuildConfig.APIKEY, hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable1 -> isLoading.set(true))
                    .doOnTerminate(() -> isLoading.set(false))
                    .subscribe((restResponse, throwable) -> {
//                      toast.setValue("Failed to update user category. please try again later…!");
                        toast.setValue(restResponse.getMessage());
                        categoriesAdapter.updateCategory(categoriesItem, checked, restResponse.isStatus());

                    }));
        });
    }
}
