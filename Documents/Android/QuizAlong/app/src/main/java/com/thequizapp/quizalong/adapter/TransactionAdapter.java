package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemTransactionBinding;
import com.thequizapp.quizalong.model.payment.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.LeaderBoardViewHolder> {
    private List<TransactionResponse.History> transactions = new ArrayList<>();

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        holder.setModel(position);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updateData(List<TransactionResponse.History> leaderBoards) {
        this.transactions = leaderBoards;
        notifyDataSetChanged();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionBinding binding;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            TransactionResponse.History transaction = transactions.get(position);
            binding.setModel(transaction);
        }
    }
}
