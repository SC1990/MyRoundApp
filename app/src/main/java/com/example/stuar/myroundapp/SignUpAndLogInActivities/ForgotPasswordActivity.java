package com.example.stuar.myroundapp.SignUpAndLogInActivities;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuar.myroundapp.CustomerActivities.CustomerHome;
import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {

    private String check = "";
    private TextView pageTitle, questionTitles;
    private EditText phone, question1, question2;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        check = getIntent().getStringExtra("check");
        pageTitle = findViewById(R.id.pageTitle);
        questionTitles = findViewById(R.id.title_questions);
        phone = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        saveBtn = findViewById(R.id.verify_btn);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        phone.setVisibility(View.GONE);

        if (check.equals("settings")) {

            retrieveAnswers();

            pageTitle.setText("Set Security Questions");
            questionTitles.setText("Please enter the following security questions");
            saveBtn.setText("save");

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();

                }

            });

        }
        else if (check.equals("login")) {
            phone.setVisibility(View.VISIBLE);
            saveBtn.setText("Verify");

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });
        }
    }

    private void verifyUser() {

        final String phoneNum = phone.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if(!phoneNum.equals("") || !answer1.equals("") || !answer2.equals("")){

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("users/customers")
                    .child(phoneNum);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        String userPhone = dataSnapshot.child("phone").getValue().toString();

                        if(dataSnapshot.hasChild("security_questions")){

                            String ans1 = dataSnapshot.child("security_questions").child("security_question1_answer").getValue().toString();
                            String ans2 = dataSnapshot.child("security_questions").child("security_question2_answer").getValue().toString();

                            if(!ans1.equals(answer1)){
                                Toast.makeText(ForgotPasswordActivity.this, "Try again, answer 1 wrong", Toast.LENGTH_SHORT).show();

                            }else if(!ans2.equals(answer2)){
                                Toast.makeText(ForgotPasswordActivity.this, "Try again, answer 2 wrong", Toast.LENGTH_SHORT).show();

                            }else{
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ForgotPasswordActivity.this);
                                newPassword.setBackgroundResource(R.drawable.edittext_border);
                                newPassword.setHint("Enter new password");
                                builder.setView(newPassword);

                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {

                                        if(!newPassword.getText().toString().equals("")){
                                            databaseReference.child("password").setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){
                                                                dialog.dismiss();
                                                                Toast.makeText(ForgotPasswordActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(ForgotPasswordActivity.this, LogIn.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();

                                    }
                                });

                                builder.show();
                            }

                        }else{
                            Toast.makeText(ForgotPasswordActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        Toast.makeText(ForgotPasswordActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{
            Toast.makeText(ForgotPasswordActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();

        }


    }

    private void setAnswers() {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if(question1.equals("") || question2.equals("")){
            Toast.makeText(ForgotPasswordActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();

        }else{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("users/customers")
                    .child(RememberMe.currentOnlineUser.getPhone());

            HashMap<String, Object> userMap = new HashMap<>();
            userMap. put("security_question1_answer", answer1);
            userMap. put("security_question2_answer", answer2);

            databaseReference.child("security_questions").updateChildren(userMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "answers saved", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ForgotPasswordActivity.this, CustomerHome.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

        }

    }

    private void retrieveAnswers(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users/customers")
                .child(RememberMe.currentOnlineUser.getPhone());

        databaseReference.child("security_questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String ans1 = dataSnapshot.child("security_question1_answer").getValue().toString();
                    String ans2 = dataSnapshot.child("security_question2_answer").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
