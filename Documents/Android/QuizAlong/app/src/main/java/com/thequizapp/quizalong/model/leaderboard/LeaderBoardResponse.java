package com.thequizapp.quizalong.model.leaderboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderBoardResponse {

    @SerializedName("leaderboard")
    private List<LeaderboardItem> leaderboardList;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<LeaderboardItem> getLeaderboardList() {
        return leaderboardList;
    }

    public void setLeaderboardList(List<LeaderboardItem> leaderboardList) {
        this.leaderboardList = leaderboardList;
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

    public static class LeaderboardItem {

        @SerializedName("image")
        private String image;

        @SerializedName("user_fullname")
        private String fullName;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("user_identity")
        private String userIdentity;

        @SerializedName("points")
        private int points;

        @SerializedName("total_points")
        private int totalPoints;

        @SerializedName("time")
        private int time;

        @SerializedName("paid")
        private int paid;

        @SerializedName("won")
        private int won;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("quiz_id")
        private String quizId;

        @SerializedName("user_id")
        private int userId;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUserIdentity() {
            return userIdentity;
        }

        public void setUserIdentity(String userIdentity) {
            this.userIdentity = userIdentity;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public void setTotalPoints(int totalPoints) {
            this.totalPoints = totalPoints;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getPaid() {
            return paid;
        }

        public void setPaid(int paid) {
            this.paid = paid;
        }

        public int getWon() {
            return won;
        }

        public void setWon(int won) {
            this.won = won;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getQuizId() {
            return quizId;
        }

        public void setQuizId(String quizId) {
            this.quizId = quizId;
        }
    }
}