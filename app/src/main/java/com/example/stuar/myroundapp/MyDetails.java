package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.stuar.myroundapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyDetails extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    private EditText nameET;
    private EditText addressET;
    private EditText townET;
    private EditText mobileNumET;

    private String userId;

    DatabaseReference databaseReferenceUsers;
    FirebaseUser firebaseUser;

    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails);


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        nameET = findViewById(R.id.fullNameET);
        addressET = findViewById(R.id.addressET);
        townET = findViewById(R.id.townET);
        mobileNumET = findViewById(R.id.mobileNumET);

        userId = firebaseUser.getUid();

        loadDetails();

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users/customers");

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LogIn.class));
        }



    }

    public void loadDetails() {

        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("users/customers").child(userId);
        // Attach a listener to read the data at your profile reference
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                try{
                    nameET.setText(user.getName());
                    addressET.setText(user.getAddress());
                    //townET.setText(user.getTown());
                    mobileNumET.setText(user.getPhone());
                }catch (Exception e){
                    NullPointerException nullPointerException;
                    nameET.setText("");
                    addressET.setText("");
                    townET.setText("");
                    mobileNumET.setText("");
                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }


    private void saveUserInfo(){
      /*  String name = nameET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String town = townET.getText().toString().trim();
        String num = mobileNumET.getText().toString().trim();


        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(address) || !TextUtils.isEmpty(town) || !TextUtils.isEmpty(num)){
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            String id = firebaseUser.getUid();
            User user = new User(name, address, town, num, id);
            user.setUserType("cust");

            databaseReferenceUsers.child(id).setValue(user);

            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show();
        }*/




    }


    public void onSaveInfoBtnClick(View view){
        if(view.getId() == R.id.saveInfoBtn){
            saveUserInfo();
        }
    }


}
