package com.thequizapp.quizalong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.model.results.ShowResultsRequest;

import java.util.ArrayList;
import java.util.List;

public class ShowResultsAdapter extends PagerAdapter {

    private Context mContext;
    private List<Object> questions = new ArrayList<>();
    private List<Object> answers = new ArrayList<>();

    public List<Object> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Object> questions) {
        this.questions = questions;
    }

    public List<Object> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Object> answers) {
        this.answers = answers;
    }

    public ShowResultsAdapter() {

    }
    public ShowResultsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return this.questions.size();
    }

    public void updateData(List<ShowResultsRequest.Question> questions, List<ShowResultsRequest.UserAnswer> answers) {
        this.questions.addAll(questions);
        this.answers.addAll(answers);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
