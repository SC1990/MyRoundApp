package com.example.stuar.myroundapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stuar.myroundapp.DataRetrieval.RememberMe;
import com.example.stuar.myroundapp.Models.NewBeer;
import com.example.stuar.myroundapp.NewBeerAdapter;
import com.example.stuar.myroundapp.R;
import com.example.stuar.myroundapp.ViewHolders.NewBeerViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import smartadapter.SmartRecyclerAdapter;

public class AllBeersFragment extends Fragment {

    DatabaseReference databaseReference;

    NewBeerAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.all_beers_layout, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.all_beers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {

                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        databaseReference = FirebaseDatabase.getInstance().getReference("new_beers");



                        FirebaseRecyclerOptions<NewBeer> options =
                                new FirebaseRecyclerOptions.Builder<NewBeer>()
                                        .setQuery(databaseReference.orderByChild("user").equalTo(RememberMe.currentOnlineUser.getName()), NewBeer.class)
                                        .build();


                        FirebaseRecyclerAdapter<NewBeer, NewBeerViewHolder> adapter =
                                new FirebaseRecyclerAdapter<NewBeer, NewBeerViewHolder>(options) {
                                    @Override
                                    protected void onBindViewHolder(@NonNull NewBeerViewHolder holder, int position, @NonNull final NewBeer model) {

                                        holder.bName.setText(model.getName());
                                        holder.bStyle.setText(model.getStyle());
                                        holder.bABV.setText(model.getAbv());
                                        Picasso.get().load(model.getImage()).into(holder.bImage);


                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                            }
                                        });
                                    }



                                    @NonNull
                                    @Override
                                    public NewBeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                                    {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_beer_row, parent, false);
                                        NewBeerViewHolder holder = new NewBeerViewHolder(view);
                                        return holder;

                                    }
                                };

                        recyclerView.setAdapter(adapter);
                        adapter.startListening();

                    }
                });
            }
        }).start();

        return view;
    }


}
