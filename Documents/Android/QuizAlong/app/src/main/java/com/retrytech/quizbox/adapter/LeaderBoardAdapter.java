package com.retrytech.quizbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ItemLeaderBoardBinding;
import com.retrytech.quizbox.model.leaderboard.LeaderBoard;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {
    private List<LeaderBoard.QuizesItem> leaderBoards = new ArrayList<>();

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leader_board, parent, false);
        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        holder.setModel(position);
    }

    @Override
    public int getItemCount() {
        return leaderBoards.size();
    }

    public void updateData(List<LeaderBoard.QuizesItem> leaderBoards) {
        this.leaderBoards = leaderBoards;
        notifyDataSetChanged();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
        ItemLeaderBoardBinding binding;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            LeaderBoard.QuizesItem leaderBoard = leaderBoards.get(position);
            binding.setModel(leaderBoard);
            binding.setPosition(String.valueOf(position + 4));
        }
    }
}
