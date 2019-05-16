package com.example.stuar.myroundapp.RetailerActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class AddNewProductActivity extends AppCompatActivity {

    private String description, price, name, volume, saveCurrentDate, saveCurrentTime;
    private EditText productName, productDesc, productPrice, prodVol;
    private ImageView productImg;
    private Button addNewProductButton;
    //------------------------------------------------
    private String retId;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    //------------------------------------------------
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        productName = (EditText) findViewById(R.id.cv_product_name);
        productDesc = (EditText) findViewById(R.id.product_description);
        productPrice = (EditText) findViewById(R.id.cv_product_price);
        prodVol = (EditText) findViewById(R.id.product_volume);
        productImg = (ImageView) findViewById(R.id.select_product_image);
        addNewProductButton = (Button) findViewById(R.id.add_new_product);

        //------------------------------------------------

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        //------------------------------------------------

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Paper.init(this);
        retId = RememberMe.currentOnlineUser.getUserId();
        //------------------------------------------------

        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        //------------------------------------------------
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            imageUri = data.getData();
            productImg.setImageURI(imageUri);
        }
    }

    public void onAddProductClick(View view) {
        if(view.getId() == R.id.add_new_product){
            ValidateProductInfo();
        }
    }

    private void ValidateProductInfo() {
        description = productDesc.getText().toString();
        price = productPrice.getText().toString();
        name = productName.getText().toString();
        volume = prodVol.getText().toString();


        if (imageUri == null) {
            Toast.makeText(this, "Product image Required...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Product description Required...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Product price Required...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Product name Required...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(volume)) {
            Toast.makeText(this, "Product Volume Required...", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInfo();
        }
    }

    private void StoreProductInfo() {
        Toast.makeText(this, "Adding Product...", Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-mm-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm-ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //unique product key generation
        productRandomKey = saveCurrentDate + saveCurrentTime + retId;
        //----------------------------------------------------------

        //store in Firebase Storage
        final StorageReference filePath = ProductImagesRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
        //----------------------------------------------------------

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                //----------------------------------------------------------

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AddNewProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                //----------------------------------------------------------

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                        //----------------------------------------------------------
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AddNewProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();

                            //----------------------------------------------------------
                        }
                    }
                });
            }
        });
    }


    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pId", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("pImage", downloadImageUrl);
        productMap.put("price", price);
        productMap.put("pName", name);
        productMap.put("volume", volume);
        productMap.put("retId", retId);


        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(AddNewProductActivity.this, RetailerProductsActivity.class);
                            startActivity(intent);
                            Toast.makeText(AddNewProductActivity.this, "Product added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            String message = task.getException().toString();
                            Toast.makeText(AddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
