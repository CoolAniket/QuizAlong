package com.retrytech.quizbox.view.quiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityQuizBinding;
import com.retrytech.quizbox.model.home.HomePage;
import com.retrytech.quizbox.utils.CustomDialogBuilder;
import com.retrytech.quizbox.utils.SessionManager;
import com.retrytech.quizbox.utils.ads.BannerAds;
import com.retrytech.quizbox.utils.ads.InterstitialAds;
import com.retrytech.quizbox.utils.ads.RewardAds;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.viewmodel.QuizViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QuizActivity extends BaseActivity implements Runnable {
    ActivityQuizBinding binding;
    QuizViewModel viewModel;
    SessionManager sessionManager;
    Handler handler = new Handler(Looper.getMainLooper());
    private InterstitialAds interstitialAds;
    private RewardAds rewardAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        initData();
        initView();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initData() {
        sessionManager = new SessionManager(this);
        viewModel.setQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), HomePage.QuizesItem.class));
        viewModel.getQuestionsByQuizId();
    }

    private void initView() {
        new BannerAds(QuizActivity.this, binding.bannerAds);
        interstitialAds = new InterstitialAds(QuizActivity.this);

        rewardAds = new RewardAds(QuizActivity.this);
        if (viewModel.getQuizesItem().getType() != 0) {

            new CustomDialogBuilder(this).showQuizTypeDialog(viewModel.getQuizesItem().getType() == 1, new CustomDialogBuilder.OnQuitTypeListener() {
                @Override
                public void onCancelDismiss() {
                    finish();
                }

                @Override
                public void onStartDismiss() {
                    if (viewModel.getQuizesItem().getType() == 1) {
                        startCountDown();

                    }
                }
            });
        }
    }

    private void startCountDown() {
        handler.removeCallbacks(this);
        if (viewModel.getQuizesItem().getType() == 1) {
            viewModel.getRapidFireDuration().set(sessionManager.getRapidFireTime());
            handler.postDelayed(this, 1000);
        }
    }

    private void initListener() {
        binding.ivLifeLine.setOnClickListener(v -> {
            if (!viewModel.isUseLifeLineInCurrentQue()) {
                new CustomDialogBuilder(this).showLifeLineDialog(viewModel.isUseDoubleDeep(), viewModel.isUseFiftyFifty(), new CustomDialogBuilder.OnLifeLineListener() {
                    @Override
                    public void onDoubleDipClick() {
                        handler.postDelayed(QuizActivity.this, 1000);
                        viewModel.setDoubleDip(true);
                        viewModel.setUseDoubleDeep(true);
                    }

                    @Override
                    public void onFiftyFiftyClick() {
                        handler.postDelayed(QuizActivity.this, 1000);
                        viewModel.setUseFiftyFifty(true);
                        viewModel.hide2WrongAnswer();
                    }

                    @Override
                    public void onDismissClick() {
                        handler.postDelayed(QuizActivity.this, 1000);
                    }
                });
                viewModel.setUseLifeLineInCurrentQue(true);
            }
            handler.removeCallbacks(this);
        });
        binding.tvCompleted.setOnClickListener(v -> onBackPressed());
        binding.tvExit.setOnClickListener(v -> onBackPressed());
        binding.lyt2X.setOnClickListener(v -> {
            rewardAds.setOnRewarded(() -> {
                viewModel.getTotalScore().set(viewModel.getTotalScore().get() * 2);
                onBackPressed();
            });
            rewardAds.showAds();
        });
        binding.lytShare.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
            } else {
                saveBitMap(this, binding.lytSs);
            }
        });
    }

    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            boolean isCreate = pictureFile.createNewFile();
            Log.d("TAG", "saveBitMap: " + isCreate);
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(ContextCompat.getColor(this, R.color.purple));
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, (path1, uri) -> {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I completed this quiz, and had a lots of fun. Play and have fun in this app.https://play.google.com/store/apps/details?id=" + getPackageName());
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "send"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initObserve() {
        viewModel.getCurrentQuestions().observe(this, questionsItem -> {
            viewModel.setAnswerList(questionsItem.getAnswerList());
            binding.setQuestion(questionsItem);
            binding.setAnswerList(viewModel.getAnswerList());
        });
        viewModel.getIsAnswer().observe(this, this::showResultDialog);
    }

    private void showResultDialog(Boolean isAnswerRight) {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, isAnswerRight ? R.raw.true_ : R.raw.false_);
        mediaPlayer.start();
        if (viewModel.getCurrentQuestions().getValue() != null) {
            viewModel.setUseLifeLineInCurrentQue(false);
            handler.removeCallbacks(this);
            new CustomDialogBuilder(this)
                    .showAnswerResultDialog(isAnswerRight != null && isAnswerRight,
                            isAnswerRight != null && isAnswerRight ? String.valueOf(viewModel.getCurrentQuestions().getValue().getReward()) : viewModel.getCurrentQuestions().getValue().getTrueAns(),
                            () -> binding.rtlMain.animate().withLayer()
                                    .rotationY(90)
                                    .setDuration(150)
                                    .withEndAction(
                                            () -> {
                                                viewModel.getTrueAnswerPosition().set(-1);
                                                viewModel.getWrongAnswerPosition().set(-1);
                                                viewModel.showAllAnswers();
                                                if (isAnswerRight != null && isAnswerRight) {
                                                    viewModel.getTotalScore().set(viewModel.getTotalScore().get() + viewModel.getCurrentQuestions().getValue().getReward());
                                                } else {
                                                    if (viewModel.getQuizesItem().getType() == 2) {
                                                        viewModel.getTotalScore().set(viewModel.getTotalScore().get() - (viewModel.getCurrentQuestions().getValue().getReward() * 2));
                                                    }
                                                }
                                                addScore();
                                            }
                                    ).start());
        }
    }

    private void addScore() {
        if (viewModel.getQuestionsList().size() > viewModel.getCurrentPosition().get()) {
            viewModel.getCurrentQuestions().setValue(viewModel.getQuestionsList().get(viewModel.getCurrentPosition().get()));
            viewModel.getCurrentPosition().set(viewModel.getCurrentPosition().get() + 1);
            startCountDown();
        } else {
            handler.removeCallbacks(this);
            viewModel.getIsComplete().set(true);

        }
        binding.rtlMain.setRotationY(-90);
        binding.rtlMain.animate().withLayer()
                .rotationY(0)
                .setDuration(150)
                .start();
    }

    @Override
    public void run() {
        viewModel.getRapidFireDuration().set(viewModel.getRapidFireDuration().get() - 1);
        if (viewModel.getRapidFireDuration().get() == 0) {
            viewModel.timesUp();
            handler.removeCallbacks(this);
        } else {
            handler.postDelayed(this, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewModel.getIsComplete().get()) {
            viewModel.addPointsToWallet();
            if (interstitialAds != null) {
                interstitialAds.showAds();
            }
            super.onBackPressed();
        } else {
            handler.removeCallbacks(this);
            new CustomDialogBuilder(this).
                    showSimpleDialog(R.drawable.ic_warning,
                            "Do you really,",
                            "want to quit quiz ?",
                            "Yes",
                            "Cancel",
                            new CustomDialogBuilder.OnDismissListener() {
                                @Override
                                public void onPositiveDismiss() {
                                    QuizActivity.super.onBackPressed();
                                }

                                @Override
                                public void onNegativeDismiss() {
                                    handler.postDelayed(QuizActivity.this, 1000);
                                }
                            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(this);
    }
}