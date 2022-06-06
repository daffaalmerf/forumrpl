package com.project.daffaalmerf.uaspm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.databinding.ActivitySpacePostBinding;
import com.project.daffaalmerf.uaspm.databinding.ActivityViewSpacePostBinding;

public class ViewSpacePostActivity extends AppCompatActivity {

    private ActivityViewSpacePostBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid = getIntent().getStringExtra("uid");
        String postId = getIntent().getStringExtra("post_id");
        String category = getIntent().getStringExtra("category");
        String content = getIntent().getStringExtra("content");
        String date = getIntent().getStringExtra("date");

        binding = ActivityViewSpacePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mUserDatabase = mDatabase.getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();

                binding.spaceViewProfilePostName.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.spaceViewCategory.setText(category);
        binding.spaceViewContent.setText(content);
        binding.spaceViewDate.setText(date);



    }
}