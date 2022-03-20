package com.thequizapp.quizalong.model.notification;

import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Notifications {

    @SerializedName("message")
    private String message;

    @SerializedName("notifications")
    private List<NotificationsItem> notificationsItems;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public List<NotificationsItem> getNotificationsItems() {
        return notificationsItems;
    }

    public boolean isStatus() {
        return status;
    }

    public static class NotificationsItem {

        @SerializedName("msg")
        private String msg;

        @SerializedName("img")
        private String img;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("type")
        private int type;

        public String getMsg() {
            return msg;
        }

        public String getImg() {
            return img;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                long time = sdf.parse(createdAt).getTime();
                long now = System.currentTimeMillis();
                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
                return ago.toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return createdAt;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }
    }
}