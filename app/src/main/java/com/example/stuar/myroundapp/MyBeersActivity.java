package com.example.stuar.myroundapp;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Fragments.AllBeersFragment;
import com.example.stuar.myroundapp.Fragments.FavouriteBeersFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MyBeersActivity extends AppCompatActivity {

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String imageURL, downloadImageUrl, uniqueKey;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private ImageView beerImg;
    private FloatingActionButton fab;
    private AlertDialog dialogBuilder;
    private LayoutInflater inflater;
    private View dialogView;
    DatabaseReference newBeersRef;
    StorageReference newBeersStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beers2);

        fab = (FloatingActionButton) findViewById(R.id.fab_new_beers);

          newBeersRef = FirebaseDatabase.getInstance().getReference().child("new_beers");
          newBeersStorageRef = FirebaseStorage.getInstance().getReference().child("MyBeer Images");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        fab.setOnClickListener(view -> {

            dialogBuilder = new AlertDialog.Builder(MyBeersActivity.this).create();
            inflater = MyBeersActivity.this.getLayoutInflater();
            dialogView = inflater.inflate(R.layout.new_beer, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.show();

            beerImg = findViewById(R.id.select_beer_img);
            beerImg.setOnClickListener(v -> OpenGallery());
        });

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
                Toast.makeText(MyBeersActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                //----------------------------------------------------------

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(MyBeersActivity.this, "Beer Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MyBeersActivity.this, "got the image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveInfoToDatabase();

                            //----------------------------------------------------------
                        }
                    }
                });
            }
        });
    }

    private void SaveInfoToDatabase() {
        EditText nameET = dialogView.findViewById(R.id.beer_name);
        EditText styleET = dialogView.findViewById(R.id.beer_style);
        EditText abvET = dialogView.findViewById(R.id.beer_abv);
        EditText notesET = dialogView.findViewById(R.id.notes);

        HashMap<String, Object> newBeersMap = new HashMap<>();
        newBeersMap.put("user", RememberMe.currentOnlineUser.getName());
        newBeersMap.put("name", nameET.getText());
        newBeersMap.put("style", styleET.getText());
        newBeersMap.put("abv", abvET.getText());
        newBeersMap.put("notes", notesET.getText());
        newBeersMap.put("image", downloadImageUrl);

        newBeersRef.child(RememberMe.currentOnlineUser.getName()).child(uniqueKey).updateChildren(newBeersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(MyBeersActivity.this, MyBeersActivity.class);
                            startActivity(intent);
                            Toast.makeText(MyBeersActivity.this, "New beer added..", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            String message = task.getException().toString();
                            Toast.makeText(MyBeersActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_beers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_beers, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AllBeersFragment();
                    break;
                case 1:
                    fragment = new FavouriteBeersFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All Beers";
                case 1:
                    return "My Favourites";
            }
            return null;
        }
    }
}
