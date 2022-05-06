package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.adapter.MyPastQuizesAdapter;
import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.model.home.TwistQuizPage;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class MyPastQuizViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private MyPastQuizesAdapter quizesAdapter = new MyPastQuizesAdapter();


    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setQuizesAdapter(MyPastQuizesAdapter quizesAdapter) {
        this.quizesAdapter = quizesAdapter;
    }

    public MyPastQuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void updateQuizData(List<TwistQuizPage.QuizItem> quizData) {
        isEmpty.set(quizData.isEmpty());
        quizesAdapter.updateData(quizData);
    }
}
