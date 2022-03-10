package com.retrytech.quizbox.model.leaderboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderBoard {

    @SerializedName("quizes")
    private List<QuizesItem> quizes;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<QuizesItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuizesItem> quizes) {
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

    public static class QuizesItem {

        @SerializedName("image")
        private String image;

        @SerializedName("full_name")
        private String fullName;

        @SerializedName("wallet")
        private int wallet;

        @SerializedName("total_plays")
        private int totalPlays;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("identity")
        private String identity;

        @SerializedName("total_points")
        private int totalPoints;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("refer_code")
        private String referCode;

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

        public int getWallet() {
            return wallet;
        }

        public void setWallet(int wallet) {
            this.wallet = wallet;
        }

        public int getTotalPlays() {
            return totalPlays;
        }

        public void setTotalPlays(int totalPlays) {
            this.totalPlays = totalPlays;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public void setTotalPoints(int totalPoints) {
            this.totalPoints = totalPoints;
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

        public String getReferCode() {
            return referCode;
        }

        public void setReferCode(String referCode) {
            this.referCode = referCode;
        }
    }
}