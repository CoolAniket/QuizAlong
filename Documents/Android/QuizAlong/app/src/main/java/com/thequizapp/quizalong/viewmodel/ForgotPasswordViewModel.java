package com.thequizapp.quizalong.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {

    private MutableLiveData<String> toast = new MutableLiveData<>();
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private MutableLiveData<Boolean> onSuccess = new MutableLiveData<>();
    private String email;

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void setToast(MutableLiveData<String> toast) {
        this.toast = toast;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(ObservableBoolean isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<Boolean> getOnSuccess() {
        return onSuccess;
    }

    public void setOnSuccess(MutableLiveData<Boolean> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void afterTextChanged(CharSequence charSequence, int type) {
        if (type == 0) {
            email = charSequence.toString();
        }
    }

    public void forgotPassword() {
        if (email == null || email.isEmpty()) {
            toast.setValue("Please enter email...!");
            return;
        }
        isLoading.set(true);

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    isLoading.set(false);
                    if (task.isSuccessful()) {
                        toast.setValue("Email sent successfully...");
                        onSuccess.setValue(true);
                    } else if (task.getException() != null) {
                        toast.setValue(task.getException().getLocalizedMessage());
                    }
                });
    }
}
