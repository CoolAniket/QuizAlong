package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.model.user.CurrentUser;

public class MainViewModel extends ViewModel {
    public boolean isBack = false;
    private MutableLiveData<Integer> mutableSelectedMenu = new MutableLiveData<>();
    private ObservableInt selectedMenu = new ObservableInt(0);
    private CurrentUser user;

    public MutableLiveData<Integer> getMutableSelectedMenu() {
        return mutableSelectedMenu;
    }

    public void setMutableSelectedMenu(MutableLiveData<Integer> mutableSelectedMenu) {
        this.mutableSelectedMenu = mutableSelectedMenu;
    }

    public ObservableInt getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(ObservableInt selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }

    public void onClickMenu(int selectedPosition) {
        selectedMenu.set(selectedPosition);
        mutableSelectedMenu.setValue(selectedPosition);
    }
}
