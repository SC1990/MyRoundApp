package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText orderName, orderPhone, orderAddress, orderTown;
    private Button goToPayBtn;

    private String total = "";

    private String saveCurrentDate;
    private String saveCurrentTime;

    private RadioGroup radioGroup;

    private String paymentOption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        goToPayBtn = findViewById(R.id.confirm_btn);
        orderName = findViewById(R.id.order_name);
        orderPhone= findViewById(R.id.order_phonenum);
        orderAddress = findViewById(R.id.order_address);
        orderTown = findViewById(R.id.order_town);

        orderName.setText(RememberMe.currentOnlineUser.getName());
        orderPhone.setText(RememberMe.currentOnlineUser.getPhone());
        orderAddress.setText(RememberMe.currentOnlineUser.getAddress());
        orderTown.setText(RememberMe.currentOnlineUser.getAddress());

        total = getIntent().getStringExtra("total");

        goToPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToPayment();
                RememberMe.cartCount = 0;
            }
        });

        radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio0:
                        paymentOption = "card";
                        break;
                    case R.id.radio1:
                        paymentOption = "cash";
                        break;

                }
            }
        });
    }


    private void goToPayment() {

        if(paymentOption.equals("card")){
            Intent intent = new Intent(ConfirmOrderActivity.this, CustomerPaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(ConfirmOrderActivity.this, CustomerOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


    }
}
