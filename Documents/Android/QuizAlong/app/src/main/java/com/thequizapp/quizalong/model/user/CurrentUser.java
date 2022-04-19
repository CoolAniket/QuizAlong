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

    @SerializedName("additional_info")
    private int additional_info;

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

    public int getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(int additional_info) {
        this.additional_info = additional_info;
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

        @SerializedName("fullname")
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

        @SerializedName("course_id")
        private String courseId;

        @SerializedName("year_id")
        private String yearId;

        @SerializedName("college")
        private String college;

        @SerializedName("proof")
        private String proof;

        @SerializedName("dob")
        private String dob;

        @SerializedName("mobile_no")
        private String mobileNo;

        @SerializedName("password")
        private String password;

        @SerializedName("otp")
        private String otp;

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

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getYearId() {
            return yearId;
        }

        public void setYearId(String yearId) {
            this.yearId = yearId;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getProof() {
            return proof;
        }

        public void setProof(String proof) {
            this.proof = proof;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
    }

    public static class Course{
        @SerializedName("key")
        private int key;

        @SerializedName("value")
        private String value;

        @SerializedName("image")
        private String image;

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

        public String getImage() {
            return image;
        }

        public void setImage(String value) {
            this.image = image;
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