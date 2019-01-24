package com.example.stuar.myroundapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {


    private EditText userEmail;
    private EditText userPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userEmail = findViewById(R.id.logInEmail);
        userPassword = findViewById(R.id.logInPassword);

        progressDialog = new ProgressDialog(this);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), UserHome.class));
        }
    }

    private void userLogin(){
        progressDialog.setMessage("Logging in..");
        progressDialog.show();

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password )){
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if(task.isSuccessful()){
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserHome.class));
                        Toast.makeText(LogIn.this, "logged in", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });



        }
    }

    public void onLogInBtnClick(View view){
        if(view.getId() == R.id.logInBtn){
            userLogin();
        }

    }

    public void onSignUpLinkBtnClick(View view){
        if(view.getId() == R.id.signUpLinkBtn){
            finish();
            startActivity(new Intent(LogIn.this, SignUp.class));
        }
    }
}
