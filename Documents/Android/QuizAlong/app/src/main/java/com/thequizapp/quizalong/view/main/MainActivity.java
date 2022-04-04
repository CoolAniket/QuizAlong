package com.thequizapp.quizalong.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.adapter.MainViewPagerAdapter;
import com.thequizapp.quizalong.databinding.ActivityMainBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.leaderboard.LeaderBoardActivity;
import com.thequizapp.quizalong.view.notification.NotificationActivity;
import com.thequizapp.quizalong.view.playhistory.PlayHistoryActivity;
import com.thequizapp.quizalong.view.redeem.HistoryRedeemRequestActivity;
import com.thequizapp.quizalong.view.web.WebViewActivity;
import com.thequizapp.quizalong.viewmodel.MainViewModel;

public class  MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    private ImageView lastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initView() {
        viewModel.setUser(new SessionManager(this).getUser());
        lastView = binding.ivHome;
        setSelect(0);
        initViewPager();
    }

    private void initViewPager() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("TAG", "");
            }

            @Override
            public void onPageSelected(int position) {
                viewModel.onClickMenu(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("TAG", "");
            }
        });
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(mainViewPagerAdapter);
    }

    private void initObserve() {
        viewModel.getMutableSelectedMenu().observe(this, this::setSelect);
    }

    private void initListener() {
        initDrawerListener();
        binding.ivMenu.setOnClickListener(v -> binding.drawer.openDrawer(GravityCompat.START));
    }

    private void initDrawerListener() {
        binding.navDrawer.lytLeaderBoard.setOnClickListener(v -> startActivity(new Intent(this, LeaderBoardActivity.class)));
        binding.navDrawer.lytPlayHistory.setOnClickListener(v -> startActivity(new Intent(this, PlayHistoryActivity.class)));
        binding.navDrawer.lytRedeemRequest.setOnClickListener(v -> startActivity(new Intent(this, HistoryRedeemRequestActivity.class)));
        binding.navDrawer.lytUpdate.setOnClickListener(v -> startActivity(new Intent(this, NotificationActivity.class)));
        binding.navDrawer.lytPrivacy.setOnClickListener(v -> startActivity(new Intent(this, WebViewActivity.class).putExtra("type", 0)));
        binding.navDrawer.lytTerms.setOnClickListener(v -> startActivity(new Intent(this, WebViewActivity.class).putExtra("type", 1)));
    }

    private void setSelect(int position) {
        binding.viewPager.setCurrentItem(position, false);
        if (lastView != null) {
            lastView.setScaleY(1);
            lastView.setScaleX(1);
        }
        ImageView imageView;
        if (position == 0) imageView = binding.ivHome;
        else imageView = position == 1 ? binding.ivCat : binding.ivProfile;
        Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.pop);
        expandIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("TAG", "");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setScaleX(1.1f);
                imageView.setScaleY(1.1f);
                lastView = imageView;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("TAG", "");
            }
        });
        imageView.startAnimation(expandIn);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else if (!viewModel.isBack) {
            viewModel.isBack = true;
            Toast.makeText(this, "Press Again To Exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> viewModel.isBack = false, 2000);
        } else {
            super.onBackPressed();
        }
    }
}