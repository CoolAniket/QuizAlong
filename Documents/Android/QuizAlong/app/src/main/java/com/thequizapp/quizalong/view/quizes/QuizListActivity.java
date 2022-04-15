package com.thequizapp.quizalong.view.quizes;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.QuizListViewPagerAdapter;
import com.thequizapp.quizalong.databinding.ActivityQuizListBinding;
import com.thequizapp.quizalong.model.categories.CategoriesResponse;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.QuizListViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class QuizListActivity extends BaseActivity {
    ActivityQuizListBinding binding;
    QuizListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_list);
        viewModel = new ViewModelProvider(this).get(QuizListViewModel.class);
        binding.tvTitle.setTransitionName(getIntent().getStringExtra("name"));
        binding.ivLogo.setTransitionName(getIntent().getStringExtra("logo"));
        initView();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {



        String catStr = getIntent().getStringExtra("data");
        viewModel.setCategoriesItem(new Gson().fromJson(catStr, CategoriesResponse.Category.class));
        viewModel.getQuizesByCatId();
    }

    private void initListener() {
        viewModel.getUpcomingQuizes().observe(this, quizByCatId -> {
            QuizListViewPagerAdapter mainViewPagerAdapter = new QuizListViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            mainViewPagerAdapter.setQuizListData(quizByCatId);
            binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.d("TAG", "");
                }

                @Override
                public void onPageSelected(int position) {
                    binding.viewPager.setCurrentItem(position);
//                viewModel.onClickMenu(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.d("TAG", "");
                }
            });
//            binding.viewPager.setOffscreenPageLimit(2);
            binding.viewPager.setAdapter(mainViewPagerAdapter);
            binding.tabLayout.setupWithViewPager(binding.viewPager);

        });
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

}