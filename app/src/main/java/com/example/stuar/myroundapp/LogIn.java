package com.example.stuar.myroundapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuar.myroundapp.CustomerActivities.CustSignUp;
import com.example.stuar.myroundapp.CustomerActivities.CustomerHome;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import io.paperdb.Paper;

public class LogIn extends AppCompatActivity {


    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "users/customers";
    private CheckBox checkBox;

    private TextView retailerLink;
    private TextView notRetailerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        retailerLink = (TextView) findViewById(R.id.retailer_link);
        notRetailerLink = (TextView) findViewById(R.id.not_retailer_link);
        loadingBar = new ProgressDialog(this);


        checkBox = findViewById(R.id.remember_me_checkbox);
        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });

        retailerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login Retailer");
                retailerLink.setVisibility(View.INVISIBLE);
                notRetailerLink.setVisibility(View.VISIBLE);
                parentDbName = "users/retailers";
            }
        });

        notRetailerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login");
                retailerLink.setVisibility(View.VISIBLE);
                notRetailerLink.setVisibility(View.INVISIBLE);
                parentDbName = "users/customers";
            }
        });



    }

    private void LoginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password);
        }

    }




    private void AllowAccessToAccount(final String phone, final String password)
    {
        if(checkBox.isChecked())
        {
            Paper.book().write(RememberMe.UserPhoneKey, phone);
            Paper.book().write(RememberMe.UserPasswordKey, password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    User usersData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("users/retailers"))
                            {
                                Toast.makeText(LogIn.this, "Welcome " + usersData.getName() +", you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LogIn.this, RetailerHome.class);
                                RememberMe.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("users/customers"))
                            {
                                Toast.makeText(LogIn.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LogIn.this, CustomerHome.class);
                                RememberMe.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LogIn.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LogIn.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


















    /*private void ValidateLogin(){
       *//* progressDialog.setMessage("Logging in..");
        progressDialog.show();*//*

        String phone = userPhone.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password )){
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

        LoginUser(phone, password);

    }

    private void LoginUser(final String phone, final String userPassword) {

        Paper.book().write(RememberMe.UserPhone, phone);
        Paper.book().write(RememberMe.UserPasswordKey, userPassword);

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    User usersData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(userPassword))
                        {
                            if (parentDbName.equals("retailers"))
                            {
                                Toast.makeText(LogIn.this, "Welcome retailer, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogIn.this, RetailerHome.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("users/customers"))
                            {

                                Toast.makeText(LogIn.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogIn.this, CustomerHome.class);
                                //RememberMe.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {

                            Toast.makeText(LogIn.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LogIn.this, "Account with this " + phone + " number does not exist.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       *//* firebaseAuth.signInWithEmailAndPassword(phone, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {

                            databaseReference = FirebaseDatabase.getInstance().getReference("users/customers").child(firebaseAuth.getUid());
                            // Attach a listener to read the data at your profile reference
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    User user = dataSnapshot.getValue(User.class);
                                    try{
                                        userType = user.getUserType();

                                        if (user.getUserType().equals("cust")){
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), CustomerHome.class));
                                            Toast.makeText(LogIn.this, "Logged in", Toast.LENGTH_SHORT).show();
                                        }

                                    }catch(Exception e){
                                        NullPointerException nullPointerException;

                                        finish();
                                        startActivity(new Intent(getApplicationContext(), RetailerHome.class));
                                        Toast.makeText(LogIn.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });*//*
    }

    public void linkUserToAppropriatePage(String userType){
        if(userType.equals("cust")){
            startActivity(new Intent(getApplicationContext(), CustomerHome.class));
        }
        else if(userType.equals("ret")){
            startActivity(new Intent(getApplicationContext(), RetailerHome.class));
        }
    }


    public void onLogInBtnClick(View view){
        if(view.getId() == R.id.logInBtn){
            ValidateLogin();
        }

    }*/

    public void onSignUpLinkBtnClick(View view){
        if(view.getId() == R.id.signUpLinkBtn){
            finish();
            startActivity(new Intent(LogIn.this, CustSignUp.class));
        }
    }
}