package com.example.stuar.myroundapp.CustomerActivities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.RetailerActivities.RetailerSU;

public class PreSignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sign_up_page);
    }

    public void onCustSU_linkBtnClick(View view) {
        if(view.getId() == R.id.custSU_linkBtn){
            startActivity(new Intent(PreSignUpPage.this, CustSignUp.class));
        }
    }

    public void onRetailerSU_linkBtnClick(View view) {
        if(view.getId() == R.id.retailerSU_linkBtn){
            startActivity(new Intent(PreSignUpPage.this, RetailerSU.class));
        }
    }
}
