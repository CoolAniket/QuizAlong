package com.retrytech.quizbox.model.quiz;

import com.google.gson.annotations.SerializedName;
import com.retrytech.quizbox.model.home.HomePage;

import java.util.List;

public class QuizByCatId {

    @SerializedName("quizes")
    private List<HomePage.QuizesItem> quizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<HomePage.QuizesItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<HomePage.QuizesItem> quizes) {
        this.quizes = quizes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}