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

    @SerializedName("user_categories")
    private int user_categories;

    @SerializedName("course")
    public ArrayList<Course> course;

    @SerializedName("colleges")
    public ArrayList<College> colleges;

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

    public int getUser_categories() {
        return user_categories;
    }

    public void setUser_categories(int user_categories) {
        this.user_categories = user_categories;
    }

    public ArrayList<Course> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<Course> course) {
        this.course = course;
    }

    public ArrayList<College> getColleges() {
        return colleges;
    }


    public void setColleges(ArrayList<College> colleges) {
        this.colleges = colleges;
    }

    public static class User {

        @SerializedName("image")
        private Object image;

        @SerializedName("fullname")
        private String fullname;

        @SerializedName("wallet")
        private int wallet;

//        @SerializedName("total_plays")
//        private int totalPlays;

//        @SerializedName("updated_at")
//        private String updatedAt;

        @SerializedName("identity")
        private String identity;

//        @SerializedName("total_points")
//        private int totalPoints;

//        @SerializedName("created_at")
//        private String createdAt;

//        @SerializedName("user_id")
        @SerializedName(value="name", alternate={"user_id", "id"})
        private int id;

        @SerializedName("refercode")
        private String refercode;

        @SerializedName("course")
        private String course;

        @SerializedName("year")
        private String year;

        @SerializedName("college")
        private String college;

        @SerializedName("proof")
        private String proof;

        @SerializedName("dob")
        private String dob;

        @SerializedName("mobileno")
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

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public int getWallet() {
            return wallet;
        }

        public void setWallet(int wallet) {
            this.wallet = wallet;
        }


        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRefercode() {
            return refercode;
        }

        public void setRefercode(String refercode) {
            this.refercode = refercode;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
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

    public class College {

        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("created_at")
        private Object createdAt;
        @SerializedName("updated_at")
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}