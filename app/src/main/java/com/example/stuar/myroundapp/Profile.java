package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userEmail = findViewById(R.id.profileEmail);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn.class));
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userEmail.setText("Welcome" + firebaseUser.getEmail());


    }

    public void onLogOutBtnClick(View view){
        if(view.getId() == R.id.logOutBtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Profile.this, LogIn.class));
        }
    }
}
