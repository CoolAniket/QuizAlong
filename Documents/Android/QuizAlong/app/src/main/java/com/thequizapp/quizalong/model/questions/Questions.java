package com.thequizapp.quizalong.model.questions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questions {

    @SerializedName("quiz")
    private Quiz quiz;

    @SerializedName("questions")
    private List<QuestionsItem> questionsItems;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<QuestionsItem> getQuestionsItems() {
        return questionsItems;
    }

    public void setQuestionsItems(List<QuestionsItem> questionsItems) {
        this.questionsItems = questionsItems;
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

    public static class QuestionsItem {

        @SerializedName("reward")
        private int reward;

        @SerializedName("image")
        private String image;

        @SerializedName("quiz_id")
        private int quizId;

        @SerializedName("question")
        private String question;

        @SerializedName("true_ans")
        private String trueAns;

        @SerializedName("has_image")
        private int hasImage;

        @SerializedName("updated_at")
        private Object updatedAt;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("false_1")
        private String false1;

        @SerializedName("false_3")
        private String false3;

        @SerializedName("false_2")
        private String false2;

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getQuizId() {
            return quizId;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getTrueAns() {
            return trueAns;
        }

        public void setTrueAns(String trueAns) {
            this.trueAns = trueAns;
        }

        public int getHasImage() {
            return hasImage;
        }

        public void setHasImage(int hasImage) {
            this.hasImage = hasImage;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFalse1() {
            return false1;
        }

        public void setFalse1(String false1) {
            this.false1 = false1;
        }

        public String getFalse3() {
            return false3;
        }

        public void setFalse3(String false3) {
            this.false3 = false3;
        }

        public String getFalse2() {
            return false2;
        }

        public void setFalse2(String false2) {
            this.false2 = false2;
        }

        public List<String> getAnswerList() {
            List<String> strings = new ArrayList<>();
            strings.add(getFalse1());
            strings.add(getTrueAns());
            strings.add(getFalse2());
            strings.add(getFalse3());
            Collections.shuffle(strings);
            return strings;
        }
    }

    public static class Quiz {

        @SerializedName("total_plays")
        private int totalPlays;

        @SerializedName("updated_at")
        private Object updatedAt;

        @SerializedName("is_permium")
        private int isPermium;

        @SerializedName("cat_id")
        private int catId;

        @SerializedName("created_at")
        private Object createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("type")
        private int type;

        @SerializedName("desc")
        private String desc;

        public int getTotalPlays() {
            return totalPlays;
        }

        public void setTotalPlays(int totalPlays) {
            this.totalPlays = totalPlays;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getIsPermium() {
            return isPermium;
        }

        public void setIsPermium(int isPermium) {
            this.isPermium = isPermium;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}