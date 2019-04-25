package com.example.stuar.myroundapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    /*
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference().child("Post").push();

    Map<String, Object> map = new HashMap<>();
    map.put("Title", "dummy title");
    map.put("Address", "dummy address");
    map.put("PostID", myRef.getKey());
    myRef.setValue(map);

     */


    private EditText userEmail;
    private EditText userPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private String userType;
    private User user;

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
            databaseReference = FirebaseDatabase.getInstance().getReference("users/customers").child(firebaseAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    try{
                        userType = user.getUserType();

                        if (user.getUserType().equals("cust")){
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserHome.class));
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

        }

        user = new User();

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
        else firebaseAuth.signInWithEmailAndPassword(email, password)
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
                                            startActivity(new Intent(getApplicationContext(), UserHome.class));
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
                });
    }

    public void linkUserToAppropriatePage(String userType){
        if(userType.equals("cust")){
            startActivity(new Intent(getApplicationContext(), UserHome.class));
        }
        else if(userType.equals("ret")){
            startActivity(new Intent(getApplicationContext(), RetailerHome.class));
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
            startActivity(new Intent(LogIn.this, CustSignUp.class));
        }
    }
}