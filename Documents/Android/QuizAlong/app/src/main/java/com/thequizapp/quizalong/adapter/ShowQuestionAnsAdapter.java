package com.thequizapp.quizalong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ItemPastQuizesBinding;
import com.thequizapp.quizalong.databinding.QuestionAnswerBinding;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.model.results.ShowResultsRequest;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionAnsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> questions = new ArrayList<>();
    private List<Object> answers = new ArrayList<>();

    public ShowQuestionAnsAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_answer, parent, false);
        return new ShowQuestionAnsAdapter.ShowQuestionAnsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShowQuestionAnsAdapter.ShowQuestionAnsViewHolder) {
            ShowQuestionAnsAdapter.ShowQuestionAnsViewHolder viewHolder = (ShowQuestionAnsAdapter.ShowQuestionAnsViewHolder) holder;
            viewHolder.setModel(position);
        }
    }

    @Override
    public int getItemCount() {
        return this.questions.size();
    }

    public ShowQuestionAnsAdapter(Context context) {
        mContext = context;
    }



    public void updateData(ShowResultsRequest showResultsRequest) {
        this.questions.addAll(showResultsRequest.getQuestions());
        this.answers.addAll(showResultsRequest.getUserAnswers());
        notifyDataSetChanged();
    }

    public class ShowQuestionAnsViewHolder extends RecyclerView.ViewHolder {
        QuestionAnswerBinding binding;

        public ShowQuestionAnsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

        }

        public void setModel(int position) {
            /*if(quizes.size() > 0) {
                if (quizes.get(position) instanceof TwistQuizPage.Quize) {
                    TwistQuizPage.Quize quizesItem = (TwistQuizPage.Quize) quizes.get(position);
                    binding.getRoot().setOnClickListener(v -> onItemClicks.onClick(quizesItem));
                    binding.setModel(quizesItem);

                }
            }*/
            ShowResultsRequest.Question queItem = (ShowResultsRequest.Question) questions.get(position);
            ShowResultsRequest.UserAnswer ansItem = (ShowResultsRequest.UserAnswer) answers.get(position);
            binding.setAnsmodel(ansItem);
            binding.setModel(queItem);
        }
    }
}
