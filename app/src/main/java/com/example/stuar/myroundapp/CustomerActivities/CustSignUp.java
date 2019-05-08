package com.example.stuar.myroundapp.CustomerActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stuar.myroundapp.LogIn;
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

public class CustSignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText userEmail;
    private EditText userPassword;
    private EditText verifyPassword;


    private ProgressDialog progressDialog;
    DatabaseReference databaseReferenceUsers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), CustomerHome.class));
        }

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        verifyPassword = findViewById(R.id.verifyPassword);

        progressDialog = new ProgressDialog(this);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users/customers");


    }


    public void onSignUpBtnClick(View v){
        if(v.getId() == R.id.signUpBtn){
            addUser();
        }
    }

    private void addUser(){

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String confirmPassword = verifyPassword.getText().toString().trim();


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
                                String num = "";
                                User user = new User(name, address, town, num, id);
                                user.setUserType("cust");

                                databaseReferenceUsers.child(id).setValue(user);
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
    }
}
