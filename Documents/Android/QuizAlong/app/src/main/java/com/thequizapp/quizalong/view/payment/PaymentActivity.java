package com.thequizapp.quizalong.view.payment;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityPaymentBinding;
import com.thequizapp.quizalong.view.BaseActivity;

import org.json.JSONObject;

import androidx.databinding.DataBindingUtil;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding binding;
//    PaymentViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        initListener();
        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_RDLFlV1Kg8XmCo");
    }

    private void initListener() {
        binding.startPayment.setOnClickListener((view) -> {
            initiatePyment();
        });
    }

    private void initiatePyment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "1");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
}