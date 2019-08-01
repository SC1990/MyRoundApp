package com.example.stuar.myroundapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddNewBeerActivity extends AppCompatActivity {

    private String downloadImageUrl, uniqueKey;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private ImageView beerImg;
    DatabaseReference newBeersRef;
    StorageReference newBeersStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_beer);

        Paper.init(this);

        newBeersRef = FirebaseDatabase.getInstance().getReference().child("new_beers");
        newBeersStorageRef = FirebaseStorage.getInstance().getReference().child("MyBeer Images");

        beerImg = findViewById(R.id.select_beer_img);
    }

    public void onNewBeerImgBtnClick(View view) {
        if(view.getId() == R.id.select_beer_img){
            OpenGallery();
        }
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
            beerImg.setImageURI(imageUri);
        }
    }

    public void addNewBeerBtnClick(View view) {
        if(view.getId() == R.id.add_new_beer_btn){
            addNewBeer();
        }
    }

    private void addNewBeer() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        uniqueKey = currentDateandTime + "_" + RememberMe.currentOnlineUser.getPhone();
        //----------------------------------------------------------

        //store in Firebase Storage
        final StorageReference filePath = newBeersStorageRef.child(imageUri.getLastPathSegment() + uniqueKey + ".jpg");
        //----------------------------------------------------------

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddNewBeerActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                //----------------------------------------------------------

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AddNewBeerActivity.this, "Beer Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddNewBeerActivity.this, "got the image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveInfoToDatabase();

                            //----------------------------------------------------------
                        }
                    }
                });
            }
        });
    }

    private void SaveInfoToDatabase() {
        EditText nameET = findViewById(R.id.beer_name);
        EditText styleET = findViewById(R.id.beer_style);
        EditText abvET = findViewById(R.id.beer_abv);
        EditText notesET = findViewById(R.id.notes);

        HashMap<String, Object> newBeersMap = new HashMap<>();
        newBeersMap.put("user", RememberMe.currentOnlineUser.getName());
        newBeersMap.put("name", nameET.getText().toString());
        newBeersMap.put("style", styleET.getText().toString());
        newBeersMap.put("abv", abvET.getText().toString());
        newBeersMap.put("notes", notesET.getText().toString());
        newBeersMap.put("image", downloadImageUrl);
        newBeersMap.put("favourites", "false");
        newBeersMap.put("beerId", uniqueKey);

        newBeersRef.child(uniqueKey).updateChildren(newBeersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(AddNewBeerActivity.this, MyBeersActivity.class);
                            startActivity(intent);
                            Toast.makeText(AddNewBeerActivity.this, "New beer added..", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            String message = task.getException().toString();
                            Toast.makeText(AddNewBeerActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


