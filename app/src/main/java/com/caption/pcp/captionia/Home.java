package com.caption.pcp.captionia;

/**
 * Created by PCP on 24-12-2018.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Home extends Fragment {

   String name;

    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Category> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;



    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View rootView = inflater.inflate(R.layout.home, container, false);





        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading");

        progressDialog.show();




        databaseReference = FirebaseDatabase.getInstance().getReference(InsertCategory.Database_Path);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Category category = dataSnapshot.getValue(Category.class);

                    list.add(category);
                }

                adapter = new RecyclerViewAdapter(getContext(), list);
//                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);



                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


        // Inflate the layout for this fragment
        return rootView;






    }



}
