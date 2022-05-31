package com.thequizapp.quizalong.model.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizItem {

    @SerializedName("system_time")
    private String systemTime;
    @SerializedName("quiz_id")
    private Integer quizId;
    @SerializedName("image")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("questions")
    private Integer questions;
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private String day;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("total_time")
    private Integer totalTime;
    @SerializedName("win_percent")
    private int winPercent;
    @SerializedName("prize")
    private int prize;
    @SerializedName("subscribed")
    private int subscribed;
    @SerializedName("played")
    private int played;
    @SerializedName("subscribed_amount")
    private int subscribedAmount;
    @SerializedName("type")
    private String type;
    @SerializedName("entry")
    private List<Integer> entry = null;


    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(Integer winPercent) {
        this.winPercent = winPercent;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public Integer getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Integer subscribed) {
        this.subscribed = subscribed;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getSubscribedAmount() {
        return subscribedAmount;
    }

    public void setSubscribedAmount(Integer subscribedAmount) {
        this.subscribedAmount = subscribedAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getEntry() {
        return entry;
    }

    public void setEntry(List<Integer> entry) {
        this.entry = entry;
    }

}
