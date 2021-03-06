package com.example.stuar.myroundapp.Other;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RetailerDetails;
import com.example.stuar.myroundapp.RetailerActivities.RetailerHome;
import com.example.stuar.myroundapp.SignUpAndLogInActivities.CustomerSignUpActivity;
import com.example.stuar.myroundapp.CustomerActivities.CustomerHome;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.User;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.SignUpAndLogInActivities.LogIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainPageActivity extends AppCompatActivity {


    private ProgressDialog loadingBar;
    private String parentDbName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        loadingBar = new ProgressDialog(this);


        Paper.init(this);

        String UserPhoneKey = Paper.book().read(RememberMe.UserEmailKey);
        String UserPasswordKey = Paper.book().read(RememberMe.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey)  &&  !TextUtils.isEmpty(UserPasswordKey)) {
                AllowAccess(UserPhoneKey, UserPasswordKey);


                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
            else{
                startActivity(new Intent(MainPageActivity.this, LogIn.class));
            }

        }
    }


    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users/customers").child(phone).exists()) {
                    User usersData = dataSnapshot.child("users/customers").child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            //Toast.makeText(MainPageActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(MainPageActivity.this, CustomerHome.class);
                            RememberMe.currentOnlineUser = usersData;
                            loadingBar.dismiss();
                            startActivity(intent);

                        }

                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainPageActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(dataSnapshot.child("users/retailers").child(phone).exists()){
                    User usersData1 = dataSnapshot.child("users/retailers").child(phone).getValue(User.class);

                    if (usersData1.getPhone().equals(phone)) {
                        if (usersData1.getPassword().equals(password)) {

                            //Toast.makeText(MainPageActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(MainPageActivity.this, RetailerHome.class);
                            RememberMe.currentOnlineUser = usersData1;
                            RetailerDetails.retailerId = usersData1.getUserId();
                            loadingBar.dismiss();
                            startActivity(intent);

                        }
                    }


                }

                else {
                    Toast.makeText(MainPageActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void onSignUpLinkBtnClick2(View view) {
        Intent intent = new Intent(MainPageActivity.this, CustomerSignUpActivity.class);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(MainPageActivity.this, LogIn.class);
        startActivity(intent);
    }

}
