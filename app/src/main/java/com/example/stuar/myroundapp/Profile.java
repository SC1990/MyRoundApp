package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userEmail;

    private EditText nameET;
    private EditText addressET;
    private EditText townET;
    private EditText mobileNumET;

    DatabaseReference databaseReferenceUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userEmail = findViewById(R.id.profileEmail);
        firebaseAuth = FirebaseAuth.getInstance();

        nameET = findViewById(R.id.fullNameET);
        addressET = findViewById(R.id.addressET);
        townET = findViewById(R.id.townET);
        mobileNumET = findViewById(R.id.mobileNumET);

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("customers");


        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn.class));
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userEmail.setText("Welcome " + firebaseUser.getEmail());


    }

    private void saveUserInfo(){
        String name = nameET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String town = townET.getText().toString().trim();
        String num = mobileNumET.getText().toString().trim();


        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(address) || !TextUtils.isEmpty(town) || !TextUtils.isEmpty(num)){
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            String id = firebaseUser.getUid();
            User user = new User(name, address, town, num, id);

            databaseReferenceUsers.child(id).setValue(user);

            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show();
        }




    }


    public void onSaveInfoBtnClick(View view){
        if(view.getId() == R.id.saveInfoBtn){
            saveUserInfo();
        }
    }

    public void onLogOutBtnClick(View view){
        if(view.getId() == R.id.logOutBtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Profile.this, LogIn.class));
        }
    }
}
