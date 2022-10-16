package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thequizapp.quizalong.BuildConfig;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isOtpSent = new ObservableBoolean(false);
    private ObservableBoolean isOtpVerified = new ObservableBoolean(false);
    private CurrentUser user;

    private String fullName;
    private String mobileNo;
    private String email;
    private String collegeName;
    private String year;
    private String profileUri;
    private String proof;
    private String otpMobile;
    private boolean mobileAuthenticated = false;


    public ObservableBoolean getIsOtpSent() {
        return isOtpSent;
    }

    public ObservableBoolean getIsOtpVerified() {
        return isOtpVerified;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
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

    public void afterTextChanged(CharSequence charSequence, int type) {
        switch (type) {
            case 0:
                fullName = charSequence.toString();
                break;
            case 1:
                mobileNo = charSequence.toString();
                break;
            case 2:
                email = charSequence.toString();
                break;
            case 3:
                collegeName = charSequence.toString();
                break;
            case 4:
                otpMobile = charSequence.toString();
                if (charSequence.toString().trim().length() == 4) {
                    verifyOTPPhone();
                }
            default:
                //
                break;
        }
    }

    public void authenticatePhone() {
        if (mobileNo == null || mobileNo.isEmpty() || !isValidMobile(mobileNo)) {
            toast.setValue("Please enter a valid mobile number");
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
                            if (restResponse != null) {
                                if (restResponse.isStatus()) {
                                    isOtpSent.set(true);
                                    toast.setValue("OTP sent successfully.");
                                } else {
                                    toast.setValue("OTP not sent. Please try again.");
                                }
                            } else {
                                toast.setValue(throwable.getLocalizedMessage());
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
                                if (restResponse.isStatus()) {
                                    isOtpVerified.set(true);
                                    setMobileAuthenticated(true);
                                    toast.setValue("OTP verified successfully.");
                                }
                                else {
                                    toast.setValue("Incorrect OTP. Please try again.");
                                }
                            } else {
                                toast.setValue(throwable.getLocalizedMessage());
                            }
                        })
        );

    }
    public void editProfile() {
        if (fullName == null || fullName.isEmpty()) {
            toast.setValue("Please enter name");
            return;
        }
        if (mobileNo == null || mobileNo.isEmpty() || !isValidMobile(mobileNo)) {
            toast.setValue("Please enter a valid mobile number");
            return;
        }
        if (!mobileAuthenticated) {
            toast.setValue("Please authenticate mobile number");
            return;
        }
        if (year == null || year.isEmpty()) {
            toast.setValue("Please select year");
            return;
        }
        if (collegeName == null || collegeName.isEmpty()) {
            toast.setValue("Please enter college name");
            return;
        }
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("fullName", toRequestBody(fullName));
        hashMap.put(Const.USERID, toRequestBody(Global.userId.get()));
        hashMap.put("yearId", toRequestBody(getYear()));
        hashMap.put("college", toRequestBody(getCollegeName()));
        hashMap.put("identity", toRequestBody(getEmail()));
        hashMap.put("mobile_no", toRequestBody(getMobileNo()));
//        hashMap.put(Const.USERID, toRequestBody("35"));
        MultipartBody.Part profile = null;
        if (profileUri != null && !profileUri.isEmpty()) {
            File file = new File(profileUri);
            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

            profile = MultipartBody.Part.createFormData(Const.PROFILE_IMAGE, file.getName(), requestFile);
        }
        MultipartBody.Part proof = null;
        if (profileUri != null && !profileUri.isEmpty()) {
            File file = new File(profileUri);
            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

            proof = MultipartBody.Part.createFormData(Const.PROOF, file.getName(), requestFile);
        }
        disposable.add(
                Global.initRetrofit()
                        .editProfile(BuildConfig.APIKEY, hashMap, profile, proof)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable1 -> isLoading.set(true))
                        .doOnTerminate(() -> isLoading.set(false))
                        .subscribe((restResponse, throwable) -> {
                            if (restResponse != null && restResponse.isStatus()) {
                                isSuccess.setValue(true);
                            }
                        })
        );
    }

    public RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }
    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 10;
        }
        return false;
    }
}
