package com.thequizapp.quizalong.model.categories;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoriesResponse {

    @SerializedName("year")
    private ArrayList<Year> year;
    private String message;
    private boolean status;

    public ArrayList<Year> getYear() {
        return year;
    }

    public void setYear(ArrayList<Year> year) {
        this.year = year;
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

    public static class Year{

        private int key;
        private String name;
        private String description;
        private ArrayList<Category> categories;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<Category> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Category> categories) {
            this.categories = categories;
        }
    }
    public static class Category{

        private int key;
        private String value;
        private String image;
        private int star;

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

        public void setImage(String image) {
            this.image = image;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }
    }
}