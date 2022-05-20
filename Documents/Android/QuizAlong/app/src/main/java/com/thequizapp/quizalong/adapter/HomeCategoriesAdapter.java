package com.thequizapp.quizalong.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemCategoiesBinding;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.List;

import static com.thequizapp.quizalong.api.Const.AD_FB_TYPE;
import static com.thequizapp.quizalong.api.Const.AD_TYPE;
import static com.thequizapp.quizalong.api.Const.POST_TYPE;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> categories = new ArrayList<>();

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoies, parent, false);
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
        return 3;
        /*return categories.size();*/
    }

    /*public void updateData(List<HomePage.CategoriesItem> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }*/

    public void updateData(List<QuizItem> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    public void addNewAds(int index, UnifiedNativeAd ad) {
        if (!categories.isEmpty() && index < categories.size()) {
            categories.add(index, ad);
            notifyItemInserted(index);
        }
    }

    public void addFBAds(int index, NativeAd nextNativeAd) {
        if (!categories.isEmpty() && index < categories.size()) {
            categories.add(index, nextNativeAd);
            notifyItemInserted(index);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return POST_TYPE;
    }


    public interface OnItemClick {
        void onClick(Pair[] pair, HomePage.CategoriesItem categoriesItem);
    }

    public class HomeCategoriesViewHolder extends RecyclerView.ViewHolder {
        ItemCategoiesBinding binding;

        public HomeCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            /*if(categories.size() > 0) {
                if (categories.get(position) instanceof HomePage.CategoriesItem) {
                    HomePage.CategoriesItem categoriesItem = (HomePage.CategoriesItem) categories.get(position);
                    binding.tvTitle.setTransitionName("hero" + position);
                    binding.ivIcon.setTransitionName("hero1" + position);
                    binding.btnCheck.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[2];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        onItemClick.onClick(pairs, categoriesItem);
                    });
                    binding.getRoot().setOnClickListener(v -> binding.btnCheck.performClick());
                    binding.setModel(categoriesItem);
                }
            }*/
        }
    }
}
