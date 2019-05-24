package com.example.stuar.myroundapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.stuar.myroundapp.Models.Product;
import com.example.stuar.myroundapp.RetailerActivities.RetailerProductsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MaintainProductsActivity extends AppCompatActivity {

    private Button saveButton, rmvBtn;
    private CircleImageView prodImg;
    private EditText pName, pDesc, pPrice;

    private String pId = "";
    private String rId = "";
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_products);

        saveButton = findViewById(R.id.save_changes_btn);
        rmvBtn = findViewById(R.id.remove_btn);
        prodImg = findViewById(R.id.img_edit);
        pName = findViewById(R.id.name_edit);
        pDesc = findViewById(R.id.desc_edit);
        pPrice = findViewById(R.id.price_edit);

        pId = getIntent().getStringExtra("pId");
        rId = getIntent().getStringExtra("rId");
        image = getIntent().getStringExtra("image");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProduct();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(pId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    Product product = dataSnapshot.getValue(Product.class);
                    pName.setText(product.getpName());
                    pDesc.setText(product.getDescription());
                    pPrice.setText(product.getPrice());
                    Picasso.get().load(product.getpImage()).into(prodImg);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void removeProduct() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        databaseReference.child(pId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "removed", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MaintainProductsActivity.this , RetailerProductsActivity.class);
                            startActivity(intent);
                        }
                    }
                });


    }

    private void saveChanges() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");

        HashMap<String, Object> prodMap = new HashMap<>();
        prodMap.put("pName", pName.getText().toString());
        prodMap.put("price", pPrice.getText().toString());
        prodMap.put("description", pDesc.getText().toString());

        ref.child(pId).updateChildren(prodMap);

        startActivity(new Intent(MaintainProductsActivity.this, RetailerProductsActivity.class));
        Toast.makeText(MaintainProductsActivity.this, "Product Info updated successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }
}
