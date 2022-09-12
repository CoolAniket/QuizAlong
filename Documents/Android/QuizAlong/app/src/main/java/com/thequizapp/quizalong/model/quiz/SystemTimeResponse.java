package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SystemTimeResponse {

    @SerializedName("system_time")
    private String systemTime;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
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