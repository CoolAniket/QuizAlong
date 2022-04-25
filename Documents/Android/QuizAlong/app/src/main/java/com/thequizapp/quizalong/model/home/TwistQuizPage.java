package com.thequizapp.quizalong.model.home;

import com.google.gson.annotations.SerializedName;

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


    public static class QuizItem {

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
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("total_time")
        private Integer totalTime;
        @SerializedName("win_percent")
        private Integer winPercent;
        @SerializedName("prize")
        private Integer prize;
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

        public List<Integer> getEntry() {
            return entry;
        }

        public void setEntry(List<Integer> entry) {
            this.entry = entry;
        }

    }
}