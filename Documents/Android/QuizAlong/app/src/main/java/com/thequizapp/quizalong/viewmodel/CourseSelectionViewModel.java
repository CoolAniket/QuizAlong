package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.CourseSelectionAdapter;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.utils.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CourseSelectionViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private final CourseSelectionAdapter categoriesAdapter = new CourseSelectionAdapter();
    List<CategoriesResponse.Category> categories;

    public CourseSelectionAdapter getCategoriesAdapter() {
        return categoriesAdapter;
    }
    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }


    public void getCourseData(int courseId, int userId) {
        disposable.add(Global.initRetrofit().getAllCategories(BuildConfig.APIKEY, courseId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((categoriesResponse, throwable) -> {
                    if (categoriesResponse != null && categoriesResponse.getYear() != null) {
                        categories = new ArrayList<>();
                        for (CategoriesResponse.Year year: categoriesResponse.getYear()){
                            categories.addAll(year.getCategories());
                        }
                        categoriesAdapter.updateData(categories);
                    }
                }));
    }

    public void saveCategories(){
        if (categoriesAdapter.getSelectedCategories().size() < 2) {
            toast.setValue("Select min 2 categories");
            return;
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        Integer[] selectedCats = categoriesAdapter.getSelectedCategories().toArray(new Integer[0]);
        for (int i = 0; i < selectedCats.length; i++) {
            hashMap.put("category_id["+i+"]", selectedCats[i]);
        }
        hashMap.put(Const.USER_ID, Integer.parseInt(Global.userId.get()));
//        hashMap.put(Const.USER_ID, 35);

        disposable.add(Global.initRetrofit().saveUserCategoriesBulk(BuildConfig.APIKEY, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((categoriesResponse, throwable) -> {
                    if (categoriesResponse != null && categoriesResponse.isStatus()) {
                        isSuccess.setValue(true);
                    }
                }));
    }
}
