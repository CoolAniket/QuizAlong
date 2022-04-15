package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.home.HomePage;

import java.util.List;

public class QuizByCatId {

//    @SerializedName("quizes")
//    private List<HomePage.QuizesItem> quizes;

    @SerializedName("upcoming_quizes")
    private List<HomePage.QuizesItem> upcomingQuizes;
    @SerializedName("past_quizes")
    private List<HomePage.QuizesItem> pastQuizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;


//    public List<HomePage.QuizesItem> getQuizes() {
//        return quizes;
//    }
//
//    public void setQuizes(List<HomePage.QuizesItem> quizes) {
//        this.quizes = quizes;
//    }

    public List<HomePage.QuizesItem> getUpcomingQuizes() {
        return upcomingQuizes;
    }

    public void setUpcomingQuizes(List<HomePage.QuizesItem> upcomingQuizes) {
        this.upcomingQuizes = upcomingQuizes;
    }

    public List<HomePage.QuizesItem> getPastQuizes() {
        return pastQuizes;
    }

    public void setPastQuizes(List<HomePage.QuizesItem> pastQuizes) {
        this.pastQuizes = pastQuizes;
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