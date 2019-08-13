package com.example.stuar.myroundapp.Other;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stuar.myroundapp.Models.ImageUpload;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.RetailerActivities.RetailerProductsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageUploader extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private EditText editText;
    private Uri uri;

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DB_PATH = "prod_image";
    public static final int REQUEST_CODE = 1234;

    String retId;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uploader);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() != null){
            retId = firebaseUser.getUid();

        }

        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DB_PATH);

        imageView = findViewById(R.id.image);
        editText = findViewById(R.id.txt);
    }

    public void browsImg(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImgExtension(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void upImg(View view) {

        if(uri != null){

            Toast.makeText(ImageUploader.this, "uploading..", Toast.LENGTH_SHORT).show();

            final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImgExtension(uri));

            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ImageUploader.this, "success", Toast.LENGTH_SHORT).show();
                        //ref.getDownloadUrl may not work..?

                        ImageUpload imageUpload = new ImageUpload(editText.getText().toString(), uri.toString(), retId);
                        Toast.makeText(ImageUploader.this, ref.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(imageUpload);

                        startActivity(new Intent(getApplicationContext(), RetailerProductsActivity.class));

                    }
                })
                    .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(ImageUploader.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });


        }

        else{
            Toast.makeText(ImageUploader.this, "select image", Toast.LENGTH_SHORT).show();

        }
    }

}
