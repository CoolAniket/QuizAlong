package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.HomeCategoriesAdapter;
import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.adapter.QuizesAdapter;
import com.thequizapp.quizalong.adapter.TwistQuizesAdapter;
import com.thequizapp.quizalong.adapter.UpcomingQuizesAdapter;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<CurrentUser> user = new MutableLiveData<>();
    //private HomeCategoriesAdapter homeCategoriesAdapter = new HomeCategoriesAdapter();
    private TwistQuizesAdapter twistQuizesAdapter = new TwistQuizesAdapter();
    private UpcomingQuizesAdapter upcomingQuizesAdapter = new UpcomingQuizesAdapter();
    //private QuizesAdapter quizesAdapter = new QuizesAdapter();
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

    /*public HomeCategoriesAdapter getHomeCategoriesAdapter() {
        return homeCategoriesAdapter;
    }

    public void setHomeCategoriesAdapter(HomeCategoriesAdapter homeCategoriesAdapter) {
        this.homeCategoriesAdapter = homeCategoriesAdapter;
    }*/

    public TwistQuizesAdapter getTwistQuizesAdapter() {
        return twistQuizesAdapter;
    }

    public void setTwistQuizesAdapter(TwistQuizesAdapter twistQuizesAdapter) {
        this.twistQuizesAdapter = twistQuizesAdapter;
    }

    public UpcomingQuizesAdapter getUpcomingQuizesAdapter() {
        return upcomingQuizesAdapter;
    }

    public void setUpcomingQuizesAdapter(UpcomingQuizesAdapter upcomingQuizesAdapter) {
        this.upcomingQuizesAdapter = upcomingQuizesAdapter;
    }

    /*public QuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void setQuizesAdapter(QuizesAdapter quizesAdapter) {
        this.quizesAdapter = quizesAdapter;
    }*/

    public void setPastQuizesAdapter(PastQuizesAdapter pastQuizesAdapter) {
        this.pastQuizesAdapter = pastQuizesAdapter;
    }

    public PastQuizesAdapter getPastQuizesAdapter() {
        return pastQuizesAdapter;
    }

    public void getHomeData() {
        /*disposable.add(Global.initRetrofit().getTwistQuizPage(BuildConfig.APIKEY)
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
                }));*/
        disposable.add(Global.initRetrofit().getTwistQuizPage(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((twistQuiz, throwable) -> {
                    Log.e("twistQuiz ",""+throwable);
                    if (twistQuiz != null) {

                        if (twistQuiz.getQuizes() != null && !twistQuiz.getQuizes().isEmpty()) {
                            twistQuizesAdapter.updateData(twistQuiz.getQuizes());
                        }
                    } else if (throwable != null) {
                        //
                    }
                }));

        disposable.add(Global.initRetrofit().getUpcomingQuizPage(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((upcomingQuiz, throwable) -> {
                    Log.e("upcomingQuiz",""+throwable);
                    if (upcomingQuiz != null) {

                        if (upcomingQuiz.getQuizes() != null && !upcomingQuiz.getQuizes().isEmpty()) {
                            upcomingQuizesAdapter.updateData(upcomingQuiz.getQuizes());
                        }
                    } else if (throwable != null) {
                        //
                    }
                }));
        disposable.add(Global.initRetrofit().getPastQuizPage(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((pastQuiz, throwable) -> {
                    Log.e("pastQuiz ",""+throwable);
                    if (pastQuiz != null) {

                        if (pastQuiz.getQuizes() != null && !pastQuiz.getQuizes().isEmpty()) {
                            pastQuizesAdapter.updateData(pastQuiz.getQuizes());
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
