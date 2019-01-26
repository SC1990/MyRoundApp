package com.example.stuar.myroundapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

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
            startActivity(new Intent(getApplicationContext(), UserHome.class));
        }

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        verifyPassword = findViewById(R.id.verifyPassword);

        progressDialog = new ProgressDialog(this);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("customers");


    }


    public void onSignUpBtnClick(View v){
        if(v.getId() == R.id.signUpBtn){
            addUser();
        }
    }

    private void addUser(){
        progressDialog.setMessage("Creating account..");
        progressDialog.show();

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String confirmPassword = verifyPassword.getText().toString().trim();

        User user = new User(email, password);
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
            Toast toast = Toast.makeText(SignUp.this, "Password must contain a number", Toast.LENGTH_SHORT);
            toast.show();
        }


        //password and password confirmation must be identical
        if(!password.equals(confirmPassword)){
                Toast passwordMsg = Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT);
                passwordMsg.show();
        }



        else{

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserHome.class));
                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(SignUp.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            progressDialog.dismiss();
                        }

                    });



        }
    }

    public void onLogInLinkBtnClick(View view) {
        if(view.getId() == R.id.logInLinkBtn){
            startActivity(new Intent(SignUp.this, LogIn.class));
        }
    }
}
