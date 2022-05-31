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
    private MutableLiveData<LeaderBoardResponse.LeaderboardItem> firstUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardItem> secondUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardItem> thirdUser = new MutableLiveData<>();
    private MutableLiveData<LeaderBoardResponse.LeaderboardItem> myUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> myUserPosition = new MutableLiveData<>();
    private CurrentUser user;
    private String quizId = "";
    private String quizType = "";

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
        LeaderBoardResponse.LeaderboardItem leaderboardItem = new LeaderBoardResponse.LeaderboardItem();
        leaderboardItem.setFullName(user.getUser().getFullname());
        leaderboardItem.setUserIdentity(user.getUser().getIdentity());
//        quizesItem.setImage(user.getUser().getImage().toString());
//        quizesItem.setTotalPoints(user.getUser().getTotalPoints());
        myUser.postValue(leaderboardItem);
        myUserPosition.postValue(false);
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardItem> getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(MutableLiveData<LeaderBoardResponse.LeaderboardItem> firstUser) {
        this.firstUser = firstUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardItem> getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(MutableLiveData<LeaderBoardResponse.LeaderboardItem> secondUser) {
        this.secondUser = secondUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardItem> getThirdUser() {
        return thirdUser;
    }

    public void setThirdUser(MutableLiveData<LeaderBoardResponse.LeaderboardItem> thirdUser) {
        this.thirdUser = thirdUser;
    }

    public MutableLiveData<LeaderBoardResponse.LeaderboardItem> getMyUser() {
        return myUser;
    }

    public void setMyUser(MutableLiveData<LeaderBoardResponse.LeaderboardItem> myUser) {
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
                            if (!leaderBoard.getLeaderboardList().isEmpty()) {
                                firstUser.setValue(leaderBoard.getLeaderboardList().get(0));
                            }
                            if (leaderBoard.getLeaderboardList().size() > 1) {
                                secondUser.setValue(leaderBoard.getLeaderboardList().get(1));
                            }
                            if (leaderBoard.getLeaderboardList().size() > 2) {
                                thirdUser.setValue(leaderBoard.getLeaderboardList().get(2));
                            }
//                            List<LeaderBoardResponse.LeaderboardItem> newList = new ArrayList<>();
                            for (int i = 3; i < leaderBoard.getLeaderboardList().size(); i++) {
                                LeaderBoardResponse.LeaderboardItem leaderboardItem = leaderBoard.getLeaderboardList().get(i);
                                if (leaderboardItem.getUserId() == user.getUser().getId()) {
                                    myUserPosition.setValue(true);
                                    myUser.setValue(leaderboardItem);
                                    break;
                                }
//                                newList.add(leaderboardItem);
                            }
                            if (leaderBoard.getLeaderboardList().size() > 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Collections.sort(leaderBoard.getLeaderboardList(), Comparator.comparingInt(LeaderBoardResponse.LeaderboardItem::getId));
                                }
                            }
                            leaderBoardAdapter.updateData(leaderBoard.getLeaderboardList());
                        } else if (throwable != null) {
//
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
                            if (!leaderBoard.getLeaderboardList().isEmpty()) {
                                firstUser.setValue(leaderBoard.getLeaderboardList().get(0));
                            }
                            if (leaderBoard.getLeaderboardList().size() > 1) {
                                secondUser.setValue(leaderBoard.getLeaderboardList().get(1));
                            }
                            if (leaderBoard.getLeaderboardList().size() > 2) {
                                thirdUser.setValue(leaderBoard.getLeaderboardList().get(2));
                            }

                            for (int i = 3; i < leaderBoard.getLeaderboardList().size(); i++) {
                                LeaderBoardResponse.LeaderboardItem leaderboardItem = leaderBoard.getLeaderboardList().get(i);
                                if (leaderboardItem.getUserId() == user.getUser().getId()) {
                                    myUserPosition.setValue(true);
                                    myUser.setValue(leaderboardItem);
                                    break;
                                }
//                                newList.add(leaderboardItem);
                            }
                            if (leaderBoard.getLeaderboardList().size() > 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Collections.sort(leaderBoard.getLeaderboardList(), Comparator.comparingInt(LeaderBoardResponse.LeaderboardItem::getId));
                                }
                            }
                            leaderBoardAdapter.updateData(leaderBoard.getLeaderboardList());
                        } else if (throwable != null) {
//
                        }
                    }));
        }


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
