package com.thequizapp.quizalong.viewmodel;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.questions.Questions;
import com.thequizapp.quizalong.utils.Global;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QuizViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private HomePage.QuizesItem quizesItem;
    private MutableLiveData<Questions.QuestionsItem> currentQuestions = new MutableLiveData<>();
    private ObservableInt trueAnswerPosition = new ObservableInt(-1);
    private ObservableInt wrongAnswerPosition = new ObservableInt(-1);
    private ObservableInt totalScore = new ObservableInt(00);
    private ObservableInt totalQuestions = new ObservableInt(00);
    private ObservableInt firstAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt secondAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt thirdAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt fourthAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt rapidFireDuration = new ObservableInt(0);
    private List<String> answerList = new ArrayList<>();
    private List<Questions.QuestionsItem> questionsList = new ArrayList<>();
    private MutableLiveData<Boolean> isAnswer = new MutableLiveData<>();
    private ObservableInt currentPosition = new ObservableInt(0);
    private ObservableBoolean isComplete = new ObservableBoolean(false);
    private boolean isDoubleDip = false;
    private boolean isUseDoubleDeep = false;
    private boolean isUseFiftyFifty = false;
    private boolean isUseLifeLineInCurrentQue = false;

    public void getQuestionsByQuizId() {
        disposable.add(Global.initRetrofit().getQuestionsByQuizId(BuildConfig.APIKEY, String.valueOf(quizesItem.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((questions, throwable) -> {
                    if (questions != null) {
                        questionsList = questions.getQuestionsItems();
                        totalQuestions.set(questions.getQuestionsItems().size());
                        currentQuestions.setValue(questions.getQuestionsItems().get(currentPosition.get()));
                        currentPosition.set(currentPosition.get() + 1);
                    } else if (throwable != null) {
//
                    }
                }));
    }

    public void addPointsToWallet() {
        disposable.add(Global.initRetrofit().addPointsToWallet(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(totalScore.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) -> {
                    //
                }));
        disposable.add(Global.initRetrofit().increasePlay(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(quizesItem.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) -> {
                    //
                }));
    }

    public void onAnswerClick(int selectAnswerNum) {
        if (currentQuestions.getValue() != null) {
            rapidFireDuration.set(0);
            Questions.QuestionsItem questionsItem = currentQuestions.getValue();
            String answer = answerList.get(selectAnswerNum);
            if (answer.equalsIgnoreCase(questionsItem.getTrueAns())) {
                trueAnswerPosition.set(selectAnswerNum);
                wrongAnswerPosition.set(-1);
                isAnswer.setValue(answer.equalsIgnoreCase(questionsItem.getTrueAns()));
                isDoubleDip = false;
            } else {
                wrongAnswerPosition.set(selectAnswerNum);
                if (!isDoubleDip) {
                    for (int i = 0; i < answerList.size(); i++) {
                        String ans = answerList.get(i);
                        if (ans.equals(questionsItem.getTrueAns())) {
                            trueAnswerPosition.set(i);
                            break;
                        }
                    }
                    isAnswer.setValue(answer.equalsIgnoreCase(questionsItem.getTrueAns()));
                } else {
                    isDoubleDip = false;
                }
            }

        }

    }

    public void hide2WrongAnswer() {
        if (currentQuestions.getValue() != null) {
            Questions.QuestionsItem questionsItem = currentQuestions.getValue();
            int count = 0;
            for (int i = 0; i < answerList.size(); i++) {
                String ans = answerList.get(i);
                if (!ans.equals(questionsItem.getTrueAns())) {
                    if (i == 0) {
                        firstAnswerVisibility.set(View.VISIBLE);
                    } else if (i == 1) {
                        secondAnswerVisibility.set(View.VISIBLE);
                    } else if (i == 2) {
                        thirdAnswerVisibility.set(View.VISIBLE);
                    } else {
                        fourthAnswerVisibility.set(View.VISIBLE);
                    }
                    count++;
                    if (count == 2)
                        break;

                }
            }
        }
    }

    public void showAllAnswers() {
        firstAnswerVisibility.set(View.GONE);
        secondAnswerVisibility.set(View.GONE);
        thirdAnswerVisibility.set(View.GONE);
        fourthAnswerVisibility.set(View.GONE);
    }

    public void timesUp() {
        if (currentQuestions.getValue() != null) {
            rapidFireDuration.set(0);
            Questions.QuestionsItem questionsItem = currentQuestions.getValue();
            for (int i = 0; i < answerList.size(); i++) {
                String ans = answerList.get(i);
                if (ans.equals(questionsItem.getTrueAns())) {
                    trueAnswerPosition.set(i);
                    break;
                }
            }
            isAnswer.setValue(false);
        }
    }

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

    public MutableLiveData<Questions.QuestionsItem> getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(MutableLiveData<Questions.QuestionsItem> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    public ObservableInt getTrueAnswerPosition() {
        return trueAnswerPosition;
    }

    public void setTrueAnswerPosition(ObservableInt trueAnswerPosition) {
        this.trueAnswerPosition = trueAnswerPosition;
    }

    public ObservableInt getWrongAnswerPosition() {
        return wrongAnswerPosition;
    }

    public void setWrongAnswerPosition(ObservableInt wrongAnswerPosition) {
        this.wrongAnswerPosition = wrongAnswerPosition;
    }

    public ObservableInt getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(ObservableInt totalScore) {
        this.totalScore = totalScore;
    }

    public ObservableInt getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(ObservableInt totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public ObservableInt getFirstAnswerVisibility() {
        return firstAnswerVisibility;
    }

    public void setFirstAnswerVisibility(ObservableInt firstAnswerVisibility) {
        this.firstAnswerVisibility = firstAnswerVisibility;
    }

    public ObservableInt getSecondAnswerVisibility() {
        return secondAnswerVisibility;
    }

    public void setSecondAnswerVisibility(ObservableInt secondAnswerVisibility) {
        this.secondAnswerVisibility = secondAnswerVisibility;
    }

    public ObservableInt getThirdAnswerVisibility() {
        return thirdAnswerVisibility;
    }

    public void setThirdAnswerVisibility(ObservableInt thirdAnswerVisibility) {
        this.thirdAnswerVisibility = thirdAnswerVisibility;
    }

    public ObservableInt getFourthAnswerVisibility() {
        return fourthAnswerVisibility;
    }

    public void setFourthAnswerVisibility(ObservableInt fourthAnswerVisibility) {
        this.fourthAnswerVisibility = fourthAnswerVisibility;
    }

    public ObservableInt getRapidFireDuration() {
        return rapidFireDuration;
    }

    public void setRapidFireDuration(ObservableInt rapidFireDuration) {
        this.rapidFireDuration = rapidFireDuration;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<Questions.QuestionsItem> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions.QuestionsItem> questionsList) {
        this.questionsList = questionsList;
    }

    public MutableLiveData<Boolean> getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(MutableLiveData<Boolean> isAnswer) {
        this.isAnswer = isAnswer;
    }

    public ObservableInt getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(ObservableInt currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ObservableBoolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(ObservableBoolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isDoubleDip() {
        return isDoubleDip;
    }

    public void setDoubleDip(boolean doubleDip) {
        isDoubleDip = doubleDip;
    }

    public boolean isUseDoubleDeep() {
        return isUseDoubleDeep;
    }

    public void setUseDoubleDeep(boolean useDoubleDeep) {
        isUseDoubleDeep = useDoubleDeep;
    }

    public boolean isUseFiftyFifty() {
        return isUseFiftyFifty;
    }

    public void setUseFiftyFifty(boolean useFiftyFifty) {
        isUseFiftyFifty = useFiftyFifty;
    }

    public boolean isUseLifeLineInCurrentQue() {
        return isUseLifeLineInCurrentQue;
    }

    public void setUseLifeLineInCurrentQue(boolean useLifeLineInCurrentQue) {
        isUseLifeLineInCurrentQue = useLifeLineInCurrentQue;
    }
}
