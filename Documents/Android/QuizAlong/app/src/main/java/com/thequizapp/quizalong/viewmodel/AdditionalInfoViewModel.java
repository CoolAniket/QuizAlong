package com.thequizapp.quizalong.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.model.user.RegisterUser;
import com.thequizapp.quizalong.utils.Global;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AdditionalInfoViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isOtpSent = new ObservableBoolean(false);


    private CurrentUser user;

    private String courseName;
    private String collegeName;
    private String mobileNo;
    private String otpMobile;
    private boolean mobileAuthenticated = false;
    private String year;
    private String profileUri;
    private String dob;
    private String referralCode;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOtpMobile() {
        return otpMobile;
    }

    public void setOtpMobile(String otpMobile) {
        this.otpMobile = otpMobile;
    }

    public boolean isMobileAuthenticated() {
        return mobileAuthenticated;
    }

    public void setMobileAuthenticated(boolean mobileAuthenticated) {
        this.mobileAuthenticated = mobileAuthenticated;
    }

    public ObservableBoolean getIsOtpSent() {
        return isOtpSent;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }

    public void authenticatePhone() {
        if (mobileNo == null || mobileNo.isEmpty() || !isValidMobile(mobileNo)) {
            toast.setValue("Please enter a valid mobile number...!");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", Global.userId.get());
        /*hashMap.put("course_id", toRequestBody(getCourseName()));*/
        hashMap.put("type", "0"); // 0 for phone number verification. 1 for forget password
        hashMap.put("mobile_no", getMobileNo());

        disposable.add(
                Global.initRetrofit()
                        .sentOTPonMobile(BuildConfig.APIKEY, hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable1 -> isLoading.set(true))
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((restResponse, throwable) -> {
                            if (restResponse != null && restResponse.isStatus()) {
//                                isSuccess.setValue(true);
                                isOtpSent.set(true);
                            }
                        })
        );

    }

    public void verifyOTPPhone() {
        if (otpMobile == null || otpMobile.length() != 4) {
            toast.setValue("Not a valid OTP...!");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", Global.userId.get());
        /*hashMap.put("course_id", toRequestBody(getCourseName()));*/
        hashMap.put("type", "0"); // 0 for phone number verification. 1 for forget password
        hashMap.put("mobile_no", getMobileNo());
        hashMap.put("otp", getOtpMobile());

        disposable.add(
                Global.initRetrofit()
                        .verifyOTPonMobile(BuildConfig.APIKEY, hashMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable1 -> isLoading.set(true))
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((restResponse, throwable) -> {
                            if (restResponse != null) {
                                if (restResponse.isStatus())
                                    setMobileAuthenticated(true);
                                else
                                    toast.setValue("Incorrect OTP. Please try again...!");
                            } else {
                                toast.setValue(throwable.getLocalizedMessage());
                            }
                        })
        );

    }
    public void submitProfile() {
        if (collegeName == null || collegeName.isEmpty()) {
            toast.setValue("Please enter college name...!");
            return;
        }
        //Log.d("MMMMM ",""+isValidMobile(mobileNo));
        if (mobileNo == null || mobileNo.isEmpty() || !isValidMobile(mobileNo)) {
            toast.setValue("Please enter mobile number...!");
            return;
        }
        if (!mobileAuthenticated) {
            toast.setValue("Please authenticate mobile number...!");
            return;
        }
        if (year == null || year.isEmpty()) {
            toast.setValue("Please select year...!");
            return;
        }
        /*if (courseName == null || courseName.isEmpty()) {
            toast.setValue("Please select course...!");
            return;
        }*/
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("user_id", toRequestBody(Global.userId.get()));
        /*hashMap.put("course_id", toRequestBody(getCourseName()));*/
        hashMap.put("course_id", toRequestBody("1"));
        hashMap.put("year_id", toRequestBody(getYear()));
        hashMap.put("college", toRequestBody(getCollegeName()));
        hashMap.put("dob", toRequestBody(getDob()));
        hashMap.put("mobile_no", toRequestBody(getMobileNo()));
        hashMap.put("refercode", toRequestBody(getReferralCode()));
        MultipartBody.Part body = null;
        if (profileUri != null && !profileUri.isEmpty()) {
            File file = new File(profileUri);
            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

            body = MultipartBody.Part.createFormData("proof", file.getName(), requestFile);
        }

        Log.e("MMMM ",""+getMobileNo());
        disposable.add(
                Global.initRetrofit()
                        .additionDetails(BuildConfig.APIKEY, hashMap, body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable1 -> isLoading.set(true))
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((restResponse, throwable) -> {
                            Log.e("EEE ",""+restResponse+" "+throwable);
                            if (restResponse != null && restResponse.isStatus()) {
                                isSuccess.setValue(true);
                            }
                        })
        );
    }
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 10;
        }
        return false;
    }

    public RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
