package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmOrderActivity extends AppCompatActivity {

    private EditText orderName, orderPhone, orderAddress, orderTown;
    private Button confirmBtn;

    private String total = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        confirmBtn = findViewById(R.id.confirm_btn);
        orderName = findViewById(R.id.order_name);
        orderPhone= findViewById(R.id.order_phonenum);
        orderAddress = findViewById(R.id.order_address);
        orderTown = findViewById(R.id.order_town);

        total = getIntent().getStringExtra("total");
        Toast.makeText(getApplicationContext(), "Total: €" + total, Toast.LENGTH_SHORT).show();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
