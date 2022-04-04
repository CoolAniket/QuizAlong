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
    private CurrentUser user;

    private String courseName;
    private String collegeName;
    private String year;
    private String profileUri;
    private String dob;

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public void submitProfile() {
        if (collegeName == null || collegeName.isEmpty()) {
            toast.setValue("Please enter college name...!");
            return;
        }
        if (year == null || year.isEmpty()) {
            toast.setValue("Please select year...!");
            return;
        }
        if (courseName == null || courseName.isEmpty()) {
            toast.setValue("Please select course...!");
            return;
        }
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("user_id", toRequestBody(Global.userId.get()));
        hashMap.put("course_id", toRequestBody(getCourseName()));
        hashMap.put("year_id", toRequestBody(getYear()));
        hashMap.put("college", toRequestBody(getCollegeName()));
        hashMap.put("dob", toRequestBody(getDob()));
        MultipartBody.Part body = null;
        if (profileUri != null && !profileUri.isEmpty()) {
            File file = new File(profileUri);
            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

            body = MultipartBody.Part.createFormData("proof", file.getName(), requestFile);
        }

        Log.e("MMMM ",""+hashMap);
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

    public RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
