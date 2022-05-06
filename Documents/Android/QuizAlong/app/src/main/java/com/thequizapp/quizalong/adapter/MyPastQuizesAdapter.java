package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemPastQuizesBinding;
import com.thequizapp.quizalong.databinding.ItemPastQuizesScoreBinding;
import com.thequizapp.quizalong.model.home.TwistQuizPage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.thequizapp.quizalong.api.Const.POST_TYPE;

public class MyPastQuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_quizes_score, parent, false);
        return new PopularQuizesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularQuizesViewHolder) {
            PopularQuizesViewHolder viewHolder = (PopularQuizesViewHolder) holder;
            viewHolder.setModel(position);
        }
    }

    @Override
    public int getItemCount() {
        /*return 3;*/
        return quizes.size();
    }

    public void updateData(List<TwistQuizPage.QuizItem> quizes) {
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
        /*if (quizes.get(position) instanceof UnifiedNativeAd) {
            return AD_TYPE;
        } else if (quizes.get(position) instanceof NativeAd) {
            return AD_FB_TYPE;
        } else {
            return POST_TYPE;
        }*/
        return POST_TYPE;
    }

    public void setOnItemClicks(OnItemClicks onItemClicks) {
        this.onItemClicks = onItemClicks;
    }

    public interface OnItemClicks {
        void onClick(TwistQuizPage.QuizItem quizesItem);
    }

    public class PopularQuizesViewHolder extends RecyclerView.ViewHolder {
        ItemPastQuizesScoreBinding binding;

        public PopularQuizesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            if(quizes.size() > 0) {
                if (quizes.get(position) instanceof TwistQuizPage.QuizItem) {
                    TwistQuizPage.QuizItem quizesItem = (TwistQuizPage.QuizItem) quizes.get(position);
                    binding.getRoot().setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                    binding.setModel(quizesItem);

                }
            }
        }
    }
}
