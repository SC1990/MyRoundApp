package com.example.stuar.myroundapp.RetailerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stuar.myroundapp.R;

public class PreSalesAnalysis extends AppCompatActivity {

    Button salesOverviewBtn, dailySummaryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales_analysis);

        salesOverviewBtn = findViewById(R.id.sales_overview_btn);
        dailySummaryBtn = findViewById(R.id.daily_sales_btn);

        salesOverviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreSalesAnalysis.this, RetailerSalesOverviewActivity.class));
            }
        });


    }
}
