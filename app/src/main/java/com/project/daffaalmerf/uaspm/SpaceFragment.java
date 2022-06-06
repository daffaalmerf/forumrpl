package com.project.daffaalmerf.uaspm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.project.daffaalmerf.uaspm.adapter.SpacePostAdapter;
import com.project.daffaalmerf.uaspm.databinding.FragmentSpaceBinding;

public class SpaceFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentSpaceBinding binding;
    private FirebaseDatabase mDatabase;
    FirebaseFirestore mSpaceFirestore;
    SpacePostAdapter spacePostAdapter;


    public SpaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSpaceBinding.inflate(inflater, container, false);
        final View spaceView = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mSpaceFirestore = FirebaseFirestore.getInstance();

        String uid = mAuth.getUid();

        binding.spaceNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newPostActivity = new Intent(getContext(), SpacePostActivity.class);
                newPostActivity.putExtra("uid", uid);
                startActivity(newPostActivity);

            }
        });

        Query query = mSpaceFirestore.collection("Space");

        FirestoreRecyclerOptions<spacePostModel> options = new FirestoreRecyclerOptions.Builder<spacePostModel>().setQuery(query, spacePostModel.class).build();

        spacePostAdapter = new SpacePostAdapter(options, getContext());

        binding.spaceListPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.spaceListPost.setAdapter(spacePostAdapter);
        binding.spaceListPost.setHasFixedSize(true);

        return spaceView;

    }

    @Override
    public void onStart() {
        super.onStart();
        spacePostAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        spacePostAdapter.stopListening();
    }

}