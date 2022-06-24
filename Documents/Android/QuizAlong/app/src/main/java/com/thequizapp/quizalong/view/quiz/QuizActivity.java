package com.thequizapp.quizalong.view.quiz;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityQuizBinding;
import com.thequizapp.quizalong.model.quiz.LobbyMessageResponse;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.receivers.GameStartReceiver;
import com.thequizapp.quizalong.utils.CustomDialogBuilder;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.utils.ads.InterstitialAds;
import com.thequizapp.quizalong.utils.ads.RewardAds;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.leaderboard.LeaderBoardActivity;
import com.thequizapp.quizalong.view.results.ShowQuizAnswersActivity;
import com.thequizapp.quizalong.viewmodel.QuizViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuizActivity extends BaseActivity implements Runnable {
    public final class Type {
        public static final String PAST = "past";
        public static final String UPCOMING = "upcoming";
        public static final String TWIST = "twist";

        private Type() { }
    }
    ActivityQuizBinding binding;
    QuizViewModel viewModel;
    SessionManager sessionManager;
    Handler handler = new Handler(Looper.getMainLooper());
    private InterstitialAds interstitialAds;
    private RewardAds rewardAds;
    private CountDownTimer cTimer = null;
    private CountDownTimer lTimer = null;
    private CountDownTimer slideTimer = null;
    private int tipCnt =0;
    private List<LobbyMessageResponse.QuizItem> lobbyMessagesList = new ArrayList<>();
    private static final String[] TIPS = new String[] {
            "You can increase your chance of winning significantly if you play in a group of 2 or more.",
            "SKIP lifeline helps you to directly pass the question as correct.",
            "SKIP lifeline can only be used once per quiz.",
            "You and your referred friend can 1 earn “SKIP” lifeline with every 1 successful referral.",
            "You can always change your interested categories under categories section on home page.",
            "Usually its not a good idea to select options like Always do, Never possible.",
            "Excluding wrong answers can definitely help your narrow down your options.",
            "All the quiz answers are backed by latest guidelines and involved practical case scanerios.",
            "Grand quizes happpens over weekends having grand winnings of upto 1000 coins in one quiz.",
            "You can always plan your quizes ahead by looking at upcoming quizzes of your subject"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        initData();
        initView();
        initObserve();
        initListener();
        //initReceiver();
        binding.setViewModel(viewModel);
    }

    private void initData() {

        sessionManager = new SessionManager(this);
        //viewModel.setQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), QuizItem.class));
        viewModel.setTwistQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), QuizItem.class));
        viewModel.getQuestionsByQuizId();
        viewModel.getLobbyMessages();
        Random r=new Random();
        //int randomNumber=r.nextInt(TIPS.length);
        binding.tvTip.setText(TIPS[tipCnt]);
        if(getIntent().getStringExtra("quiz_type").contains("past")){
            //binding.payTxt.setVisibility(View.INVISIBLE);
            binding.tvQuiztype.setText("FREE");
            binding.ivLifeLine.setVisibility(View.INVISIBLE);
        }else{
            binding.payTxt.setVisibility(View.VISIBLE);
            binding.tvQuiztype.setText("");
            binding.ivLifeLine.setVisibility(View.VISIBLE);
        }
        viewModel.setQuizType(getIntent().getStringExtra("quiz_type"));
    }

    private void slideTextWithTime(List<LobbyMessageResponse.QuizItem> messages,String[] TIPS){


        if (slideTimer != null)
            slideTimer.cancel();
        slideTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining: ", "slide" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                Log.e("omFinish ", "");
                if(messages.size() > 0) {
                    if (tipCnt >= (messages.size() - 1)) {
                        tipCnt = 0;
                    } else {
                        tipCnt++;
                    }
                    binding.tvTip.setText(messages.get(tipCnt).getMessage());
                    Log.e("UUUUU ",messages.get(tipCnt).getMessage());

                }else {
                    if (tipCnt >= (TIPS.length - 1)) {
                        tipCnt = 0;
                    } else {
                        tipCnt++;
                    }
                    binding.tvTip.setText(TIPS[tipCnt]);
                }
                slideTextWithTime(messages,TIPS);
            }
        };
        slideTimer.start();
    }

    private void initReceiver() {

        Intent myIntent = new Intent(getBaseContext(),
                GameStartReceiver.class);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(getBaseContext(),
                0, myIntent, 0);

        AlarmManager alarmManager
                = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, 20);
        calSet.set(Calendar.MINUTE, 37);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
    }

    private void initView() {
        Log.e("LOBBYYY "," ++ "+lobbyMessagesList);
        new BannerAds(QuizActivity.this, binding.bannerAds);
        interstitialAds = new InterstitialAds(QuizActivity.this);

        rewardAds = new RewardAds(QuizActivity.this);
        /*if (viewModel.getQuizesItem().getType() != 0) {

            new CustomDialogBuilder(this).showQuizTypeDialog(viewModel.getQuizesItem().getType() == 1, new CustomDialogBuilder.OnQuitTypeListener() {
        if (viewModel.getQuizesItem().getType() != null) {
            new CustomDialogBuilder(this).showQuizTypeDialog(true, new CustomDialogBuilder.OnQuitTypeListener() {
                @Override
                public void onCancelDismiss() {
                    finish();
                }

                @Override
                public void onStartDismiss() {
//                    if (viewModel.getQuizesItem().getType() == 1) {
                        startCountDown();

//                    }
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
                    //Log.e("omFinish ", ""+viewModel.getIsAnswer().getValue());
                    if(viewModel.isUseOnce()){
                        viewModel.showAllAnswers();
                        viewModel.setUseOnce(false);
                        addScore(false,"skip");
                    } else {
                        addScore(viewModel.getIsAnswer().getValue() == null, "");
                    }
                }
            };
            cTimer.start();
        }
        //viewModel.getRapidFireDuration().set(sessionManager.getRapidFireTime());
        //handler.postDelayed(this, 1000);

    }

    private void startLobbyTimer(){
        /*For direct start uncomment below lines*/
        /*viewModel.getIsLobby().set(true);
        startCountDown();*/
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
            String currentTime = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());
            Date startDate = simpleDateFormat.parse(currentTime);
            Date endDate = null;
            endDate = simpleDateFormat.parse(viewModel.getTwistQuizesItem().getStartTime());
            //endDate = simpleDateFormat.parse("21:09");

            long difference = endDate.getTime() - startDate.getTime();
            if(difference<0)
            {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
            }
            int days = (int) (difference / (1000*60*60*24));
            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            //Log.e("log_tag","Hours: "+hours+", Mins: "+min+" currentTime "+currentTime+" difference "+difference);
            if (viewModel.getIsInfo().get()) {
                if (lTimer != null)
                    lTimer.cancel();
                lTimer = new CountDownTimer(difference, 1000) {
                    public void onTick(long millisUntilFinished) {

                        NumberFormat f = new DecimalFormat("00");
                        long hour = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        //Log.e("l seconds remaining: ", "" + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                        String val= f.format(hour) + ":" + f.format(min) + ":" + f.format(sec);
                        viewModel.getLobbyTime().setValue(val);

                        //viewModel.getLobbyTimeRemaining().set((int) (millisUntilFinished / 1000));
                        //viewModel.getLobbyTimeRemaining().set(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }

                    public void onFinish() {
                        Log.e("lobby omFinish ", "");
                        viewModel.getLobbyTime().setValue("00:00:00");
                        /*For direct start comment below lines*/
                        viewModel.getIsLobby().set(true);
                        if (slideTimer != null)
                            slideTimer.cancel();
                        startCountDown();
                    }
                };
                lTimer.start();
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
                        viewModel.setUseOnce(true);
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

//            Log.e(">.... ",getIntent().getStringExtra("quiz_type"));
            if(getIntent().getStringExtra("quiz_type").contains("past")){
//                Log.e(">.... inside ",getIntent().getStringExtra("quiz_type"));
                viewModel.getIsInfo().set(true);
                /*startLobbyTimer();*/
                viewModel.getIsLobby().set(true);
                //startCountDown();

                if (slideTimer != null)
                    slideTimer.cancel();
            }else
            {
                viewModel.getIsInfo().set(true);
                slideTextWithTime(lobbyMessagesList,TIPS);
                startLobbyTimer();
                /*viewModel.getIsLobby().set(true);
                startCountDown();*/
            }


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
        binding.lytShowResult.setOnClickListener(v ->{
            startActivity(new Intent(this, ShowQuizAnswersActivity.class)
                    .putExtra(Const.QUIZ_ID, String.valueOf(viewModel.getTwistQuizesItem().getQuizId()))
                    .putExtra(Const.QUIZ_TYPE, viewModel.getQuizType()));
        });
        binding.lytShowLeaderboard.setOnClickListener(v -> {
            startActivity(new Intent(this, LeaderBoardActivity.class)
                    .putExtra(Const.QUIZ_ID, String.valueOf(viewModel.getTwistQuizesItem().getQuizId()))
                    .putExtra(Const.QUIZ_TYPE, viewModel.getQuizType()));

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
                //shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I completed this quiz, and had a lots of fun. Play and have fun in this app.https://play.google.com/store/apps/details?id=" + getPackageName());
                shareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("user_name")+" has just completed this quiz on Quizalong : the best medical partner you’ll ever wish for +" +
                        "\n Dive into the smartest way of learning top notch clinical accent men and start multifolding your learning and earning in just 5 minutes.+" +
                        "\n Join now for FREE+" +
                        "\n http://quizalong.com/#download+");
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


        //viewModel.getIsAnswer().observe(this, this::addSkipScore);

        viewModel.getSkipLifelines().observe(this, lifelines -> {
            Log.e("lifelines ",""+lifelines);
            if(Integer.parseInt(lifelines) > 0 && !getIntent().getStringExtra("quiz_type").contains("past")){
                binding.ivLifeLine.setVisibility(View.VISIBLE);
            }else{
                binding.ivLifeLine.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getSubscribedAmount().observe(this, subscribeAmt -> {
            Log.e("subscribeAmt ",""+subscribeAmt);
            if(subscribeAmt.contains("0") ){
                binding.tvQuiztype.setText("FREE");
            }else{
                binding.tvQuiztype.setText(subscribeAmt);
            }
        });

        //viewModel.getIsSkipAnswer().observe(this, this::addSkipScore);

        viewModel.getAnswerVal().observe(this, this::showResultDialog);

        viewModel.getOnSuccess().observe(this, AddDataResponse -> {

            Log.e("....",""+AddDataResponse);
            Toast.makeText(this, getResources().getString(R.string.live_data_successfully), Toast.LENGTH_SHORT).show();
            viewModel.getIsComplete().set(true);
        });

        viewModel.getLobbyTime().observe(this, timeString -> {
            binding.tvLobbyTime.setText(timeString);
        });

        viewModel.onLobbySuccess.observe(this, response -> {
            lobbyMessagesList.addAll(response.getQuizes());
            Log.e("....","///// "+lobbyMessagesList);
            slideTextWithTime(lobbyMessagesList,TIPS);
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
//                                                    if (viewModel.getQuizesItem().getType() == 2) {
//                                                        viewModel.getTotalScore().set(viewModel.getTotalScore().get() - (viewModel.getCurrentQuestions().getValue().getReward() * 2));
//                                                    }
                                                }
                                                addScore();
                                            }
                                    ).start());
        }*/
        //Log.e(">>> ",answer);
        /*if(cTimer != null)
            cTimer.cancel();*/
//        viewModel.setUseLifeLineInCurrentQue(false);
//        handler.removeCallbacks(this);
            if(getIntent().getStringExtra("quiz_type").contains("past")){
                addScore(viewModel.getIsAnswer().getValue() == null, "");
            }
        //addScore(false,"");
    }

    /*private void addSkipScore(Boolean isAnswerRight) {
        addScore(false,"skip");
        Log.e(">>> ","Skip Called ");
    }*/

    private void addScore(boolean isTimerOff,String skipVal) {
        viewModel.createGameHashMap(viewModel.getCurrentPosition().get(),isTimerOff,skipVal);
        Log.e("???? ",viewModel.getCurrentPosition().get()+""+viewModel.getAnswerVal().getValue());
        if (viewModel.getQuestionsList().size() > viewModel.getCurrentPosition().get()) {

            viewModel.getCurrentQuestions().setValue(viewModel.getQuestionsList().get(viewModel.getCurrentPosition().get()));
            viewModel.getCurrentPosition().set(viewModel.getCurrentPosition().get() + 1);
            viewModel.getIsAnswer().setValue(null);
            viewModel.resetQuestion();
            if(getIntent().getStringExtra("quiz_type").contains("past")){
                //addScore(viewModel.getIsAnswer().getValue() == null, "");
            }else {
                startCountDown();
            }
        } else {
            viewModel.setUseLifeLineInCurrentQue(false);
            handler.removeCallbacks(this);
            viewModel.getIsComplete().set(true);
            if(cTimer != null)
                cTimer.cancel();
            viewModel.callAddGameDataLiveApi(getIntent().getStringExtra("quiz_type"));
        }
        binding.rtlMain.setRotationY(-90);
        binding.rtlMain.animate().withLayer()
                .rotationY(0)
                .setDuration(150)
                .start();
    }

    @Override
    public void run() {
        /*viewModel.getRapidFireDuration().set(viewModel.getRapidFireDuration().get() - 1);
        if (viewModel.getRapidFireDuration().get() == 0) {
            viewModel.timesUp();
            handler.removeCallbacks(this);
        } else {
            handler.postDelayed(this, 1000);
        }*/
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
            if (slideTimer != null)
                slideTimer.cancel();
            super.onBackPressed();
        } else {
            if(cTimer != null)
                cTimer.cancel();
            if(lTimer != null)
                lTimer.cancel();
            if (slideTimer != null)
                slideTimer.cancel();
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