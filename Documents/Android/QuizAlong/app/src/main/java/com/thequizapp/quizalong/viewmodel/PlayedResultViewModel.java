package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class PlayedResultViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private final MutableLiveData<QuizItem> twistQuizesItem = new MutableLiveData<>();

    private String quizType = "";

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<QuizItem> getTwistQuizesItem() {
        return twistQuizesItem;
    }

    public void setTwistQuizesItem(QuizItem twistQuizesItem) {
        this.twistQuizesItem.setValue(twistQuizesItem);
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
}
