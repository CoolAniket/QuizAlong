package com.retrytech.quizbox.model.user;

import com.google.gson.annotations.SerializedName;

public class CurrentUser {

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class User {

        @SerializedName("image")
        private Object image;

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

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
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