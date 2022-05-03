package com.thequizapp.quizalong.model.questions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewQuestions {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("skip_lifeline")
    private String skipLifeline;
    @SerializedName("subscribed_amount")
    private String subscribedAmount;

    @SerializedName("questions")
    private List<Question> questions = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSkipLifeline() {
        return skipLifeline;
    }

    public void setSkipLifeline(String skipLifeline) {
        this.skipLifeline = skipLifeline;
    }

    public String getSubscribedAmount() {
        return subscribedAmount;
    }

    public void setSubscribedAmount(String subscribedAmount) {
        this.subscribedAmount = subscribedAmount;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public class Question {

        @SerializedName("id")
        private Integer id;
        @SerializedName("question")
        private String question;
        @SerializedName("true_ans")
        private String trueAns;
        @SerializedName("false_1")
        private String false1;
        @SerializedName("false_2")
        private String false2;
        @SerializedName("false_3")
        private String false3;
        @SerializedName("reward")
        private Integer reward;
        @SerializedName("has_image")
        private Integer hasImage;
        @SerializedName("image")
        private String image;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("category_id")
        private Integer categoryId;
        @SerializedName("quiz_id")
        private Integer quizId;
        @SerializedName("assigned")
        private Integer assigned;
        @SerializedName("description")
        private Object description;
        @SerializedName("options")
        private List<String> options = null;
        @SerializedName("points")
        private Integer points;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getFalse1() {
            return false1;
        }

        public void setFalse1(String false1) {
            this.false1 = false1;
        }

        public String getFalse2() {
            return false2;
        }

        public void setFalse2(String false2) {
            this.false2 = false2;
        }

        public String getFalse3() {
            return false3;
        }

        public void setFalse3(String false3) {
            this.false3 = false3;
        }

        public Integer getReward() {
            return reward;
        }

        public void setReward(Integer reward) {
            this.reward = reward;
        }

        public Integer getHasImage() {
            return hasImage;
        }

        public void setHasImage(Integer hasImage) {
            this.hasImage = hasImage;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getQuizId() {
            return quizId;
        }

        public void setQuizId(Integer quizId) {
            this.quizId = quizId;
        }

        public Integer getAssigned() {
            return assigned;
        }

        public void setAssigned(Integer assigned) {
            this.assigned = assigned;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }
        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public Integer getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points = points;
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
}

