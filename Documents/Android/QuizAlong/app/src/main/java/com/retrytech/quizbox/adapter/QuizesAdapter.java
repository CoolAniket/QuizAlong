package com.retrytech.quizbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ItemPopularQuizesBinding;
import com.retrytech.quizbox.model.home.HomePage;

import java.util.ArrayList;
import java.util.List;

import static com.retrytech.quizbox.api.Const.AD_FB_TYPE;
import static com.retrytech.quizbox.api.Const.AD_TYPE;
import static com.retrytech.quizbox.api.Const.POST_TYPE;

public class QuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> quizes = new ArrayList<>();
    private OnItemClicks onItemClicks;

    public List<Object> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Object> quizes) {
        this.quizes = quizes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == AD_TYPE) {

            View unifiedNativeLayoutView = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.admob_quiz,
                    parent, false);
            return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);

        } else if (viewType == AD_FB_TYPE) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.fb_quiz, parent, false);
            return new AdHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_quizes, parent, false);
            return new PopularQuizesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularQuizesViewHolder) {
            PopularQuizesViewHolder viewHolder = (PopularQuizesViewHolder) holder;
            viewHolder.setModel(position);
        } else if (holder instanceof UnifiedNativeAdViewHolder) {
            UnifiedNativeAdViewHolder viewHolder = (UnifiedNativeAdViewHolder) holder;
            UnifiedNativeAd nativeAd = (UnifiedNativeAd) quizes.get(position);
            viewHolder.populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
        } else if (holder instanceof AdHolder) {
            AdHolder adHolder = (AdHolder) holder;
            adHolder.adChoicesContainer.removeAllViews();
            NativeAd ad = (NativeAd) quizes.get(position);
            adHolder.showAds(ad);
        }
    }

    @Override
    public int getItemCount() {
        return quizes.size();
    }

    public void updateData(List<HomePage.QuizesItem> quizes) {
        this.quizes.addAll(quizes);
        notifyDataSetChanged();
    }

    public void addNewAds(int index, UnifiedNativeAd ad) {
        if (!quizes.isEmpty() && index < quizes.size()) {
            quizes.add(index, ad);
            notifyItemInserted(index);
        }
    }

    public void addFBAds(int index, NativeAd nextNativeAd) {
        if (!quizes.isEmpty() && index < quizes.size()) {
            quizes.add(index, nextNativeAd);
            notifyItemInserted(index);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (quizes.get(position) instanceof UnifiedNativeAd) {
            return AD_TYPE;
        } else if (quizes.get(position) instanceof NativeAd) {
            return AD_FB_TYPE;
        } else {
            return POST_TYPE;
        }
    }

    public void setOnItemClicks(OnItemClicks onItemClicks) {
        this.onItemClicks = onItemClicks;
    }

    public interface OnItemClicks {
        void onClick(HomePage.QuizesItem quizesItem);
    }

    public class PopularQuizesViewHolder extends RecyclerView.ViewHolder {
        ItemPopularQuizesBinding binding;

        public PopularQuizesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            if (quizes.get(position) instanceof HomePage.QuizesItem) {
                HomePage.QuizesItem quizesItem = (HomePage.QuizesItem) quizes.get(position);
                binding.getRoot().setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                binding.setModel(quizesItem);

            }
        }
    }
}
