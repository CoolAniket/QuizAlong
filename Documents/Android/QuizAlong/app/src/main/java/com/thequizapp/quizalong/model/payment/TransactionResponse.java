package com.thequizapp.quizalong.model.payment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("history")
    private List<History> history = new ArrayList<>();

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
    public List<History> getHistory() {
        return history;
    }
    public void setHistory(List<History> history) {
        this.history = history;
    }

    public static class History {
        @SerializedName("quiz_id")
        private int quizId;

        @SerializedName("quiz_title")
        private String quizTitle;

        @SerializedName("quiz_description")
        private String quizDescription;

        @SerializedName("amount")
        private int amount;

        @SerializedName("order_id")
        private String orderId;

        @SerializedName("status")
        private String status;

        @SerializedName("transaction_date")
        private String transactionDate;

        public int getQuizId() {
            return quizId;
        }
        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }
        public String getQuizTitle() {
            return quizTitle;
        }
        public void setQuizTitle(String quizTitle) {
            this.quizTitle = quizTitle;
        }
        public String getQuizDescription() {
            return quizDescription;
        }
        public void setQuizDescription(String quizDescription) {
            this.quizDescription = quizDescription;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        public String getOrderId() {
            return orderId;
        }
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }
    }
}
