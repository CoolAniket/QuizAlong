package com.thequizapp.quizalong.model.home;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.List;

public class TwistQuizPage {


    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("quizes")
    private List<QuizItem> quizes = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QuizItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuizItem> quizes) {
        this.quizes = quizes;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}