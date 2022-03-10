package com.retrytech.quizbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ItemPlayHistoryBinding;

public class PlayHistoryAdapter extends RecyclerView.Adapter<PlayHistoryAdapter.PlayHistoryViewHolder> {
    @NonNull
    @Override
    public PlayHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_history, parent, false);
        return new PlayHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayHistoryViewHolder holder, int position) {
        holder.binding.getRoot();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PlayHistoryViewHolder extends RecyclerView.ViewHolder {
        ItemPlayHistoryBinding binding;

        public PlayHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
