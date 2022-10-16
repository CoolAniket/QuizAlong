package com.thequizapp.quizalong.view.payment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.api.Const;
import com.thequizapp.quizalong.databinding.ActivityPaymentBinding;
import com.thequizapp.quizalong.model.home.TwistQuizPage;
import com.thequizapp.quizalong.model.quiz.QuizItem;
import com.thequizapp.quizalong.model.rest.RestResponse;
import com.thequizapp.quizalong.model.user.CurrentUser;
import com.thequizapp.quizalong.utils.Global;
import com.thequizapp.quizalong.utils.SessionManager;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.PaymentViewModel;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class PaymentActivity extends BaseActivity implements PaymentResultWithDataListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    ActivityPaymentBinding binding;
//    private Razorpay razorpay;

    private PaymentViewModel viewModel;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        initListener();
        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());
        WebView webView = new WebView(this);
//        razorpay = new Razorpay(getString(R.string.razor_pay_test_key), webView, this);
        binding.setViewModel(viewModel);
        initData();
    }
    private void initData() {

        sessionManager = new SessionManager(this);
        //viewModel.setQuizesItem(new Gson().fromJson(getIntent().getStringExtra("data"), QuizItem.class));
        QuizItem quiz = new Gson().fromJson(getIntent().getStringExtra("data"), QuizItem.class);
        viewModel.setQuiz(quiz);
        viewModel.setAmount(getIntent().getIntExtra("amount", 1));
        viewModel.getOrderDetails();

    }
    private void initListener() {
        viewModel.getOrderId().observe(this, this::initiatePyment);
        viewModel.getOnSuccess().observe(this, this::proceedSuccess);
        viewModel.getOnStatus().observe(this, stringBooleanPair -> {
            if (!stringBooleanPair.second) {

            }
        });
    }

    private void proceedSuccess(RestResponse restResponse) {
        if (restResponse.isStatus()) {
            Toast.makeText(this, "Payment success. Redirecting...", Toast.LENGTH_SHORT)
                    .show();
            setResult(Activity.RESULT_OK);
            finish();
        }
        else {
            Toast.makeText(this, restResponse.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void initiatePyment(String orderId) {
        CurrentUser.User user = sessionManager.getUser().getUser();
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID(getString(R.string.razor_pay_test_key2));

        try {
            JSONObject options = new JSONObject();
            options.put("name", viewModel.getQuiz().getTitle());
            options.put("description", "Participation Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            options.put("currency", "INR");
            options.put("amount", "100" );
            JSONObject preFill = new JSONObject();
            preFill.put("email", user.getIdentity());
            preFill.put("contact", user.getMobileNo());
            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        try {
            String jsonString = new com.google.gson.Gson().toJson(paymentData);

            Toast.makeText(this, "Payment Successful: ", Toast.LENGTH_SHORT).show();
            String output = "Payment Successful: razorpayPaymentID: " + razorpayPaymentID+
                    "\nPayment data: "+jsonString;
            Log.d(TAG, output);
            binding.textOutput.setText(output);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(Const.QUIZ_ID_NEW, String.valueOf(viewModel.getQuiz().getQuizId()));
            hashMap.put(Const.AMOUNT, String.valueOf(viewModel.getAmount()));
            hashMap.put(Const.ORDER_ID, paymentData.getOrderId());
            hashMap.put(Const.PAYMENT_ID, paymentData.getPaymentId());
            hashMap.put(Const.SIGNATURE, paymentData.getSignature());
            hashMap.put(Const.USER_ID, Global.userId.get());
            hashMap.put(Const.STATUS, "1");

            viewModel.addpaymentdata(hashMap);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response, PaymentData paymentData) {
        try {
            String jsonString = new com.google.gson.Gson().toJson(paymentData);
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            String output = "Payment failed: errorCode: " + code+" \nresponse: "+response+
                    "\nPayment data: "+jsonString;
            Log.d(TAG, output);
            binding.textOutput.setText(output);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Razorpay.UPI_INTENT_REQUEST_CODE) {
//            razorpay.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }
}