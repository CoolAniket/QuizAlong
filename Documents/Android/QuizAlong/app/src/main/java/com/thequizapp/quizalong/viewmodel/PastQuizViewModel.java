package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.adapter.PastQuizesAdapter;
import com.thequizapp.quizalong.model.quiz.QuizItem;

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

    private PastQuizesAdapter quizesAdapter = new PastQuizesAdapter();


    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setQuizesAdapter(PastQuizesAdapter quizesAdapter) {
        this.quizesAdapter = quizesAdapter;
    }

    public PastQuizesAdapter getQuizesAdapter() {
        return quizesAdapter;
    }

    public void updateQuizData(List<QuizItem> quizData) {
        isEmpty.set(quizData.isEmpty());
        quizesAdapter.updateData(quizData, true);
    }
}
