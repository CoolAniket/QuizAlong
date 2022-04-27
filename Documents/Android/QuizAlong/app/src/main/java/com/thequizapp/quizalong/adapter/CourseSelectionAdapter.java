package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemCatCategoiesSelectionBinding;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CourseSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoriesResponse.Category> categories = new ArrayList<>();

    private final Set<Integer> selectedCategories = new HashSet<>();

    public List<CategoriesResponse.Category> getCategories() {
        return categories;
    }
    public Set<Integer> getSelectedCategories() {
        return selectedCategories;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat_categoies_selection, parent, false);
        return new CourseCategoriesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CourseCategoriesViewHolder) {
            CourseCategoriesViewHolder viewHolder = (CourseCategoriesViewHolder) holder;
            viewHolder.setModel(position);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateData(@NonNull List<CategoriesResponse.Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class CourseCategoriesViewHolder extends RecyclerView.ViewHolder {
        ItemCatCategoiesSelectionBinding binding;

        public CourseCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            if (categories.get(position) != null) {
                CategoriesResponse.Category categoriesItem = categories.get(position);
                if (categoriesItem.getStar() == 1) {
                    selectedCategories.add(categoriesItem.getKey());
                }
                binding.setModel(categoriesItem);
                binding.getRoot().setOnClickListener(v -> {
                    if (categoriesItem.getStar() == 0) {
                        selectedCategories.add(categoriesItem.getKey());
                        categoriesItem.setStar(1);
                    } else {
                        selectedCategories.remove(categoriesItem.getKey());
                        categoriesItem.setStar(0);
                    }
                    binding.setModel(categoriesItem);
                });
            }
        }
    }
}
