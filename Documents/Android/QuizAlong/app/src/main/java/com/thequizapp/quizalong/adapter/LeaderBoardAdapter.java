package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemLeaderBoardBinding;
import com.thequizapp.quizalong.model.leaderboard.LeaderBoardResponse;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder> {
    private List<LeaderBoardResponse.LeaderboardItem> leaderBoards = new ArrayList<>();

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

    public void updateData(List<LeaderBoardResponse.LeaderboardItem> leaderBoards) {
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
            LeaderBoardResponse.LeaderboardItem leaderBoard = leaderBoards.get(position);
            binding.setModel(leaderBoard);
            binding.setPosition(String.valueOf(position + 4));
        }
    }
}
