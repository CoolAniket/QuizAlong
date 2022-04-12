package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.home.HomePage;

import java.util.List;

public class AddDataLiveResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;

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


}