package com.thequizapp.quizalong.model.payment;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("order_id")
    private String orderId;
    private String message;
    private boolean status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
