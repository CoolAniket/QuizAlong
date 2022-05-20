package com.thequizapp.quizalong.adapter;

import static com.thequizapp.quizalong.api.Const.POST_TYPE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemPastQuizesBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.List;

public class PastQuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> quizes = new ArrayList<>();
    public ObservableBoolean loadFullList = new ObservableBoolean(true);
    public ObservableInt itemCountObserve = new ObservableInt(0);
    private OnItemClicks onItemClicks;

    public List<Object> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Object> quizes) {
        this.quizes = quizes;
        itemCountObserve.set(quizes.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_quizes, parent, false);
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
//        return quizes.size();
        return loadFullList.get() ? quizes.size() : Math.min(quizes.size(), 3);
    }

    public void updateData(List<QuizItem> quizes, boolean loadFullList) {
        this.loadFullList.set(loadFullList);
        this.quizes.clear();
        this.quizes.addAll(quizes);
        itemCountObserve.set(quizes.size());
        notifyDataSetChanged();
    }

    public void switchLoadFullList() {
        loadFullList.set(!loadFullList.get());
        notifyDataSetChanged();
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
        void onClick(QuizItem quizesItem);
    }

    public class PopularQuizesViewHolder extends RecyclerView.ViewHolder {
        ItemPastQuizesBinding binding;

        public PopularQuizesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            if(quizes.size() > 0) {
                if (quizes.get(position) instanceof QuizItem) {
                    QuizItem quizesItem = (QuizItem) quizes.get(position);
                    binding.btnStartNow.setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                    binding.setModel(quizesItem);

                }
            }
        }
    }
}
