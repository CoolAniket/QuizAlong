package com.thequizapp.quizalong.model.home;

import com.google.gson.annotations.SerializedName;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.List;

public class HomePage {

    @SerializedName("quizes")
    private List<QuizItem> quizes;

    @SerializedName("categories")
    private List<CategoriesItem> categories;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<QuizItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuizItem> quizes) {
        this.quizes = quizes;
    }

    public List<CategoriesItem> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories = categories;
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

    public static class CategoriesItem {

        @SerializedName("image")
        private String image;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
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
    }

    public static class Category {

        @SerializedName("image")
        private String image;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
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
    }

}