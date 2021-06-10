package com.example.giftsapp.Controller.RazorPay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giftsapp.R;
import com.google.firestore.v1.StructuredQuery;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class TestPay extends AppCompatActivity {
    EditText edtAmount;
    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pay);

        edtAmount = findViewById(R.id.edtAmount);
        btnPay = findViewById(R.id.btnPay);

        Checkout.preload(getApplicationContext());
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {
        /**
x         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_agSuWkI0TetOjD");
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("currency", "USD");
            options.put("amount", "50");//pass amount in currency subunits

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

//    @Override
//    public void onPaymentSuccess(String s) {
//        Toast.makeText(this, "thanh cong", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//    }
}