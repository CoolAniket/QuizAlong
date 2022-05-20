package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;

import java.util.List;

public class QuizByCatId {

//    @SerializedName("quizes")
//    private List<QuizItem> quizes;

    @SerializedName("upcoming_quizes")
    private List<QuizItem> upcomingQuizes;
    @SerializedName("past_quizes")
    private List<QuizItem> pastQuizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;


    public List<QuizItem> getUpcomingQuizes() {
        return upcomingQuizes;
    }

    public void setUpcomingQuizes(List<QuizItem> upcomingQuizes) {
        this.upcomingQuizes = upcomingQuizes;
    }

    public List<QuizItem> getPastQuizes() {
        return pastQuizes;
    }

    public void setPastQuizes(List<QuizItem> pastQuizes) {
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