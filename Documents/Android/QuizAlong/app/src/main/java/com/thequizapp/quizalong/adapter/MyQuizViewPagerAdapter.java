package com.thequizapp.quizalong.adapter;

import com.thequizapp.quizalong.model.quiz.QuizByCatId;
import com.thequizapp.quizalong.view.quizes.PastQuizFragment;
import com.thequizapp.quizalong.view.quizes.PastQuizScoreFragment;
import com.thequizapp.quizalong.view.quizes.UpcomingQuizFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyQuizViewPagerAdapter extends FragmentPagerAdapter {
    private QuizByCatId quizByCatId = new QuizByCatId();

    public MyQuizViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new UpcomingQuizFragment(quizByCatId.getUpcomingQuizes());
        } else {
            return new PastQuizScoreFragment(quizByCatId.getPastQuizes());
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Upcoming Quiz";
        }
        else
        {
            title = "Past Quiz";
        }

        return title;
    }

    public void setQuizListData(QuizByCatId quizByCatId){
        this.quizByCatId = quizByCatId;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
