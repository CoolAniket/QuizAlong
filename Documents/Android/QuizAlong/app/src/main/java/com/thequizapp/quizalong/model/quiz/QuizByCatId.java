package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;

import java.util.List;

public class QuizByCatId {

//    @SerializedName("quizes")
//    private List<HomePage.QuizesItem> quizes;

    @SerializedName("upcoming_quizes")
    private List<HomePage.QuizesItem> upcomingQuizes;
    @SerializedName("past_quizes")
    private List<TwistQuizPage.QuizItem> pastQuizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;


    public List<HomePage.QuizesItem> getUpcomingQuizes() {
        return upcomingQuizes;
    }

    public void setUpcomingQuizes(List<HomePage.QuizesItem> upcomingQuizes) {
        this.upcomingQuizes = upcomingQuizes;
    }

    public List<TwistQuizPage.QuizItem> getPastQuizes() {
        return pastQuizes;
    }

    public void setPastQuizes(List<TwistQuizPage.QuizItem> pastQuizes) {
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