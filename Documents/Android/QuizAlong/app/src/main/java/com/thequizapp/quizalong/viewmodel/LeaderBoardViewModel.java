package com.thequizapp.quizalong.viewmodel;

import android.os.Build;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.LeaderBoardAdapter;
import com.thequizapp.quizalong.model.leaderboard.LeaderBoardResponse;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LeaderBoardViewModel extends ViewModel {
    private LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<LeaderBoardResponse.LeaderboardUser> firstUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardUser> secondUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardUser> thirdUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardUser> myUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse> leaderBoardResult = new MutableLiveData<>();

    private MutableLiveData<Boolean> myUserPosition = new MutableLiveData<>();
    private CurrentUser user;
    private String quizId = "";
    private String quizType = "";
    private List<Integer> typeArray = new ArrayList<>();

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
        LeaderBoardResponse.LeaderboardUser leaderboardUser = new LeaderBoardResponse.LeaderboardUser();
        leaderboardUser.setFullName(user.getUser().getFullname());
        leaderboardUser.setUserIdentity(user.getUser().getIdentity());
//        quizesItem.setImage(user.getUser().getImage().toString());
//        quizesItem.setTotalPoints(user.getUser().getTotalPoints());
        myUser.postValue(leaderboardUser);
        myUserPosition.postValue(false);
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardUser> getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(MutableLiveData<LeaderBoardResponse.LeaderboardUser> firstUser) {
        this.firstUser = firstUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardUser> getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(MutableLiveData<LeaderBoardResponse.LeaderboardUser> secondUser) {
        this.secondUser = secondUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardUser> getThirdUser() {
        return thirdUser;
    }

    public void setThirdUser(MutableLiveData<LeaderBoardResponse.LeaderboardUser> thirdUser) {
        this.thirdUser = thirdUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardUser> getMyUser() {
        return myUser;
    }

    public void setMyUser(MutableLiveData<LeaderBoardResponse.LeaderboardUser> myUser) {
        this.myUser = myUser;
    }

    public MutableLiveData<Boolean> getMyUserPosition() {
        return myUserPosition;
    }

    public void setMyUserPosition(MutableLiveData<Boolean> myUserPosition) {
        this.myUserPosition = myUserPosition;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public MutableLiveData<LeaderBoardResponse> getLeaderBoardResult() {
        return leaderBoardResult;
    }

    public List<Integer> getTypeArray() {
        return typeArray;
    }

    public void getLeaderBoard() {
        if ("past".equals(quizType)) {
            disposable.add(Global.initRetrofit().getPastLeaderBoard(BuildConfig.APIKEY, quizId, Global.userId.get())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable1 -> isLoading.set(true))
                    .doOnTerminate(() -> isLoading.set(false))
                    .subscribe((leaderBoard, throwable) -> {
                        if (leaderBoard != null) {
                            this.leaderBoardResult.setValue(leaderBoard);
                        }

                    }));
        } else {
            disposable.add(Global.initRetrofit().getLiveLeaderBoard(BuildConfig.APIKEY, quizId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable1 -> isLoading.set(true))
                    .doOnTerminate(() -> isLoading.set(false))
                    .subscribe((leaderBoard, throwable) -> {
                        if (leaderBoard != null) {
                            this.leaderBoardResult.setValue(leaderBoard);
                        }

                    }));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void updateAdapter(int position) {
        if (leaderBoardResult.getValue() != null) {
            switch (position) {
                case 0:
                    updateAdapter(leaderBoardResult.getValue().getLeaderboardItem().getGroup1());
                    break;
                case 1:
                    updateAdapter(leaderBoardResult.getValue().getLeaderboardItem().getGroup2());
                    break;
                case 2:
                    updateAdapter(leaderBoardResult.getValue().getLeaderboardItem().getGroup3());
                    break;
            }
        }
    }

    private void updateAdapter(List<LeaderBoardResponse.LeaderboardUser> users) {
        LeaderBoardResponse.LeaderboardUser nullUser = new LeaderBoardResponse.LeaderboardUser();
        if (users.isEmpty()) {
            firstUser.setValue(nullUser);
            secondUser.setValue(nullUser);
            thirdUser.setValue(nullUser);
        }
        else {
            firstUser.setValue(users.get(0));
            secondUser.setValue(users.size() > 1 ? users.get(1): nullUser);
            thirdUser.setValue(users.size() > 2 ? users.get(2) : nullUser);
        }
        for (int i = 3; i < users.size(); i++) {
            LeaderBoardResponse.LeaderboardUser leaderboardUser = users.get(i);
            if (leaderboardUser.getUserId() == user.getUser().getId()) {
                myUserPosition.setValue(true);
                myUser.setValue(leaderboardUser);
                break;
            }
        }
        if (users.size() > 1) {
            users.sort(Comparator.comparingInt(LeaderBoardResponse.LeaderboardUser::getRank));
        }
        leaderBoardAdapter.updateData(users);
    }
}
