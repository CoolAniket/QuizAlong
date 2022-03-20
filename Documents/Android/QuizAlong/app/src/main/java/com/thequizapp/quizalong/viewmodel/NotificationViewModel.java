package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.adapter.NotificationAdapter;
import com.thequizapp.quizalong.utils.Global;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private NotificationAdapter notificationAdapter = new NotificationAdapter();
    private boolean isLoading = false;
    private ObservableBoolean observeIsLoading = new ObservableBoolean(true);
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private boolean isLast = false;
    private int start = 0;
    private int count = 2;

    public NotificationAdapter getNotificationAdapter() {
        return notificationAdapter;
    }

    public void setNotificationAdapter(NotificationAdapter notificationAdapter) {
        this.notificationAdapter = notificationAdapter;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public ObservableBoolean getObserveIsLoading() {
        return observeIsLoading;
    }

    public void setObserveIsLoading(ObservableBoolean observeIsLoading) {
        this.observeIsLoading = observeIsLoading;
    }

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(ObservableBoolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public void getNotifications() {
        disposable.add(Global.initRetrofit().getNotifications(BuildConfig.APIKEY, String.valueOf(start), String.valueOf(count))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading = true)
                .doOnTerminate(() -> isLoading = false)
                .subscribe((notifications, throwable) -> {
                    if (notifications != null) {
                        observeIsLoading.set(false);
                        if (notifications.getNotificationsItems().isEmpty()) {
                            isLast = true;
                            return;
                        }
                        if (notificationAdapter.getNotifications().isEmpty()) {
                            notificationAdapter.updateData(notifications.getNotificationsItems());
                        } else {
                            notificationAdapter.addNewData(notifications.getNotificationsItems());
                        }
                        isEmpty.set(notificationAdapter.getNotifications().isEmpty());
                        start += 2;
                    } else if (throwable != null) {
//
                    }
                }));
    }
}
