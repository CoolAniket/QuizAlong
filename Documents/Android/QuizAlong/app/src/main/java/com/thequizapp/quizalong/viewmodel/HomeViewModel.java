package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.HomeCategoriesAdapter;
import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.adapter.QuizesAdapter;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<CurrentUser> user = new MutableLiveData<>();
    private HomeCategoriesAdapter homeCategoriesAdapter = new HomeCategoriesAdapter();
    private QuizesAdapter quizesAdapter = new QuizesAdapter();
    private PastQuizesAdapter pastQuizesAdapter = new PastQuizesAdapter();
    private CompositeDisposable disposable = new CompositeDisposable();

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<CurrentUser> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<CurrentUser> user) {
        this.user = user;
    }

    public HomeCategoriesAdapter getHomeCategoriesAdapter() {
        return homeCategoriesAdapter;
    }

    public void setHomeCategoriesAdapter(HomeCategoriesAdapter homeCategoriesAdapter) {
        this.homeCategoriesAdapter = homeCategoriesAdapter;
    }

    public QuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void setQuizesAdapter(QuizesAdapter quizesAdapter) {
        this.quizesAdapter = quizesAdapter;
    }

    public void setPastQuizesAdapter(PastQuizesAdapter pastQuizesAdapter) {
        this.pastQuizesAdapter = pastQuizesAdapter;
    }

    public PastQuizesAdapter getPastQuizesAdapter() {
        return pastQuizesAdapter;
    }

    public void getHomeData() {
        disposable.add(Global.initRetrofit().getHomePage(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((homePage, throwable) -> {
                    if (homePage != null) {
                        if (homePage.getCategories() != null && !homePage.getCategories().isEmpty()) {
                            homeCategoriesAdapter.updateData(homePage.getCategories());
                        }
                        if (homePage.getQuizes() != null && !homePage.getQuizes().isEmpty()) {
                            quizesAdapter.updateData(homePage.getQuizes());
                        }
                    } else if (throwable != null) {
//
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
