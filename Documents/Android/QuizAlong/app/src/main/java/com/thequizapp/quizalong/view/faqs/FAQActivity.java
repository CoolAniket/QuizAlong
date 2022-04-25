package com.thequizapp.quizalong.view.faqs;

import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityFaqBinding;
import com.thequizapp.quizalong.view.BaseActivity;


public class FAQActivity extends BaseActivity {

    ActivityFaqBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq);

        binding.firstAnswer.setText(getString(R.string.faq_a_1));
        binding.secondAnswer.setText(getString(R.string.faq_a_2));
        binding.thirdAnswer.setText(getString(R.string.faq_a_3));
        binding.fourthAnswer.setText(getString(R.string.faq_a_4));
        binding.fifthAnswer.setText(getString(R.string.faq_a_5));
        binding.sixthAnswer.setText(getString(R.string.faq_a_6));
        binding.sevenAnswer.setText(getString(R.string.faq_a_7));
        binding.eightAnswer.setText(getString(R.string.faq_a_8));
        binding.nineAnswer.setText(getString(R.string.faq_a_9));
        binding.tenAnswer.setText(getString(R.string.faq_a_10));
        binding.elevenAnswer.setText(getString(R.string.faq_a_11));
//        binding.firstAnswer.ex(true);
//        binding.firstAnswer.setDrawableExpand(ContextCompat.getDrawable(this, R.drawable.ic_action_up));

        initListener();
    }
    private void initListener() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

}