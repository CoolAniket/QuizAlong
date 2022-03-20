package com.thequizapp.quizalong.model.categories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("categories")
    private List<CategoriesItem> categoriesItemList;
    private String message;
    private boolean status;

    @SerializedName("categories")
    public List<CategoriesItem> getCategoriesItemList() {
        return categoriesItemList;
    }

    public void setCategoriesItemList(List<CategoriesItem> categoriesItemList) {
        this.categoriesItemList = categoriesItemList;
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
        private String image;
        private String updatedAt;
        private String name;
        private String description;
        private String createdAt;
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