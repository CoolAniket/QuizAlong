package com.retrytech.quizbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ItemNotificationBinding;
import com.retrytech.quizbox.model.notification.Notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notifications.NotificationsItem> notifications = new ArrayList<>();

    public List<Notifications.NotificationsItem> getNotifications() {
        return notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.setModel(position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateData(List<Notifications.NotificationsItem> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void addNewData(List<Notifications.NotificationsItem> notifications) {
        for (int i = 0; i < notifications.size(); i++) {
            this.notifications.add(notifications.get(i));
            notifyItemInserted(this.notifications.size() - 1);
        }
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            Notifications.NotificationsItem notificationsItem = notifications.get(position);
            binding.setModel(notificationsItem);
        }
    }
}
