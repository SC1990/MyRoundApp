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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText userEmail;
    private EditText userPassword;
    private EditText verifyPassword;

    private ProgressDialog progressDialog;

    //DatabaseReference databaseReferenceUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        verifyPassword = findViewById(R.id.verifyPassword);

        progressDialog = new ProgressDialog(this);

        //databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users");
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

        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password )){
            //String id = databaseReferenceUsers.push().getKey();
            //User user = new User(email, password);
            //databaseReferenceUsers.child(id).setValue(user);

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                Toast.makeText(SignUp.this, "Account created", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(SignUp.this, "Account not created", Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.dismiss();
                        }

                    });



        }else{
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show();
        }
    }

    public void onLogInLinkBtnClick(View view) {
        if(view.getId() == R.id.logInLinkBtn){
            startActivity(new Intent(SignUp.this, LogIn.class));
        }
    }
}
