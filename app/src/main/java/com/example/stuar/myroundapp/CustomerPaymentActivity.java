
package com.example.stuar.myroundapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import java.util.Currency;

import static java.security.AccessController.getContext;

public class CustomerPaymentActivity extends AppCompatActivity {

    private Card card;
    private Button payBtn;
    private String uId = "";
    private double amount = 12.50;

    //only have the power to create tokens
    private String pk = "pk_test_Qz6xxzsbKrTinqluYMFxitQW005xGSAkr3";

    private CardMultilineWidget mCardMultilineWidget;

    private Token token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mCardMultilineWidget = findViewById(R.id.card_input_widget);

        payBtn = findViewById(R.id.pay_btn);

        amount = amount * 100;

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCardMultilineWidget.getCard()!= null){

                    String cvv= mCardMultilineWidget.getCard().getCVC();
                    int exp= mCardMultilineWidget.getCard().getExpMonth();
                    int exp_year = mCardMultilineWidget.getCard().getExpYear();
                    String card_num= mCardMultilineWidget.getCard().getNumber();

                    card = new Card(card_num, exp, exp_year, cvv);
                    card.setCurrency("EUR");

                    if(card.validateCard()){
                        //create token
                        // unique string that Stripe generates to refer to a user’s sensitive credit information

                        final Stripe stripe = new Stripe(getApplicationContext(), "pk_test_Qz6xxzsbKrTinqluYMFxitQW005xGSAkr3");
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(final Token token) {
                                        // Send token to your server
                                        final DatabaseReference tokenRef;
                                        tokenRef = FirebaseDatabase.getInstance().getReference()
                                                .child("stripe_customers")
                                                .child(RememberMe.currentOnlineUser.getUserId())
                                                .child("tokens");

                                        tokenRef.setValue(token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                tokenRef.child("amount").setValue(amount);
                                                Toast.makeText(getApplicationContext(), "Token successfully added to database", Toast.LENGTH_SHORT).show();
                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                    public void onError(Exception error) {
                                        // Show localized error message
                                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );


                    }
                    else if (!card.validateNumber()) {
                        handleError("The card number that you entered is invalid");
                    }
                    else if (!card.validateExpiryDate()) {
                        handleError("expiration date invalid");
                    }
                    else if (!card.validateCVC()) {
                        handleError("CVC invalid");
                    }
                    else {
                        handleError("The card details that you entered are invalid");
                    }
                }

            }
        });

    }



    private void handleError(String s) {
        Toast.makeText(CustomerPaymentActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}