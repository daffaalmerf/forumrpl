package com.project.daffaalmerf.uaspm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.daffaalmerf.uaspm.LoadingDialog;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.databinding.ActivityForgetPasswordBinding;
import com.project.daffaalmerf.uaspm.databinding.ActivityHomeBinding;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance();
        
        binding.forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadingDialog loadingDialog = new LoadingDialog(ForgetPasswordActivity.this);

                final String email = binding.forgetInputEmail.getText().toString();

                String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                        ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

                if(TextUtils.isEmpty(email) || !email.matches(regexEmail)) {

                    binding.forgetInputEmail.setError("Invalid e-mail");

                }

                if(!TextUtils.isEmpty(email) && email.matches(regexEmail)){

                    loadingDialog.startDialog();

                    DatabaseReference mUserDatabase = mDatabase.getReference("Users");
                    Query emailQuery = mUserDatabase.orderByChild("email").equalTo(email);

                    emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()){

                                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            loadingDialog.dismissDialog();

                                            Toast.makeText(ForgetPasswordActivity.this, "Password Reset Has Been Sent to Your E-mail", Toast.LENGTH_SHORT).show();
                                            Intent returnLogin = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                            startActivity(returnLogin);
                                            finish();

                                        } else {

                                            loadingDialog.dismissDialog();

                                            Toast.makeText(ForgetPasswordActivity.this, "Password Reset Failed", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                            } else {

                                loadingDialog.dismissDialog();

                                binding.forgetInputEmail.setError("E-mail is Not Registered");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        binding.forgetLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnLoginIntent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(returnLoginIntent);
                finish();

            }
        });

    }
}