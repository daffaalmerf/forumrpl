package com.project.daffaalmerf.uaspm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.daffaalmerf.uaspm.databinding.FragmentProfileBinding;
import com.project.daffaalmerf.uaspm.databinding.FragmentSpaceBinding;

public class SpaceFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentSpaceBinding binding;
    private FirebaseDatabase mDatabase;

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

        String uid = mAuth.getUid();

        binding.spaceNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newPostActivity = new Intent(getContext(), SpacePostActivity.class);
                newPostActivity.putExtra("uid", uid);
                startActivity(newPostActivity);

            }
        });

        return spaceView;

    }
}