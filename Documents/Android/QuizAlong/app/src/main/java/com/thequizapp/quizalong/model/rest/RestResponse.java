package com.thequizapp.quizalong.model.rest;

import com.google.gson.annotations.SerializedName;

public class RestResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}