package com.thequizapp.quizalong.view.login;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityAdditionalInfoBinding;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.utils.compressimage.Compressor;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.view.home.CourseSelectionActivity;
import com.thequizapp.quizalong.view.main.MainActivity;
import com.thequizapp.quizalong.viewmodel.AdditionalInfoViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdditionalInfoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ActivityAdditionalInfoBinding binding;
    AdditionalInfoViewModel viewModel;
    SessionManager sessionManager;
    private ArrayList<String> courses;
    private ArrayList<String> years;
    private ArrayList<String> colleges;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    int maxYear,maxMonth,maxDay,minYear,minMonth,minDay;
    private static final String[] COLLEGES = new String[] {
            "HBT, Dr.R.N.Cooper Medical College",
            "Grant Medical College",
            "Seth G.S. Medical College",
            "Topiwala Medical College",
            "Lokmanya Tilak Medical College",
            "K.J. Somaiya Medical College",
            "Terna Medical College",
            "Rajiv Gandhi Medical College",
            "Vedanta Medical College",
            "MGM Medical College",
            "Dr DY Patil Medical College"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_additional_info);
        viewModel = new ViewModelProvider(this).get(AdditionalInfoViewModel.class);
        sessionManager = new SessionManager(this);
        courses = new ArrayList<String>();
        years = new ArrayList<String>();
        colleges = new ArrayList<String>();
        initData();
        initObserve();
        initListener();
        binding.setViewModel(viewModel);
    }
    private void initData() {
        SessionManager sessionManager = new SessionManager(this);
        viewModel.setUser(sessionManager.getUser());
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
            Toast.makeText(this, getResources().getString(R.string.log_in_successfully), Toast.LENGTH_SHORT).show();
            sessionManager.saveAdditionalDetails(isSuccess.toString());
            //startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(this, CourseSelectionActivity.class));
            finishAffinity();
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
        //Log.e("AAAAAA ",""+viewModel.getUser().getCourse());
        /*Log.e("AAAAAA ",""+viewModel.getUser().getCourse().get(0).getValue());
        Log.e("CCCC ",""+viewModel.getUser().getCourse().get(0).getYear().size());
        Log.e("CCCC ",""+viewModel.getUser().getCourse().get(0).getYear().get(0).getValue());*/
        //
        getStudents();

        binding.btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                /*maxYear = year - 7;
                maxMonth = month;
                maxDay = day;

                minYear = year - 18;
                minMonth = month;
                minDay = day;*/
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add( Calendar.YEAR, -18 ); // Subtract 6 months
                long minDate = c.getTime().getTime() ;// Twice!

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdditionalInfoActivity.this, R.style.DialogTheme,AdditionalInfoActivity.this,year, month,day);
                datePickerDialog.getDatePicker().setMaxDate(minDate);
                //datePickerDialog.getDatePicker().setMinDate(minDate);
                datePickerDialog.show();
            }
        });

        binding.etCollegeName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                viewModel.setCollegeName(mEdit.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        binding.etMobileNo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                viewModel.setMobileNo(mEdit.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable)
            {
                viewModel.setOtpMobile(editable.toString());
                if (editable.toString().trim().length() == 4) {
                    viewModel.verifyOTPPhone();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        binding.etReferral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setReferralCode(editable.toString());
            }
        });
    }

    private void getStudents(){
        //Traversing through all the items in the json array
        courses.add("Select Course");
        for(int i=0;i<viewModel.getUser().getCourse().size();i++){
            try {
                //Getting json object
                courses.add(viewModel.getUser().getCourse().get(i).getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Log.e("KKKK ",""+courses);

        colleges.add("Select college name");
        for(int k=0;k<viewModel.getUser().getColleges().size();k++){
            try {
                //Getting json object
                colleges.add(viewModel.getUser().getColleges().get(k).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        years.add("Current official year");
        for(int j=0;j<viewModel.getUser().getCourse().get(0).getYear().size();j++){
            try {
                //Getting json object
                years.add(viewModel.getUser().getCourse().get(0).getYear().get(j).getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e("CCCC ",""+years);

        //Setting adapter to show the items in the spinner
        /*binding.spCourse.setAdapter(new ArrayAdapter<String>(AdditionalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, courses));
        binding.spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    viewModel.setCourseName(""+viewModel.getUser().getCourse().get(position-1).getKey());
                } else {
                    viewModel.setCourseName(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COLLEGES);
        binding.etCollegeName.setAdapter(adapter);
        binding.spCollege.setAdapter(new ArrayAdapter<String>(AdditionalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, colleges));
        binding.spCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    viewModel.setCollegeName(""+viewModel.getUser().getColleges().get(position).getName());
                } else {
                    viewModel.setCollegeName(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spYear.setAdapter(new ArrayAdapter<String>(AdditionalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, years));
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
                    .into(binding.ivIdProof);
            File imgFile = new File(picturePath);
            File compressFile = Compressor.getDefault(this).compressToFile(imgFile);
            viewModel.setProfileUri(compressFile.getAbsolutePath());
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month+1;

        binding.txtDate.setText(myday+"/"+myMonth+"/"+myYear);
        viewModel.setDob(myday+"/"+myMonth+"/"+myYear);
//        Calendar c = Calendar.getInstance();
//        hour = c.get(Calendar.HOUR);
//        minute = c.get(Calendar.MINUTE);
//        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
//        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }


}