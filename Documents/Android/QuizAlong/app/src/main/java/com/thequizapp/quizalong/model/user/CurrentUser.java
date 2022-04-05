package com.thequizapp.quizalong.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentUser {

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private boolean status;

    @SerializedName("course")
    public ArrayList<Course> course;

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

    public ArrayList<Course> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<Course> course) {
        this.course = course;
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

    public static class Course{
        @SerializedName("key")
        private int key;

        @SerializedName("value")
        private String value;

        @SerializedName("year")
        private ArrayList<Year> year;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ArrayList<Year> getYear() {
            return year;
        }

        public void setYear(ArrayList<Year> year) {
            this.year = year;
        }
    }

    public static class Year{
        @SerializedName("key")
        private int key;

        @SerializedName("value")
        private String value;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}