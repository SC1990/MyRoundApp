package com.example.stuar.myroundapp.CustomerActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.User;
import com.example.stuar.myroundapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.paperdb.Paper;

public class CustSignUp extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPhone;
    private EditText userPassword;
    private EditText verifyPassword;


    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceUsers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Paper.init(this);

        firebaseAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.register_phone_number_input);
        userPassword = findViewById(R.id.register_password_input);
        verifyPassword = findViewById(R.id.verify_password);

        progressDialog = new ProgressDialog(this);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users/customers");

    }

    public void onSignUpBtnClick(View v){
        if(v.getId() == R.id.register_btn){
            CreateAccount();
        }
    }

    /*private void addUser(){

        String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();
        String confirmPassword = verifyPassword.getText().toString().trim();
        final String phoneNum = userPhone.getText().toString().trim();


        //String id = databaseReferenceUsers.push().getKey();

        if(email.isEmpty()){
            userEmail.setError("Email Required!");
            userEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please Enter a Valid Email!");
            userEmail.requestFocus();
            return;
        }

        if(phoneNum.isEmpty()){
            userPhone.setError("Phone number Required!");
            userPhone.requestFocus();
            return;
        }


        if(password.isEmpty()){
            userPassword.setError("Password Required!");
            userPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            userPassword.setError("Password Must be at Least 6 characters!");
            userPassword.requestFocus();
            return;
        }


        if(confirmPassword.isEmpty()){
            verifyPassword.setError("Please Confirm Password!");
            verifyPassword.requestFocus();
            return;
        }
        //if password does not contain a number
        if(!password.matches(".*\\d+.*")){
            userPassword.setError("Password Must be contain a number");
            userPassword.requestFocus();
            return;
        }


        //password and password confirmation must be identical
        if(!password.equals(confirmPassword)){
            verifyPassword.setError("Passwords don't match!");
            verifyPassword.requestFocus();
            return;
        }



        else{

            progressDialog.setMessage("Creating account..");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                String id = firebaseUser.getUid();
                                String name = "";
                                String address = "";
                                String image = "empty";
                                User user = new User(name, phoneNum, password, image, address, id);
                                user.setUserType("cust");

                                databaseReferenceUsers.child(phoneNum).setValue(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), CustomerHome.class));
                                Toast.makeText(CustSignUp.this, "Account created", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(CustSignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(CustSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            progressDialog.dismiss();
                        }

                    });



        }


    }*/

    private void CreateAccount()
    {
        String email = userEmail.getText().toString();
        String phone = userPhone.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = verifyPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmPassword))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirmPassword)){
            verifyPassword.setError("Passwords don't match!");
            verifyPassword.requestFocus();

        }
        else
        {


            ValidatePhoneNumber(email, phone, password);
        }
    }



    private void ValidatePhoneNumber(final String name, final String phone, final String password)
    {

        firebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("phone", phone);
                            userdataMap.put("password", password);
                            userdataMap.put("name", name);
                            userdataMap.put("userId", userId);
                            userdataMap.put("image", "empty");
                            userdataMap.put("address", "");
                            userdataMap.put("userType", "customer");

                            User user = new User(name, phone, password, "empty", "", userId);

                            databaseReferenceUsers.child(phone).setValue(userdataMap);
                            RememberMe.currentOnlineUser = user;
                            finish();
                            startActivity(new Intent(getApplicationContext(), CustomerHome.class));
                            Toast.makeText(CustSignUp.this, "Account created", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(CustSignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CustSignUp.this, "No", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(CustSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }

                    }

                });


    }












/*
    public void onSignUpBtnClick(View v){
        if(v.getId() == R.id.signUpBtn){
            addUser();
        }
    }

    private void addUser(){

        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();
        String confirmPassword = verifyPassword.getText().toString().trim();
        final String phoneNum = phone.getText().toString().trim();


        //String id = databaseReferenceUsers.push().getKey();

        if(email.isEmpty()){
            userEmail.setError("Email Required!");
            userEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please Enter a Valid Email!");
            userEmail.requestFocus();
            return;
        }

        if(phoneNum.isEmpty()){
            phone.setError("Phone number Required!");
            phone.requestFocus();
            return;
        }


        if(password.isEmpty()){
            userPassword.setError("Password Required!");
            userPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            userPassword.setError("Password Must be at Least 6 characters!");
            userPassword.requestFocus();
            return;
        }


        if(confirmPassword.isEmpty()){
            verifyPassword.setError("Please Confirm Password!");
            verifyPassword.requestFocus();
            return;
        }
        //if password does not contain a number
        if(!password.matches(".*\\d+.*")){
            Toast toast = Toast.makeText(CustSignUp.this, "Password must contain a number", Toast.LENGTH_SHORT);
            toast.show();
        }


        //password and password confirmation must be identical
        if(!password.equals(confirmPassword)){
                Toast passwordMsg = Toast.makeText(CustSignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
                passwordMsg.show();
        }



        else{

            progressDialog.setMessage("Creating account..");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                String id = firebaseUser.getUid();
                                String name = "";
                                String address = "";
                                String town = "";
                                User customer = new User(name, address, town, phoneNum, id, password);
                                customer.setUserType("cust");

                                databaseReferenceUsers.child(phoneNum).setValue(customer);
                                finish();
                                startActivity(new Intent(getApplicationContext(), CustomerHome.class));
                                Toast.makeText(CustSignUp.this, "Account created", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(CustSignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(CustSignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            progressDialog.dismiss();
                        }

                    });



        }


    }

    public void onLogInLinkBtnClick(View view) {
        if(view.getId() == R.id.logInLinkBtn){
            startActivity(new Intent(CustSignUp.this, LogIn.class));
        }
    }*/
}
