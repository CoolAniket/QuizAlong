package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemRequestBinding;
import com.thequizapp.quizalong.model.redeemrequest.RedeemRequest;

import java.util.ArrayList;
import java.util.List;

public class RedeemRequestAdapter extends RecyclerView.Adapter<RedeemRequestAdapter.RedeemRequestViewHolder> {
    private List<RedeemRequest.RedeemsItem> redeems = new ArrayList<>();

    @NonNull
    @Override
    public RedeemRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new RedeemRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedeemRequestViewHolder holder, int position) {
        holder.setModel(position);
    }

    @Override
    public int getItemCount() {
        return redeems.size();
    }

    public void updateData(List<RedeemRequest.RedeemsItem> redeems) {
        this.redeems = redeems;
        notifyDataSetChanged();
    }

    public class RedeemRequestViewHolder extends RecyclerView.ViewHolder {
        ItemRequestBinding binding;

        public RedeemRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            RedeemRequest.RedeemsItem redeemsItem = redeems.get(position);
            binding.setModel(redeemsItem);
        }
    }
}
