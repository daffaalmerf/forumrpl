package com.project.daffaalmerf.uaspm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.daffaalmerf.uaspm.databinding.ActivityLoginBinding;
import com.project.daffaalmerf.uaspm.databinding.ActivitySpacePostBinding;

import java.util.HashMap;
import java.util.Map;

public class SpacePostActivity extends AppCompatActivity {

    private ActivitySpacePostBinding binding;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid = getIntent().getStringExtra("uid");

        binding = ActivitySpacePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mUserDatabase = mDatabase.getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();
                binding.spaceProfilePostName.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] categories = getResources().getStringArray(R.array.categoryList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spaceNewCategory.setAdapter(adapter);

        binding.spaceNewCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                final String selectedCategory = categories[position];

                binding.spaceNewPostUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String spaceContent = binding.spaceNewText.getText().toString();

                        if(TextUtils.isEmpty(selectedCategory)){

                            Toast.makeText(SpacePostActivity.this, "Please Select a Post Category", Toast.LENGTH_SHORT).show();

                        }

                        if(TextUtils.isEmpty(spaceContent)){

                            Toast.makeText(SpacePostActivity.this, "Please Describe Your Post", Toast.LENGTH_SHORT).show();

                        }

                        if(!TextUtils.isEmpty(selectedCategory) && !TextUtils.isEmpty(spaceContent)){


                            final LoadingDialog loadingDialog = new LoadingDialog(SpacePostActivity.this);

                            loadingDialog.startDialog();

                            final FirebaseFirestore mAddPostFirestore = FirebaseFirestore.getInstance();
                            final Map<String, Object> post = new HashMap<>();
                            post.put("by", uid);
                            post.put("timestamp", FieldValue.serverTimestamp());
                            post.put("category", selectedCategory);
                            post.put("content", spaceContent);
                            mAddPostFirestore.collection("Space").document().set(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {

                                        loadingDialog.dismissDialog();

                                        Toast.makeText(SpacePostActivity.this, "Post Successfully Shared!", Toast.LENGTH_SHORT).show();
                                        Intent successUpload = new Intent(SpacePostActivity.this, HomeActivity.class);
                                        startActivity(successUpload);
                                        finish();

                                    } else {

                                        loadingDialog.dismissDialog();

                                        Toast.makeText(SpacePostActivity.this, "Failed to Share Post", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}