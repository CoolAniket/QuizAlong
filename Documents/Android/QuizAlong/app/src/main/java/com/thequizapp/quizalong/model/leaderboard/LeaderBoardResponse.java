package com.thequizapp.quizalong.model.leaderboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderBoardResponse {

    @SerializedName("leaderboard")
    private LeaderboardItem leaderboardItem;


    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public LeaderboardItem getLeaderboardItem() {
        return leaderboardItem;
    }

    public void setLeaderboardItem(LeaderboardItem leaderboardItem) {
        this.leaderboardItem = leaderboardItem;
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
        @SerializedName("groups")
        private List<Integer> groups = null;
        @SerializedName("group1")
        private List<LeaderboardUser> group1;
        @SerializedName("group2")
        private List<LeaderboardUser> group2;
        @SerializedName("group3")
        private List<LeaderboardUser> group3;

        public List<Integer> getGroups() {
            return groups;
        }

        public void setGroups(List<Integer> groups) {
            this.groups = groups;
        }

        public List<LeaderboardUser> getGroup1() {
            return group1;
        }

        public void setGroup1(List<LeaderboardUser> group1) {
            this.group1 = group1;
        }

        public List<LeaderboardUser> getGroup2() {
            return group2;
        }

        public void setGroup2(List<LeaderboardUser> group2) {
            this.group2 = group2;
        }

        public List<LeaderboardUser> getGroup3() {
            return group3;
        }

        public void setGroup3(List<LeaderboardUser> group3) {
            this.group3 = group3;
        }

    }
    public static class LeaderboardUser {

        @SerializedName("id")
        private int id;

        @SerializedName("quiz_id")
        private String quizId;

        @SerializedName("user_id")
        private int userId;


        @SerializedName("user_fullname")
        private String fullName;

        @SerializedName("user_image")
        private String image;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("user_identity")
        private String userIdentity;

        @SerializedName("points")
        private int points;

        @SerializedName("total_points")
        private int totalPoints;

        @SerializedName("rank")
        private int rank;

        @SerializedName("time")
        private int time;

        @SerializedName("paid")
        private int paid;

        @SerializedName("won")
        private int won;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("status")
        private String status;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}