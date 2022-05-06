package com.thequizapp.quizalong.viewmodel;

import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class PlayedResultViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private HomePage.QuizesItem quizesItem;
    private TwistQuizPage.QuizItem twistQuizesItem;
    private String quizType = "";

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public HomePage.QuizesItem getQuizesItem() {
        return quizesItem;
    }

    public void setQuizesItem(HomePage.QuizesItem quizesItem) {
        this.quizesItem = quizesItem;
    }

    public TwistQuizPage.QuizItem getTwistQuizesItem() {
        return twistQuizesItem;
    }

    public void setTwistQuizesItem(TwistQuizPage.QuizItem twistQuizesItem) {
        this.twistQuizesItem = twistQuizesItem;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
}
