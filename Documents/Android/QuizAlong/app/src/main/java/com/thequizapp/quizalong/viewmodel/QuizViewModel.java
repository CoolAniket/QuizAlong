package com.thequizapp.quizalong.viewmodel;

import android.util.Log;
import android.view.View;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.model.questions.NewQuestions;
import com.thequizapp.quizalong.model.quiz.AddDataLiveResponse;
import com.thequizapp.quizalong.model.quiz.LobbyMessageResponse;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.utils.DateUtils;
import com.thequizapp.quizalong.utils.Global;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QuizViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(true);
    private QuizItem quizesItem;
    private QuizItem twistQuizesItem;
    private boolean isDoubleDip = false;
    private boolean isUseDoubleDeep = false;
    private boolean isUseFiftyFifty = false;
    private boolean isUseSkip = false;
    private boolean isUseOnce = false;
    private boolean isUseLifeLineInCurrentQue = false;

    private String quizType = "";
    private List<String> answerList = new ArrayList<>();
    private List<NewQuestions.Question> questionsList = new ArrayList<>();
    private HashMap<String, String> hashMap = new HashMap<>();
    private ObservableInt timeRemaining = new ObservableInt(0);
    private ObservableInt lobbyTimeRemaining = new ObservableInt(0);
    private ObservableInt trueAnswerPosition = new ObservableInt(-1);
    private ObservableInt wrongAnswerPosition = new ObservableInt(-1);
    private ObservableInt totalScore = new ObservableInt(00);
    private ObservableInt totalQuestions = new ObservableInt(00);
    //private ObservableInt skipLifelines = new ObservableInt(0);
    private ObservableInt firstAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt secondAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt thirdAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt fourthAnswerVisibility = new ObservableInt(View.GONE);
    private ObservableInt rapidFireDuration = new ObservableInt(0);
    private ObservableInt currentPosition = new ObservableInt(0);
    private ObservableBoolean isComplete = new ObservableBoolean(false);
    private ObservableBoolean isInfo = new ObservableBoolean(false);
    private ObservableBoolean isLobby = new ObservableBoolean(false);
    private MutableLiveData<NewQuestions.Question> currentQuestions = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAnswer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSkipAnswer = new MutableLiveData<>();
    private MutableLiveData<String> answerVal = new MutableLiveData<>();
    private MutableLiveData<String> skipLifelines = new MutableLiveData<>();
    private MutableLiveData<String> subscribedAmount = new MutableLiveData<>();
    private final MutableLiveData<Long> serverTime = new MutableLiveData<>();
    private MutableLiveData<AddDataLiveResponse> onSuccess = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<String> lobbyTime = new MutableLiveData<>();

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public ObservableInt getTimeRemaining() {
        return timeRemaining;
    }

    public final MutableLiveData<LobbyMessageResponse> onLobbySuccess = new MutableLiveData<>();

    public void setTimeRemaining(ObservableInt timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public MutableLiveData<String> getLobbyTime() {
        return lobbyTime;
    }

    public void setLobbyTime(MutableLiveData<String> lobbyTime) {
        this.lobbyTime = lobbyTime;
    }

    public MutableLiveData<Long> getServerTime() {
        return serverTime;
    }

    public ObservableInt getLobbyTimeRemaining() {
        return lobbyTimeRemaining;
    }

    public void setLobbyTimeRemaining(ObservableInt lobbyTimeRemaining) {
        this.lobbyTimeRemaining = lobbyTimeRemaining;
    }
    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public MutableLiveData<AddDataLiveResponse> getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(MutableLiveData<AddDataLiveResponse> onSuccess) {
        this.onSuccess = onSuccess;
    }


    public void letGoBtn() {
        disposable.add(Global.initRetrofit().getSystemTime(BuildConfig.APIKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((questions, throwable) -> {
                    if (throwable == null) {
                        Log.d("System Time....",""+questions.getSystemTime());
                        Date date = DateUtils.parseDateTime24(questions.getSystemTime());
                        serverTime.postValue(date.getTime());
                    } else {
                        Log.e("System Time....",""+throwable);
                        toast.postValue(throwable.getLocalizedMessage());
                    }
                }));
    }
    public void cancelQuiz() {

    }
    public void getQuestionsByQuizId() {
        disposable.add(Global.initRetrofit().getQuestionsByQuizId(BuildConfig.APIKEY, String.valueOf(twistQuizesItem.getQuizId()), Global.userId.get())
                /*disposable.add(Global.initRetrofit().getQuestionsByQuizId(BuildConfig.APIKEY, "11")*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((questions, throwable) -> {
                    if (throwable == null) {
                        Log.d("QUIZ....",""+questions);
                        Log.d("QUIZ....",""+questions.getSkipLifeline());
                        Log.d("QUIZ....",""+questions.getSubscribedAmount());
                        if (questions.getQuestions() != null && questions.getQuestions().size() > currentPosition.get()) {
                            questionsList = questions.getQuestions();
                            totalQuestions.set(questions.getQuestions().size());
                            skipLifelines.setValue(questions.getSkipLifeline());
                            subscribedAmount.setValue(questions.getSubscribedAmount());
                            currentQuestions.setValue(questions.getQuestions().get(currentPosition.get()));
                            currentPosition.set(currentPosition.get() + 1);
                        }
                    } else {
                        Log.e("QUIZ....",""+throwable);
                    }

                }));
    }

    public void getLobbyMessages() {
        disposable.add(Global.initRetrofit().lobbyMessages(BuildConfig.APIKEY)
                /*disposable.add(Global.initRetrofit().getQuestionsByQuizId(BuildConfig.APIKEY, "11")*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .doOnTerminate(() -> isLoading.set(false))
                .subscribe((messages, throwable) -> {
                    if (throwable == null) {
                        Log.e("Lobby....","Messages "+messages);
                       onLobbySuccess.setValue(messages);
                    } else {
                        Log.e("Lobby....","Messages "+throwable);
                    }

                }));
    }

    public void updateGameStarted() {
        disposable.add(Global.initRetrofit().updateGameStarted(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(twistQuizesItem.getQuizId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) ->
                        Log.d("QUIZ_START", questions != null ? "updateGameStarted: "+questions.isStatus() : throwable.getLocalizedMessage())));
    }
    public void addPointsToWallet() {
        disposable.add(Global.initRetrofit().addPointsToWallet(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(totalScore.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) -> {
                    //
                }));
        disposable.add(Global.initRetrofit().increasePlay(BuildConfig.APIKEY, String.valueOf(Global.userId), String.valueOf(twistQuizesItem.getQuizId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe((questions, throwable) -> {
                    //
                }));
    }

    public void onAnswerClick(int selectAnswerNum) {
        Log.d("selectAnswerNum ","..."+selectAnswerNum+" "+isAnswer.getValue());
        if (currentQuestions.getValue() != null /*&& isAnswer.getValue() == null*/ ) {
            //rapidFireDuration.set(0);
            resetQuestion();
            if(selectAnswerNum == 0){
                firstAnswerVisibility.set(View.VISIBLE);

            }else if(selectAnswerNum == 1){
                secondAnswerVisibility.set(View.VISIBLE);

            }else if(selectAnswerNum == 2){
                thirdAnswerVisibility.set(View.VISIBLE);

            }else {
                fourthAnswerVisibility.set(View.VISIBLE);
            }
            NewQuestions.Question questionsItem = currentQuestions.getValue();
            String answer = answerList.get(selectAnswerNum);
            isAnswer.setValue(true);
            answerVal.setValue(answer);
            /*if (answer.equalsIgnoreCase(questionsItem.getTrueAns())) {
                trueAnswerPosition.set(selectAnswerNum);
                wrongAnswerPosition.set(-1);
                //isAnswer.setValue(answer.equalsIgnoreCase(questionsItem.getTrueAns()));
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
                    //isAnswer.setValue(answer.equalsIgnoreCase(questionsItem.getTrueAns()));
                } else {
                    isDoubleDip = false;
                }
            }*/

        }

    }
    public void createGameHashMap(int pos, boolean isTimerOff, String skip) {
        if(isTimerOff) {
            //Log.e("Hash", String.valueOf(getTimeRemaining())+" isTimerOff "+isTimerOff);
            hashMap.put("question_id[" + (pos - 1) + "]", String.valueOf(getQuestionsList().get(pos - 1).getId()));
            hashMap.put("selected_ans[" + (pos - 1) + "]", "-");
            hashMap.put("time_taken[" + (pos - 1) + "]", "0");
        }else{
            //Log.e("Hash.....",skip);
            if (skip.equals("skip")) {
                hashMap.put("question_id[" + (pos - 1) + "]", String.valueOf(getQuestionsList().get(pos - 1).getId()));
                hashMap.put("selected_ans[" + (pos - 1) + "]", "skip");
                hashMap.put("time_taken[" + (pos - 1) + "]", "0");
            }else {
                //Log.e("Hash", String.valueOf(getTimeRemaining().get()));
                //Log.e("Answer...", answerVal.getValue());
                hashMap.put("question_id[" + (pos - 1) + "]", String.valueOf(getQuestionsList().get(pos - 1).getId()));
                hashMap.put("selected_ans[" + (pos - 1) + "]", answerVal.getValue());
                hashMap.put("time_taken[" + (pos - 1) + "]", String.valueOf(getTimeRemaining().get()));
            }

        }
    }
    /*public void callAddGameDataLiveApi(HashMap<String, Integer> hashMap) {*/
        public void callAddGameDataLiveApi(String quizType) {
            //Log.e("::: ",""+Global.userId.get()+" "+getTwistQuizesItem().getQuizId());
        hashMap.put("user_id", Global.userId.get());/*Global.userId.get()*/
        hashMap.put("quiz_id", String.valueOf(getTwistQuizesItem().getQuizId()));

//            Log.e("::: map ",hashMap.size()+""+hashMap.get(""));
//            Log.e("::: quizType ",quizType);
            if(quizType.equals("past")){
                disposable.add(Global.initRetrofit().addGameDataPast(BuildConfig.APIKEY, hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((addDataLiveResponse, throwable) -> {
                            Log.e(">>>> past", "" + addDataLiveResponse + ".." + throwable);
                            if (addDataLiveResponse != null) {
                                onSuccess.setValue(addDataLiveResponse);
                            } else if (throwable != null) {
                                toast.setValue(throwable.getLocalizedMessage());
                            }
                        }));
            }else {
                disposable.add(Global.initRetrofit().addGameDataLive(BuildConfig.APIKEY, hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((addDataLiveResponse, throwable) -> {
                            Log.e(">>>> live", "" + addDataLiveResponse + ".." + throwable);
                            if (addDataLiveResponse != null) {
                                onSuccess.setValue(addDataLiveResponse);
                            } else if (throwable != null) {
                                toast.setValue(throwable.getLocalizedMessage());
                            }
                        }));
            }
    }
    public void skipQuestion() {
        //isSkipAnswer.setValue(true);
        firstAnswerVisibility.set(View.VISIBLE);
        secondAnswerVisibility.set(View.VISIBLE);
        thirdAnswerVisibility.set(View.VISIBLE);
        fourthAnswerVisibility.set(View.VISIBLE);
    }

    public void resetQuestion() {
        //isSkipAnswer.setValue(true);
        firstAnswerVisibility.set(View.GONE);
        secondAnswerVisibility.set(View.GONE);
        thirdAnswerVisibility.set(View.GONE);
        fourthAnswerVisibility.set(View.GONE);
    }

    public void hide2WrongAnswer() {
        if (currentQuestions.getValue() != null) {
            NewQuestions.Question questionsItem = currentQuestions.getValue();
            int count = 0;
            for (int i = 0; i < answerList.size(); i++) {
                String ans = answerList.get(i);
//                if (!ans.equals(questionsItem.getTrueAns())) {
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

//                }
            }
        }
    }

    public void showAllAnswers() {
        firstAnswerVisibility.set(View.GONE);
        secondAnswerVisibility.set(View.GONE);
        thirdAnswerVisibility.set(View.GONE);
        fourthAnswerVisibility.set(View.GONE);
    }

    public void resetAllAnswers() {
        firstAnswerVisibility.set(View.VISIBLE);
        secondAnswerVisibility.set(View.VISIBLE);
        thirdAnswerVisibility.set(View.VISIBLE);
        fourthAnswerVisibility.set(View.VISIBLE);
    }

    public void timesUp() {
        if (currentQuestions.getValue() != null) {
            rapidFireDuration.set(0);
            NewQuestions.Question questionsItem = currentQuestions.getValue();
            for (int i = 0; i < answerList.size(); i++) {
                String ans = answerList.get(i);
//                if (ans.equals(questionsItem.getTrueAns())) {
                    trueAnswerPosition.set(i);
                    break;
//                }
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

    public QuizItem getQuizesItem() {
        return quizesItem;
    }

    public void setQuizesItem(QuizItem quizesItem) {
        this.quizesItem = quizesItem;
    }

    public QuizItem getTwistQuizesItem() {
        return twistQuizesItem;
    }

    public void setTwistQuizesItem(QuizItem twistQuizesItem) {
        this.twistQuizesItem = twistQuizesItem;
    }

    public MutableLiveData<NewQuestions.Question> getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(MutableLiveData<NewQuestions.Question> currentQuestions) {
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

    public MutableLiveData<String> getSkipLifelines() {
        return skipLifelines;
    }

    public MutableLiveData<String> getSubscribedAmount() {
        return subscribedAmount;
    }

    public void setSubscribedAmount(MutableLiveData<String> subscribedAmount) {
        this.subscribedAmount = subscribedAmount;
    }

    public void setSkipLifelines(MutableLiveData<String> skipLifelines) {
        this.skipLifelines = skipLifelines;
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

    public List<NewQuestions.Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<NewQuestions.Question> questionsList) {
        this.questionsList = questionsList;
    }

    public MutableLiveData<Boolean> getIsAnswer() {
        return isAnswer;
    }

    public void setIsSkipAnswer(MutableLiveData<Boolean> isSkipAnswer) {
        this.isSkipAnswer = isSkipAnswer;
    }

    public MutableLiveData<Boolean> getIsSkipAnswer() {
        return isSkipAnswer;
    }

    public void setIsAnswer(MutableLiveData<Boolean> isAnswer) {
        this.isAnswer = isAnswer;
    }

    public MutableLiveData<String> getAnswerVal() {
        return answerVal;
    }

    public void setAnswerVal(MutableLiveData<String> answerVal) {
        this.answerVal = answerVal;
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

    public ObservableBoolean getIsInfo() {
        return isInfo;
    }

    public void setIsInfo(ObservableBoolean isInfo) {
        this.isInfo = isInfo;
    }

    public ObservableBoolean getIsLobby() {
        return isLobby;
    }

    public void setIsLobby(ObservableBoolean isLobby) {
        this.isLobby = isLobby;
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

    public boolean isUseSkip() {
        return isUseSkip;
    }

    public void setUseSkip(boolean useSkip) {
        isUseSkip = useSkip;
    }

    public boolean isUseOnce() {
        return isUseOnce;
    }

    public void setUseOnce(boolean useOnce) {
        isUseOnce = useOnce;
    }

    public boolean isUseLifeLineInCurrentQue() {
        return isUseLifeLineInCurrentQue;
    }

    public void setUseLifeLineInCurrentQue(boolean useLifeLineInCurrentQue) {
        isUseLifeLineInCurrentQue = useLifeLineInCurrentQue;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
}
