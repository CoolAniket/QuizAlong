package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LobbyMessageResponse {

    @SerializedName("quizes")
    private List<QuizItem> quizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<QuizItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuizItem> quizes) {
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

    public class QuizItem {
        @SerializedName("message")
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}