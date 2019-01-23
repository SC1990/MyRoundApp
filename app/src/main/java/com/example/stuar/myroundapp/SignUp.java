package com.example.stuar.myroundapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText userEmail;
    private EditText userPassword;
    private EditText verifyPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        verifyPassword = findViewById(R.id.verifyPassword);
    }


    public void onSignUpBtnClick(View v){
        if(v.getId() == R.id.signUpBtn){

        }
    }

    private void addUser(){
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email)){

        }else{
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
        }
    }
}
