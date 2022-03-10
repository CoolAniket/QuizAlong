package com.retrytech.quizbox.viewmodel;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryRedeemRequestViewModel extends ViewModel {
    private MutableLiveData<Integer> mutableSelectPosition = new MutableLiveData<>();
    private ObservableInt selectPosition = new ObservableInt(0);

    public MutableLiveData<Integer> getMutableSelectPosition() {
        return mutableSelectPosition;
    }

    public void setMutableSelectPosition(MutableLiveData<Integer> mutableSelectPosition) {
        this.mutableSelectPosition = mutableSelectPosition;
    }

    public ObservableInt getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(ObservableInt selectPosition) {
        this.selectPosition = selectPosition;
    }

    public void onClickItem(int position) {
        selectPosition.set(position);
        mutableSelectPosition.setValue(position);
    }

}
