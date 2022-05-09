package com.thequizapp.quizalong.view.results;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityPlayedResultBinding;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.leaderboard.LeaderBoardActivity;
import com.thequizapp.quizalong.viewmodel.PlayedResultViewModel;
import com.thequizapp.quizalong.viewmodel.QuizViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class PlayedResultActivity extends AppCompatActivity {

    ActivityPlayedResultBinding binding;
    PlayedResultViewModel viewModel;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_played_result);
        viewModel = new ViewModelProvider(this).get(PlayedResultViewModel.class);
        initData();
        initListener();
    }


    private void initData() {

        sessionManager = new SessionManager(this);
        //viewModel.setQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), HomePage.QuizesItem.class));
        viewModel.setTwistQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), TwistQuizPage.QuizItem.class));
        //int randomNumber=r.nextInt(TIPS.length);

        viewModel.setQuizType(getIntent().getStringExtra("quiz_type"));
    }

    private void initListener() {
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
                    .putExtra(Const.QUIZ_ID, String.valueOf(viewModel.getTwistQuizesItem().getValue().getQuizId()))
                    .putExtra(Const.QUIZ_TYPE, viewModel.getQuizType()));
        });
        binding.lytShowLeaderboard.setOnClickListener(v -> {
            startActivity(new Intent(this, LeaderBoardActivity.class)
                    .putExtra(Const.QUIZ_ID, String.valueOf(viewModel.getTwistQuizesItem().getValue().getQuizId()))
                    .putExtra(Const.QUIZ_TYPE, viewModel.getQuizType()));

        });
        binding.setViewModel(viewModel);
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


}