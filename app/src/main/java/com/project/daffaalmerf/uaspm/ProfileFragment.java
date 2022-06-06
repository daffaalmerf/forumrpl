package com.project.daffaalmerf.uaspm;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.daffaalmerf.uaspm.databinding.FragmentProfileBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FragmentProfileBinding binding;
    private FirebaseDatabase mDatabase;

    private static final int GALLERY_PICK = 1;

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

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(logoutIntent);
                getActivity().finish();

            }
        });

//        binding.profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final CharSequence[] options = {"View Profile Picture", "Change Profile Picture"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Options");
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i) {
//                            case 0:
//
//                                break;
//                            case 1:
//
//                                Intent galleryIntent = new Intent();
//                                galleryIntent.setType("image/*");
//                                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_PICK);
//
//                                break;
//                        }
//                    }
//                });
//
//            }
//        });


        return profileView;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                mCropImageUri = result.getOriginalUri();
//
//                createImage();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                AppLogger.e(result.getError().getMessage());
//            }
//        }
//
//        if (requestCode == PermissionCheckUtils.LOCATION_PERMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
//            getLocation();
//        }
}