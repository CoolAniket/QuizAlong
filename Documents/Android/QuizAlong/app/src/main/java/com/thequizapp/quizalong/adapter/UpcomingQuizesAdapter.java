package com.thequizapp.quizalong.adapter;

import static com.thequizapp.quizalong.api.Const.POST_TYPE;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemUpcomingQuizesBinding;
import com.thequizapp.quizalong.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.List;

public class UpcomingQuizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> quizes = new ArrayList<>();
    public ObservableBoolean loadFullList = new ObservableBoolean(true);
    public ObservableInt itemCountObserve = new ObservableInt(0);

    public List<Object> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<Object> quizes) {
        this.quizes = quizes;
        itemCountObserve.set(quizes.size());
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_quizes, parent, false);
        return new UpcomingQuizViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UpcomingQuizViewHolder) {
            UpcomingQuizViewHolder viewHolder = (UpcomingQuizViewHolder) holder;
            viewHolder.setModel(position);
        }
    }

    @Override
    public int getItemCount() {
        return loadFullList.get() ? quizes.size() : Math.min(quizes.size(), 3);
    }

    /*public void updateData(List<HomePage.CategoriesItem> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }*/

    public void updateData(List<QuizItem> categories, boolean loadFullList) {
        this.loadFullList.set(loadFullList);
        itemCountObserve.set(categories.size());
        this.quizes.clear();
        this.quizes.addAll(categories);
        notifyDataSetChanged();
    }
    public void switchLoadFullList() {
        loadFullList.set(!loadFullList.get());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return POST_TYPE;
    }


    public interface OnItemClick {
        void onClick(Pair<View, String>[] pair, QuizItem categoriesItem);
    }

    public class UpcomingQuizViewHolder extends RecyclerView.ViewHolder {
        ItemUpcomingQuizesBinding binding;

        public UpcomingQuizViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setModel(int position) {
            if(quizes.size() > 0) {
                if (quizes.get(position) instanceof QuizItem) {
                    QuizItem categoriesItem = (QuizItem) quizes.get(position);
                    binding.tvTitle.setTransitionName("hero" + position);
                    /*binding.ivIcon.setTransitionName("hero1" + position);*/
                    binding.btnCheck.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[1];
                        pairs[0] = new Pair<>(binding.tvTitle, "Free");
                        onItemClick.onClick(pairs, categoriesItem);
                    });
                    binding.btnPay.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[3];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[2] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        onItemClick.onClick(pairs, categoriesItem);
                    });
                    binding.btnEnrolled.setOnClickListener(v -> {
                        Pair<View, String>[] pairs = new Pair[2];
                        pairs[0] = new Pair<>(binding.tvTitle, binding.tvTitle.getTransitionName());
                        pairs[1] = new Pair<>(binding.ivIcon, binding.ivIcon.getTransitionName());
                        onItemClick.onClick(pairs, categoriesItem);
                    });
//                    binding.getRoot().setOnClickListener(v -> binding.btnCheck.performClick());
                    binding.setModel(categoriesItem);
                }
            }
        }
    }
}
