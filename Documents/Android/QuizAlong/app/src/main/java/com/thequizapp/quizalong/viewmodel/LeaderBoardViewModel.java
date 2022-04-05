package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.LeaderBoardAdapter;
import com.thequizapp.quizalong.model.leaderboard.LeaderBoard;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

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
    private MutableLiveData<LeaderBoard.QuizesItem> myUser = new MutableLiveData<>();
    private MutableLiveData<String> myUserPosition = new MutableLiveData<>();
    private CurrentUser user;

    public LeaderBoardAdapter getLeaderBoardAdapter() {
        return leaderBoardAdapter;
    }

    public void setLeaderBoardAdapter(LeaderBoardAdapter leaderBoardAdapter) {
        this.leaderBoardAdapter = leaderBoardAdapter;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
        LeaderBoard.QuizesItem quizesItem = new LeaderBoard.QuizesItem();
        quizesItem.setFullName(user.getUser().getFullName());
        quizesItem.setIdentity(user.getUser().getIdentity());
//        quizesItem.setImage(user.getUser().getImage().toString());
//        quizesItem.setTotalPoints(user.getUser().getTotalPoints());
        myUser.postValue(quizesItem);
        myUserPosition.postValue("0");
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

    public MutableLiveData<LeaderBoard.QuizesItem> getMyUser() {
        return myUser;
    }

    public void setMyUser(MutableLiveData<LeaderBoard.QuizesItem> myUser) {
        this.myUser = myUser;
    }

    public MutableLiveData<String> getMyUserPosition() {
        return myUserPosition;
    }

    public void setMyUserPosition(MutableLiveData<String> myUserPosition) {
        this.myUserPosition = myUserPosition;
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
                            LeaderBoard.QuizesItem quizesItem = leaderBoard.getQuizes().get(i);
                            myUserPosition.setValue(""+i);
                            if (quizesItem.getIdentity().equals(user.getUser().getIdentity())) {
                                myUser.setValue(quizesItem);
                            }
                            newList.add(quizesItem);
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
