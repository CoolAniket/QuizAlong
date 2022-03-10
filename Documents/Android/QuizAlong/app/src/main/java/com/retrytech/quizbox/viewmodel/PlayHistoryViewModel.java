package com.retrytech.quizbox.viewmodel;

import androidx.lifecycle.ViewModel;

import com.retrytech.quizbox.adapter.PlayHistoryAdapter;

public class PlayHistoryViewModel extends ViewModel {
    private PlayHistoryAdapter playHistoryAdapter = new PlayHistoryAdapter();

    public PlayHistoryAdapter getPlayHistoryAdapter() {
        return playHistoryAdapter;
    }

    public void setPlayHistoryAdapter(PlayHistoryAdapter playHistoryAdapter) {
        this.playHistoryAdapter = playHistoryAdapter;
    }
}
