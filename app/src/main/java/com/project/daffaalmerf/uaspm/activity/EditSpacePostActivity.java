package com.project.daffaalmerf.uaspm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.project.daffaalmerf.uaspm.LoadingDialog;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.databinding.ActivityEditSpacePostBinding;
import com.project.daffaalmerf.uaspm.databinding.ActivitySpacePostBinding;

import java.util.HashMap;
import java.util.Map;

public class EditSpacePostActivity extends AppCompatActivity {

    private ActivityEditSpacePostBinding binding;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_space_post);

        binding = ActivityEditSpacePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = getIntent().getStringExtra("uid");
        String postId = getIntent().getStringExtra("postId");
        String category = getIntent().getStringExtra("category");
        String content = getIntent().getStringExtra("content");

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mUserDatabase = mDatabase.getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();
                binding.spaceEditProfilePostName.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] categories = getResources().getStringArray(R.array.categoryList);

        ArrayAdapter adapter = new ArrayAdapter(EditSpacePostActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spaceEditCategory.setAdapter(adapter);
        int categoryIndex = adapter.getPosition(category);
        binding.spaceEditCategory.setSelection(categoryIndex);

        binding.spaceEditText.setText(content);

        binding.spaceEditCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final String selectedCategory = categories[position];

                binding.spaceEditPostUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String spaceContent = binding.spaceEditText.getText().toString();

                        if (TextUtils.isEmpty(selectedCategory)) {

                            Toast.makeText(EditSpacePostActivity.this, "Please Select a Post Category", Toast.LENGTH_SHORT).show();

                        }

                        if (TextUtils.isEmpty(spaceContent)) {

                            Toast.makeText(EditSpacePostActivity.this, "Please Describe Your Post", Toast.LENGTH_SHORT).show();

                        }

                        if (!TextUtils.isEmpty(selectedCategory) && !TextUtils.isEmpty(spaceContent)) {


                            final LoadingDialog loadingDialog = new LoadingDialog(EditSpacePostActivity.this);

                            loadingDialog.startDialog();

                            final FirebaseFirestore mAddPostFirestore = FirebaseFirestore.getInstance();
                            final Map<String, Object> post = new HashMap<>();
                            post.put("category", selectedCategory);
                            post.put("content", spaceContent);
                            mAddPostFirestore.collection("Space").document(postId).update(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        loadingDialog.dismissDialog();

                                        Toast.makeText(EditSpacePostActivity.this, "Post Successfully Shared!", Toast.LENGTH_SHORT).show();
                                        Intent successUpload = new Intent(EditSpacePostActivity.this, HomeActivity.class);
                                        startActivity(successUpload);
                                        finish();

                                    } else {

                                        loadingDialog.dismissDialog();

                                        Toast.makeText(EditSpacePostActivity.this, "Failed to Update Post", Toast.LENGTH_SHORT).show();

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