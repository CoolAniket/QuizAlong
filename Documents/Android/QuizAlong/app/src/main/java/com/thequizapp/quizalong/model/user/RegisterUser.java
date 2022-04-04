package com.thequizapp.quizalong.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterUser {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    user UserObject;
    ArrayList< Object > course = new ArrayList < Object > ();


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public user getUser() {
        return UserObject;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(user userObject) {
        this.UserObject = userObject;
    }
}
/*
public class User {
    @SerializedName("id")
    private float id;
    @SerializedName("identity")
    private String identity;
    @SerializedName("image")
    private String image = null;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("wallet")
    private float wallet;
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


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getIdentity() {
        return identity;
    }

    public String getImage() {
        return image;
    }

    public String getFull_name() {
        return full_name;
    }

    public float getWallet() {
        return wallet;
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

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
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
}*/
