package com.project.daffaalmerf.uaspm.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.daffaalmerf.uaspm.LoadingDialog;
import com.project.daffaalmerf.uaspm.WrapContentLinearLayoutManager;
import com.project.daffaalmerf.uaspm.activity.LoginActivity;
import com.project.daffaalmerf.uaspm.adapter.SpacePostAdapter;
import com.project.daffaalmerf.uaspm.databinding.FragmentProfileBinding;
import com.project.daffaalmerf.uaspm.model.SpacePostModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ProfileFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private FirebaseDatabase mDatabase;
    private FirebaseFirestore mFirestore;
    private SpacePostAdapter spacePostAdapter;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        final View profileView = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        String uid = mAuth.getUid();
        DatabaseReference mUserDatabase = mDatabase.getReference().child("Users").child(uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String username = snapshot.child("username").getValue().toString();
                binding.profileUsername.setText(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mFirestore = FirebaseFirestore.getInstance();

        Query query = mFirestore.collection("Space").whereEqualTo("by",uid).orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<SpacePostModel> options = new FirestoreRecyclerOptions.Builder<SpacePostModel>().setQuery(query, SpacePostModel.class).build();

        spacePostAdapter = new SpacePostAdapter(options, getContext());

        binding.profilePostList.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.profilePostList.setAdapter(spacePostAdapter);

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm Log Out");
                builder.setMessage("Do you wish to log out from this account?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mAuth.signOut();
                                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                                startActivity(logoutIntent);
                                getActivity().finish();


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

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] options = {"View Profile Picture", "Change Profile Picture"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:

                                break;
                            case 1:

                                break;
                        }
                    }
                });

            }
        });


        return profileView;
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