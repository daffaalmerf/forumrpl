package com.project.daffaalmerf.uaspm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.daffaalmerf.uaspm.LoadingDialog;
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

        mAuth = FirebaseAuth.getInstance();
        String current_uid = mAuth.getCurrentUser().getUid();

        if(current_uid.equals(uid)){

            binding.spaceViewEdit.setVisibility(View.VISIBLE);
            binding.spaceViewDelete.setVisibility(View.VISIBLE);

            binding.spaceViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewSpacePostActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Edit Confirmation");
                    builder.setMessage("Are you sure you want to edit this post?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent updatePostIntent = new Intent(ViewSpacePostActivity.this, EditSpacePostActivity.class);
                                    updatePostIntent.putExtra("uid", uid);
                                    updatePostIntent.putExtra("postId", postId);
                                    updatePostIntent.putExtra("category", category);
                                    updatePostIntent.putExtra("content", content);
                                    startActivity(updatePostIntent);


                                }
                            });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

            binding.spaceViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewSpacePostActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Are you sure you want to delete this post?");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    LoadingDialog loadingDialog = new LoadingDialog(ViewSpacePostActivity.this);

                                    loadingDialog.startDialog();

                                    FirebaseFirestore mPostFirestore = FirebaseFirestore.getInstance();

                                    mPostFirestore.collection("Space").document(postId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                loadingDialog.dismissDialog();

                                                Intent returnIntent = new Intent(ViewSpacePostActivity.this, HomeActivity.class);
                                                startActivity(returnIntent);

                                            } else {

                                                loadingDialog.dismissDialog();

                                                Toast.makeText(ViewSpacePostActivity.this, "Failed to Delete Post", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                                }
                            });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

        }



    }
}