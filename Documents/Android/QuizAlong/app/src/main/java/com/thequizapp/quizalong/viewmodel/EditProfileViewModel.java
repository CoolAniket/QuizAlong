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
    private CurrentUser user;

    private String firstName;
    private String lastName;
    private String profileUri;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void afterTextChanged(CharSequence charSequence, int type) {
        switch (type) {
            case 0:
                firstName = charSequence.toString();
                break;
            case 1:
                lastName = charSequence.toString();
                break;
            default:
                //
                break;
        }
    }

    public void editProfile() {
        if (firstName == null || firstName.isEmpty()) {
            toast.setValue("Please enter firstName...!");
            return;
        }
        if (lastName == null || lastName.isEmpty()) {
            toast.setValue("Please enter lastName...!");
            return;
        }
        HashMap<String, RequestBody> hashMap = new HashMap<>();
        hashMap.put("fullName", toRequestBody(firstName.concat(" ").concat(lastName)));
        hashMap.put(Const.USER_ID, toRequestBody(Global.userId.get()));
        MultipartBody.Part body = null;
        if (profileUri != null && !profileUri.isEmpty()) {
            File file = new File(profileUri);
            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

            body = MultipartBody.Part.createFormData(Const.PROFILE_IMAGE, file.getName(), requestFile);
        }
        disposable.add(
                Global.initRetrofit()
                        .editProfile(BuildConfig.APIKEY, hashMap, body)
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
}
