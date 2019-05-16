package com.example.stuar.myroundapp.RetailerActivities;

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
import com.example.stuar.myroundapp.Models.Retailer;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.RetailerHome;
import com.example.stuar.myroundapp.CustomerActivities.CustomerHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetailerSU extends AppCompatActivity {

    private EditText retEmail;
    private EditText retPassword;
    private EditText verifyPassword;
    Retailer retailer;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReferenceUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_su);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), CustomerHome.class));
        }

        retEmail = findViewById(R.id.retEmail);
        retPassword = findViewById(R.id.retPassword);
        verifyPassword = findViewById(R.id.retVerifyPassword);

        progressDialog = new ProgressDialog(this);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users/retailers");
    }




    private void addRetUser() {
        progressDialog.setMessage("Creating account..");
        progressDialog.show();

        String email = retEmail.getText().toString().trim();
        final String password = retPassword.getText().toString().trim();
        String confirmPassword = verifyPassword.getText().toString().trim();


        //String id = databaseReferenceUsers.push().getKey();

        if(email.isEmpty()){
            retEmail.setError("Email Required!");
            retEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            retEmail.setError("Please Enter a Valid Email!");
            retEmail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            retPassword.setError("Password Required!");
            retPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            retPassword.setError("Password Must be at Least 6 characters!");
            retPassword.requestFocus();
            return;
        }


        if(confirmPassword.isEmpty()){
            verifyPassword.setError("Please Confirm Password!");
            verifyPassword.requestFocus();
            return;
        }
        //if password does not contain a number
        if(!password.matches(".*\\d+.*")){
            Toast toast = Toast.makeText(RetailerSU.this, "Password must contain a number", Toast.LENGTH_SHORT);
            toast.show();
        }


        //password and password confirmation must be identical
        if(!password.equals(confirmPassword)){
            Toast passwordMsg = Toast.makeText(RetailerSU.this, "Passwords don't match", Toast.LENGTH_SHORT);
            passwordMsg.show();
        }



        else{

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
                                String num = "";
                                User user = new Retailer(name, address, image, num, id, password);
                                user.setUserType("ret");

                                databaseReferenceUsers.child(id).setValue(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), RetailerHome.class));
                                Toast.makeText(RetailerSU.this, "Account created", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(RetailerSU.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(RetailerSU.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            progressDialog.dismiss();
                        }

                    });



        }


    }

    public void onRet_SignUpBtnClick(View view) {
        if(view.getId() == R.id.retSignUpBtn){
            addRetUser();
        }
    }



    public void onLogInLinkBtnClick(View view) {
        if(view.getId() == R.id.login_btn){
            startActivity(new Intent(RetailerSU.this, LogIn.class));
        }
    }
}
