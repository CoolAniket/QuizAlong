package com.thequizapp.quizalong.view.payment;


import android.os.Bundle;

import com.razorpay.Checkout;
import com.thequizapp.quizalong.R;
import com.thequizapp.quizalong.databinding.ActivityPaymentBinding;
import com.thequizapp.quizalong.view.BaseActivity;

import org.json.JSONObject;

public class PaymentActivity extends BaseActivity {
    ActivityPaymentBinding binding;
//    PaymentViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_RDLFlV1Kg8XmCo");

        initiatePyment();
    }

    private void initiatePyment() {
        /*try {
            RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", 1); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = razorpay.Orders.create(orderRequest);
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }*/
    }
}