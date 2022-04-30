package com.thequizapp.quizalong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemPopularQuizesBinding;
import com.thequizapp.quizalong.model.home.HomePage;

import java.util.ArrayList;
import java.util.List;

import static com.thequizapp.quizalong.api.Const.POST_TYPE;

public class QuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomePage.QuizesItem> quizes = new ArrayList<>();
    private OnItemClicks onItemClicks;
    private boolean showAll = false;

    public QuizesAdapter() {
    }
    public QuizesAdapter(boolean showAll) {
        this.showAll = showAll;
    }

    public List<HomePage.QuizesItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<HomePage.QuizesItem> quizes) {
        this.quizes = quizes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_quizes, parent, false);
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
        return showAll ? quizes.size(): 3;
    }

    public void updateData(List<HomePage.QuizesItem> quizes) {
        this.quizes.addAll(quizes);
        notifyDataSetChanged();
    }


    public void setFullLength(boolean showAll) {
        this.showAll = showAll;
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
        void onClick(HomePage.QuizesItem quizesItem);
    }

    public class PopularQuizesViewHolder extends RecyclerView.ViewHolder {
        ItemPopularQuizesBinding binding;

        public PopularQuizesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            if(quizes.size() > position) {
                if (quizes.get(position) != null) {
                    HomePage.QuizesItem quizesItem = quizes.get(position);
                    binding.getRoot().setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                    binding.setModel(quizesItem);

                }
            }
        }
    }
}
