package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.adapter.TwistQuizesAdapter;
import com.thequizapp.quizalong.adapter.UpcomingQuizesAdapter;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.model.rest.RestResponse;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import java.util.Collections;
import java.util.HashMap;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    public final MutableLiveData<RestResponse> onSuccess = new MutableLiveData<>();
    public final MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<CurrentUser> user = new MutableLiveData<>();
    private TwistQuizesAdapter twistQuizesAdapter = new TwistQuizesAdapter();
    private UpcomingQuizesAdapter upcomingQuizesAdapter = new UpcomingQuizesAdapter();
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


    public void setPastQuizesAdapter(PastQuizesAdapter pastQuizesAdapter) {
        this.pastQuizesAdapter = pastQuizesAdapter;
    }

    public PastQuizesAdapter getPastQuizesAdapter() {
        return pastQuizesAdapter;
    }

    public void getHomeData() {
        disposable.add(Global.initRetrofit().getTwistQuizPage(BuildConfig.APIKEY,Integer.parseInt(Global.userId.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((twistQuiz, throwable) -> {
                    Log.e("twistQuiz ",""+throwable);
                    if (twistQuiz != null) {
                        twistQuizesAdapter.updateData(twistQuiz.getQuizes());
                    } else if (throwable != null) {
                        //
                    }
                }));

        disposable.add(Global.initRetrofit().getUpcomingQuizPage(BuildConfig.APIKEY,Integer.parseInt(Global.userId.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((upcomingQuiz, throwable) -> {
                    Log.e("upcomingQuiz",""+throwable);
                    if (upcomingQuiz != null) {
                        upcomingQuizesAdapter.updateData(upcomingQuiz.getQuizes(), false);
                    } else if (throwable != null) {
                        //
                    }
                }));
        disposable.add(Global.initRetrofit().getPastQuizPage(BuildConfig.APIKEY,Integer.parseInt(Global.userId.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((pastQuiz, throwable) -> {
                    Log.e("pastQuiz ",""+throwable);
                    if (pastQuiz != null) {
                        if (pastQuiz.getQuizes() != null && !pastQuiz.getQuizes().isEmpty()) {
                            Collections.sort(pastQuiz.getQuizes(), (obj1, obj2) -> {
                                // ## Ascending order
                                return obj1.getPlayed().toString().compareToIgnoreCase("1"); // To compare string values
                            });
                        }
                        pastQuizesAdapter.updateData(pastQuiz.getQuizes(), false);
                    } else if (throwable != null) {
                        //
                    }
                }));
    }

    public void showMore(int quizType) {
        if (quizType == 0) {
            upcomingQuizesAdapter.switchLoadFullList();
        } else {
            pastQuizesAdapter.switchLoadFullList();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
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
}
