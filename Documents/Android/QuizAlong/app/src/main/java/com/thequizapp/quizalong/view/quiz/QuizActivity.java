package com.thequizapp.quizalong.view.quiz;

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
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityQuizBinding;
import com.thequizapp.quizalong.model.home.HomePage;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.utils.ads.InterstitialAds;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.login.AdditionalInfoActivity;
import com.thequizapp.quizalong.viewmodel.QuizViewModel;

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
    private CountDownTimer cTimer = null;
    private CountDownTimer lTimer = null;
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
        //viewModel.setQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), HomePage.QuizesItem.class));
        viewModel.setTwistQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), TwistQuizPage.Quize.class));
        viewModel.getQuestionsByQuizId();
    }

    private void initView() {
        new BannerAds(QuizActivity.this, binding.bannerAds);
        interstitialAds = new InterstitialAds(QuizActivity.this);

        rewardAds = new RewardAds(QuizActivity.this);
        /*if (viewModel.getQuizesItem().getType() != 0) {

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
        }*/
    }

    private void startCountDown() {

        handler.removeCallbacks(this);
        /*if (viewModel.getQuizesItem().getType() == 1) {
            viewModel.getRapidFireDuration().set(sessionManager.getRapidFireTime());
            handler.postDelayed(this, 1000);
        }*/

        if (viewModel.getIsLobby().get()) {
            if (cTimer != null)
                cTimer.cancel();
            cTimer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);
                    viewModel.getTimeRemaining().set((int) (millisUntilFinished / 1000));
                }

                public void onFinish() {
                    Log.e("omFinish ", "");
                    addScore(true);
                }
            };
            cTimer.start();
        }
    }

    private void startLobbyTimer(){
        if (viewModel.getIsInfo().get()) {
            if (lTimer != null)
                lTimer.cancel();
            lTimer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.e("l seconds remaining: ", "" + millisUntilFinished / 1000);
                    viewModel.getLobbyTimeRemaining().set((int) (millisUntilFinished / 1000));
                }

                public void onFinish() {
                    Log.e("lobby omFinish ", "");
                    viewModel.getIsLobby().set(true);
                    startCountDown();
                }
            };
            lTimer.start();
        }
    }
    private void initListener() {
        binding.ivLifeLine.setOnClickListener(v -> {
            if (!viewModel.isUseLifeLineInCurrentQue()) {
                new CustomDialogBuilder(this).showLifeLineDialog(viewModel.isUseDoubleDeep(), viewModel.isUseFiftyFifty(), viewModel.isUseSkip(), new CustomDialogBuilder.OnLifeLineListener() {
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
                    public void onSkipClick() {
                        handler.postDelayed(QuizActivity.this, 1000);
                        viewModel.setUseSkip(true);
                        viewModel.skipQuestion();
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
        binding.tvCancel.setOnClickListener(v -> onBackPressed());
        binding.btnGo.setOnClickListener(v -> {
            viewModel.getIsInfo().set(true);
            startLobbyTimer();
        });
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
            viewModel.setAnswerList(questionsItem.getOptions());
            binding.setQuestion(questionsItem);
            binding.setAnswerList(viewModel.getAnswerList());

        });
        //viewModel.getIsAnswer().observe(this, this::showResultDialog);

        viewModel.getAnswerVal().observe(this, this::showResultDialog);

        viewModel.getOnSuccess().observe(this, AddDataResponse -> {

            Log.e("....",""+AddDataResponse);
            Toast.makeText(this, getResources().getString(R.string.live_data_successfully), Toast.LENGTH_SHORT).show();
            viewModel.getIsComplete().set(true);
        });
    }

    /*private void showResultDialog(Boolean isAnswerRight) {*/
        private void showResultDialog(String answer) {
        /*MediaPlayer mediaPlayer = MediaPlayer.create(this, isAnswerRight ? R.raw.true_ : R.raw.false_);
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
        }*/
        Log.e(">>> ",answer);
            cTimer.cancel();
        viewModel.setUseLifeLineInCurrentQue(false);
        handler.removeCallbacks(this);

        addScore(false);
    }

    private void addScore(boolean isTimerOff) {

        Log.e("???? ",viewModel.getCurrentPosition().get()+""+viewModel.getAnswerVal().getValue());
        if (viewModel.getQuestionsList().size() > viewModel.getCurrentPosition().get()) {
            viewModel.createGameHashMap(viewModel.getCurrentPosition().get(),isTimerOff);
            viewModel.getCurrentQuestions().setValue(viewModel.getQuestionsList().get(viewModel.getCurrentPosition().get()));
            viewModel.getCurrentPosition().set(viewModel.getCurrentPosition().get() + 1);
            startCountDown();
        } else {
            handler.removeCallbacks(this);
            viewModel.getIsComplete().set(true);
            viewModel.callAddGameDataLiveApi();
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
            if(cTimer != null)
                cTimer.cancel();
            if(lTimer != null)
                lTimer.cancel();
            super.onBackPressed();
        } else {
            if(cTimer != null)
                cTimer.cancel();
            if(lTimer != null)
                lTimer.cancel();
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