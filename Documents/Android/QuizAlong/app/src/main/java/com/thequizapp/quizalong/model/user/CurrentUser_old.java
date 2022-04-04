package com.thequizapp.quizalong.model.user;

import com.google.gson.annotations.SerializedName;

public class CurrentUser_old {

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
        @SerializedName("full_name")
        private String full_name;
        @SerializedName("total_points")
        private float total_points;
        @SerializedName("total_plays")
        private float total_plays;
        @SerializedName("refer_code")
        private String refer_code;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("course_id")
        private String course_id = null;
        @SerializedName("year_id")
        private String year_id = null;
        @SerializedName("college")
        private String college = null;
        @SerializedName("proof")
        private String proof = null;
        @SerializedName("dob")
        private String dob = null;

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

        public String getFull_name() {
            return full_name;
        }

        public float getTotal_points() {
            return total_points;
        }

        public float getTotal_plays() {
            return total_plays;
        }

        public String getRefer_code() {
            return refer_code;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getCourse_id() {
            return course_id;
        }

        public String getYear_id() {
            return year_id;
        }

        public String getCollege() {
            return college;
        }

        public String getProof() {
            return proof;
        }

        public String getDob() {
            return dob;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public void setTotal_points(float total_points) {
            this.total_points = total_points;
        }

        public void setTotal_plays(float total_plays) {
            this.total_plays = total_plays;
        }

        public void setRefer_code(String refer_code) {
            this.refer_code = refer_code;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public void setYear_id(String year_id) {
            this.year_id = year_id;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public void setProof(String proof) {
            this.proof = proof;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }
    }
}