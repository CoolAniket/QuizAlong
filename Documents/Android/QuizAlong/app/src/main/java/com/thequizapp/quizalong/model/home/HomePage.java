package com.thequizapp.quizalong.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePage {

    @SerializedName("quizes")
    private List<QuizesItem> quizes;

    @SerializedName("categories")
    private List<CategoriesItem> categories;

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

    public static class QuizesItem {

        @SerializedName("total_points")
        private int totalPoints;
        @SerializedName("cat_id")
        private int catId;
        @SerializedName("questions")
        private int questions;
        @SerializedName("date")
        private String date;
        @SerializedName("quiz_id")
        private int quizId;
        @SerializedName("image")
        private String image;
        @SerializedName("title")
        private String title;
        @SerializedName("type")
        private String type;
        @SerializedName("description")
        private String description;
        @SerializedName("system_time")
        private String systemTime;
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("total_time")
        private int totalTime;
        @SerializedName("category")
        private Category category;
        @SerializedName("win_percent")
        private Integer winPercent;
        @SerializedName("prize")
        private Integer prize;
        @SerializedName("entry")
        private List<Integer> entry = null;

        public int getTotalPoints() {
            return totalPoints;
        }

        public void setTotalPoints(int totalPoints) {
            this.totalPoints = totalPoints;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public int getQuestions() {
            return questions;
        }

        public void setQuestions(int questions) {
            this.questions = questions;
        }

        public int getQuizId() {
            return quizId;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeInString() {
            return type;
        }

        public String getSystemTime() {
            return systemTime;
        }

        public void setSystemTime(String systemTime) {
            this.systemTime = systemTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(int totalTime) {
            this.totalTime = totalTime;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getWinPercent() {
            return winPercent;
        }

        public void setWinPercent(Integer winPercent) {
            this.winPercent = winPercent;
        }

        public Integer getPrize() {
            return prize;
        }

        public void setPrize(Integer prize) {
            this.prize = prize;
        }

        public List<Integer> getEntry() {
            return entry;
        }

        public void setEntry(List<Integer> entry) {
            this.entry = entry;
        }
    }
}