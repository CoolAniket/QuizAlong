package com.thequizapp.quizalong.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemUpcomingQuizesBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.List;

import static com.thequizapp.quizalong.api.Const.POST_TYPE;

public class QuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<QuizItem> quizes = new ArrayList<>();
    private OnItemClick onItemClick;
    private boolean showAll = false;

    public QuizesAdapter() {
    }
    public QuizesAdapter(boolean showAll) {
        this.showAll = showAll;
    }

    public List<QuizItem> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuizItem> quizes) {
        this.quizes = quizes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_quizes, parent, false);
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

    public void updateData(List<QuizItem> quizes) {
        this.quizes.addAll(quizes);
        notifyDataSetChanged();
    }


    public void setFullLength(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    public int getItemViewType(int position) {
        return POST_TYPE;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onClick(Pair<View, String>[] pair, QuizItem categoriesItem);
    }

    public class PopularQuizesViewHolder extends RecyclerView.ViewHolder {
        ItemUpcomingQuizesBinding binding;

        public PopularQuizesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            if(quizes.size() > position) {
                if (quizes.get(position) != null) {
                    QuizItem quizesItem = quizes.get(position);
                    binding.btnCheck.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[2];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        onItemClick.onClick(pairs, quizesItem);
                    });
                    binding.btnPay.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[3];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        pairs[2] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        onItemClick.onClick(pairs, quizesItem);
                    });
//                    binding.getRoot().setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                    binding.btnEnrolled.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[2];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        onItemClick.onClick(pairs, quizesItem);
                    });
                    binding.setModel(quizesItem);

                }
            }
        }
    }
}
