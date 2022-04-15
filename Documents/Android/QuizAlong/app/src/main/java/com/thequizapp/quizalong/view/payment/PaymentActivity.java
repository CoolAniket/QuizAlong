package com.thequizapp.quizalong.view.payment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityPaymentBinding;
import com.thequizapp.quizalong.view.BaseActivity;
import com.thequizapp.quizalong.viewmodel.PaymentViewModel;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class PaymentActivity extends BaseActivity implements PaymentResultWithDataListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    ActivityPaymentBinding binding;
//    private Razorpay razorpay;

    private PaymentViewModel viewModel;

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
    }

    private void initListener() {
        binding.startPayment.setOnClickListener((view) -> {
            viewModel.getOrderDetails();
        });
        viewModel.getOrderId().observe(this, this::initiatePyment);
    }

    private void initiatePyment(String orderId) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID(getString(R.string.razor_pay_test_key));

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Quiz Along");
            options.put("description", "Participation Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            options.put("currency", "INR");
            options.put("amount", "200");
            JSONObject preFill = new JSONObject();
            preFill.put("email", "saifybombay1@gmail.com");
            preFill.put("contact", "7737303466");
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

            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            String output = "Payment Successful: razorpayPaymentID: " + razorpayPaymentID+
                    "\nPayment data: "+jsonString;
            Log.d(TAG, output);
            binding.textOutput.setText(output);
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
}