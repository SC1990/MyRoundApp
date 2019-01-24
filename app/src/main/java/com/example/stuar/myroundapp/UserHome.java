package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHome extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private TextView userEmailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);

        userEmailET = findViewById(R.id.userEmailET);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
           finish();
           startActivity(new Intent(getApplicationContext(), LogIn.class));
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userEmailET.setText("Welcome " + firebaseUser.getEmail());
    }

    public void onDetailsBtnClick(View view){
        if(view.getId() == R.id.myDetailsBtn){
            retrieveUserInfo();
        }
    }

    private void retrieveUserInfo() {
        startActivity(new Intent(UserHome.this, MyDetails.class));
    }


}
