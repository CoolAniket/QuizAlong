package com.retrytech.quizbox.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ItemCategoiesBinding;
import com.retrytech.quizbox.model.home.HomePage;

import java.util.ArrayList;
import java.util.List;

import static com.retrytech.quizbox.api.Const.AD_FB_TYPE;
import static com.retrytech.quizbox.api.Const.AD_TYPE;
import static com.retrytech.quizbox.api.Const.POST_TYPE;

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
        if (viewType == AD_TYPE) {
            View unifiedNativeLayoutView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.admob_cateroies,
                    parent, false);
            return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

        } else if (viewType == AD_FB_TYPE) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.fb_categories, parent, false);
            return new AdHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoies, parent, false);
            return new HomeCategoriesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeCategoriesViewHolder) {
            HomeCategoriesViewHolder viewHolder = (HomeCategoriesViewHolder) holder;
            viewHolder.setModel(position);
        } else if (holder instanceof UnifiedNativeAdViewHolder) {
            UnifiedNativeAdViewHolder viewHolder = (UnifiedNativeAdViewHolder) holder;
            UnifiedNativeAd nativeAd = (UnifiedNativeAd) categories.get(position);
            viewHolder.populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
        } else if (holder instanceof AdHolder) {
            AdHolder adHolder = (AdHolder) holder;
            adHolder.adChoicesContainer.removeAllViews();
            NativeAd ad = (NativeAd) categories.get(position);
            adHolder.showAds(ad);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateData(List<HomePage.CategoriesItem> categories) {
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
        if (categories.get(position) instanceof UnifiedNativeAd) {
            return AD_TYPE;
        } else if (categories.get(position) instanceof NativeAd) {
            return AD_FB_TYPE;
        } else {
            return POST_TYPE;
        }
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
        }
    }
}
