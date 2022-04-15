package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.adapter.QuizesAdapter;
import com.thequizapp.quizalong.model.home.HomePage;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class PastQuizViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isEmpty = new ObservableBoolean(false);
    private final ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<String> toast = new MutableLiveData<>();

    private QuizesAdapter quizesAdapter = new QuizesAdapter(true);


    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public QuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void updateQuizData(List<HomePage.QuizesItem> quizData) {
        isEmpty.set(quizData.isEmpty());
        quizesAdapter.updateData(quizData);
    }
}
