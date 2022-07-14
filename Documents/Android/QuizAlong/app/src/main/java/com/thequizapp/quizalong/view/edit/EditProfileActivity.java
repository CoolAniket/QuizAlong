package com.thequizapp.quizalong.view.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityEditProfileBinding;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.ads.BannerAds;
import com.thequizapp.quizalong.utils.compressimage.Compressor;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.login.AdditionalInfoActivity;
import com.thequizapp.quizalong.viewmodel.EditProfileViewModel;

import java.io.File;
import java.util.ArrayList;

public class EditProfileActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int RESULT_LOAD_PROOF = 102;
    private static final int PERMISSION_REQUEST_CODE_PROOF = 104;
    ActivityEditProfileBinding binding;
    EditProfileViewModel viewModel;
    private ArrayList<String> years;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        years = new ArrayList<String>();
        initData();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }

    private void initData() {
        SessionManager sessionManager = new SessionManager(this);
        viewModel.setUser(sessionManager.getUser());
        CurrentUser.User user = viewModel.getUser().getUser();
//        String[] strings = user.getFullname().split("\\s+");
        String name = user.getFullname();
        if (!TextUtils.isEmpty(name)) {
            viewModel.setFullName(name);
        }
        viewModel.setMobileNo(user.getMobileNo());
        viewModel.setEmail(user.getIdentity());

        viewModel.setCollegeName(user.getCollege());
        int selectedYearIndex = 1;

        years.add("Current official year");
        for(int j=0;j<viewModel.getUser().getCourse().get(0).getYear().size();j++){
            try {
                //Getting json object
                years.add(viewModel.getUser().getCourse().get(0).getYear().get(j).getValue());
                if (viewModel.getUser().getCourse().get(0).getYear().get(j).getValue().equals(user.getYear())) {
                    selectedYearIndex = j+1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        binding.spYear.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years));
        binding.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String[] paymentMethods = getResources().getStringArray(R.array.payment);
//                if (position != 0) {
//                    viewModel.setPaymentMethod(paymentMethods[position]);
//                } else {
//                    viewModel.setPaymentMethod(null);
//                }
                if (position != 0) {
                    viewModel.setYear(""+viewModel.getUser().getCourse().get(0).getYear().get(position-1).getKey());
                } else {
                    viewModel.setYear(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.spYear.setSelection(selectedYearIndex);
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
        binding.rtlProof.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE_PROOF);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_PROOF);
            }
        });
        binding.tvCancel.setOnClickListener(v -> onBackPressed());
        Log.e("PPPP ",""+viewModel.getUser());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == PERMISSION_REQUEST_CODE || requestCode == PERMISSION_REQUEST_CODE_PROOF) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, requestCode == PERMISSION_REQUEST_CODE ? RESULT_LOAD_IMAGE : RESULT_LOAD_PROOF);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == RESULT_LOAD_IMAGE || requestCode == RESULT_LOAD_PROOF) && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            File imgFile = new File(picturePath);
            File compressFile = Compressor.getDefault(this).compressToFile(imgFile);
            if (requestCode == RESULT_LOAD_IMAGE) {
                Glide.with(this)
                        .load(imgFile)
                        .circleCrop()
                        .into(binding.ivProfile);
                viewModel.setProfileUri(compressFile.getAbsolutePath());
            }
            else {
                Glide.with(this)
                        .load(imgFile)
                        .circleCrop()
                        .into(binding.ivIdProof);
                viewModel.setProof(compressFile.getAbsolutePath());
            }
        }
    }
}