package com.example.stuar.myroundapp.OrderActivities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
            Intent intent = new Intent(ConfirmOrderActivity.this, CardPaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else{
            createOrder();

        }


    }

    private void createOrder() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm-ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(RememberMe.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("total", RememberMe.total);
        ordersMap.put("name", RememberMe.currentOnlineUser.getName());
        ordersMap.put("phone", RememberMe.currentOnlineUser.getPhone());
        ordersMap.put("address", RememberMe.currentOnlineUser.getAddress());
        ordersMap.put("rId", RetailerDetails.retailerId);
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        //ordersMap.put("status", "CustomerOrder Placed");
        ordersMap.put("payment_type", "Cash");

        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Customer Order Placed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmOrderActivity.this, CustomerOrderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    /*FirebaseDatabase.getInstance().getReference()
                            .child("Cart")
                            .child("Customer view")
                            .child(RememberMe.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "CustomerOrder Placed", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmOrderActivity.this, CustomerOrderActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });*/
                }

            }
        });
    }
}
