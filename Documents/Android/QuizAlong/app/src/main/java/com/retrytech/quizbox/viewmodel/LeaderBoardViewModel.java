package com.retrytech.quizbox.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.retrytech.quizbox.BuildConfig;
import com.retrytech.quizbox.adapter.LeaderBoardAdapter;
import com.retrytech.quizbox.model.leaderboard.LeaderBoard;
import com.retrytech.quizbox.utils.Global;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LeaderBoardViewModel extends ViewModel {
    private LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<LeaderBoard.QuizesItem> firstUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoard.QuizesItem> secondUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoard.QuizesItem> thirdUser = new MutableLiveData<>();

    public LeaderBoardAdapter getLeaderBoardAdapter() {
        return leaderBoardAdapter;
    }

    public void setLeaderBoardAdapter(LeaderBoardAdapter leaderBoardAdapter) {
        this.leaderBoardAdapter = leaderBoardAdapter;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<LeaderBoard.QuizesItem> getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(MutableLiveData<LeaderBoard.QuizesItem> firstUser) {
        this.firstUser = firstUser;
    }

    public MutableLiveData<LeaderBoard.QuizesItem> getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(MutableLiveData<LeaderBoard.QuizesItem> secondUser) {
        this.secondUser = secondUser;
    }

    public MutableLiveData<LeaderBoard.QuizesItem> getThirdUser() {
        return thirdUser;
    }

    public void setThirdUser(MutableLiveData<LeaderBoard.QuizesItem> thirdUser) {
        this.thirdUser = thirdUser;
    }

    public void getLeaderBoard() {
        disposable.add(Global.initRetrofit().getLeaderBoard(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((leaderBoard, throwable) -> {
                    if (leaderBoard != null) {
                        if (!leaderBoard.getQuizes().isEmpty()) {
                            firstUser.setValue(leaderBoard.getQuizes().get(0));
                        }
                        if (leaderBoard.getQuizes().size() > 1) {
                            secondUser.setValue(leaderBoard.getQuizes().get(1));
                        }
                        if (leaderBoard.getQuizes().size() > 2) {
                            thirdUser.setValue(leaderBoard.getQuizes().get(2));
                        }
                        List<LeaderBoard.QuizesItem> newList = new ArrayList<>();
                        for (int i = 3; i < leaderBoard.getQuizes().size(); i++) {
                            newList.add(leaderBoard.getQuizes().get(i));
                        }
                        leaderBoardAdapter.updateData(newList);
                    } else if (throwable != null) {
//
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
