package com.retrytech.quizbox.view.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.retrytech.quizbox.R;
import com.retrytech.quizbox.databinding.ActivityEditProfileBinding;
import com.retrytech.quizbox.utils.SessionManager;
import com.retrytech.quizbox.utils.ads.BannerAds;
import com.retrytech.quizbox.utils.compressimage.Compressor;
import com.retrytech.quizbox.view.BaseActivity;
import com.retrytech.quizbox.viewmodel.EditProfileViewModel;

import java.io.File;

public class EditProfileActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ActivityEditProfileBinding binding;
    EditProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        initData();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initData() {
        SessionManager sessionManager = new SessionManager(this);
        viewModel.setUser(sessionManager.getUser());
        String[] strings = viewModel.getUser().getUser().getFullName().split("\\s+");
        if (strings.length > 0) {
            viewModel.setFirstName(strings[0]);
        }
        if (strings.length > 1) {
            viewModel.setLastName(strings[1]);
        }
        new BannerAds(this, binding.bannerAds);
    }

    private void initObserve() {
        viewModel.getToast().observe(this, toastMsg -> {
            if (toastMsg != null && !toastMsg.isEmpty()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                Snackbar.make(binding.getRoot(), toastMsg, 2000)
                        .setBackgroundTint(getColorById(R.color.black))
                        .setTextColor(getColorById(R.color.white))
                        .show();
            }
        });
        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                onBackPressed();
            }
        });
    }

    private void initListener() {
        binding.rltImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        binding.tvCancel.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Glide.with(this)
                    .load(new File(picturePath))
                    .circleCrop()
                    .into(binding.ivProfile);
            File imgFile = new File(picturePath);
            File compressFile = Compressor.getDefault(this).compressToFile(imgFile);
            viewModel.setProfileUri(compressFile.getAbsolutePath());
        }
    }
}