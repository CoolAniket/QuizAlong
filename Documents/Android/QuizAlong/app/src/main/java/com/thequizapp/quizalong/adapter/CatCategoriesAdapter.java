package com.thequizapp.quizalong.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemCatCategoriesBinding;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.view.quiz.QuizActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CatCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClick onItemClick;
    private OnFavouriteCheck onFavouriteCheck;


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnFavouriteCheck(OnFavouriteCheck onFavouriteCheck) {
        this.onFavouriteCheck = onFavouriteCheck;
    }

    private List<CategoriesResponse.Category> categories;

    public List<CategoriesResponse.Category> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesResponse.Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat_categories, parent, false);
        return new HomeCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeCategoriesViewHolder) {
            HomeCategoriesViewHolder viewHolder = (HomeCategoriesViewHolder) holder;
            viewHolder.setModel(position);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateData(List<CategoriesResponse.Category> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }


    public interface OnItemClick {
        void onClick(Pair[] pair, CategoriesResponse.Category categoriesItem);
    }

    public interface OnFavouriteCheck {
        void onChecked(CategoriesResponse.Category categoriesItem, boolean checked);
    }



    public class HomeCategoriesViewHolder extends RecyclerView.ViewHolder {
        ItemCatCategoriesBinding binding;

        public HomeCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            if (categories.get(position) != null) {
                CategoriesResponse.Category categoriesItem = categories.get(position);
                binding.tvTitle.setTransitionName("hero" + position);
                binding.ivIcon.setTransitionName("hero1" + position);
                binding.btnCheck.setOnClickListener(v -> {
                    Pair<View, String>[] pairs = new Pair[2];
                    pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                    pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                    onItemClick.onClick(pairs, categoriesItem);
                });
                binding.chkFavourite.setOnClickListener((view) -> {
                    new CustomDialogBuilder(view.getContext()).showFavouriteDialog(R.drawable.gold_star,
                            (categoriesItem.getStar() == 1 ? "Remove" : "Add")+" Favourite",
                            String.format((categoriesItem.getStar() == 1 ? "Remove %s from Favourites" : "Add %s to Favourites"), categoriesItem.getValue()),
                            "Yes",
                            "No",
                            new CustomDialogBuilder.OnDismissListener() {
                                @Override
                                public void onPositiveDismiss() {
                                    if (onFavouriteCheck != null) {
                                        onFavouriteCheck.onChecked(categoriesItem, categoriesItem.getStar() != 1);
                                        binding.chkFavourite.setChecked(true);
                                    }
                                }

                                @Override
                                public void onNegativeDismiss() {
                                    /*if (onFavouriteCheck != null) {
                                        onFavouriteCheck.onChecked(categoriesItem, false);
                                        binding.chkFavourite.setChecked(false);
                                    }*/
                                    binding.chkFavourite.setChecked(categoriesItem.getStar() == 1);
                                }
                            });

                });
                binding.setModel(categoriesItem);
            }
        }
    }
}
