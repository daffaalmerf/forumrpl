package com.project.daffaalmerf.uaspm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.daffaalmerf.uaspm.LoadingDialog;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.databinding.ActivityRegisterBinding;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String regexName = "^[a-zA-z ]+$";

                final String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                        ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

                final String reg_name = binding.regInputName.getText().toString();
                final String reg_email = binding.regInputEmail.getText().toString();
                final String reg_password = binding.regInputPassword.getText().toString();
                final String reg_confirm = binding.regInputConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(reg_name) || !(reg_name).matches(regexName)) {
                    binding.regInputName.setError("Invalid name");
                }
                if (TextUtils.isEmpty(reg_email) || !(reg_email.matches(regexEmail))) {
                    binding.regInputEmail.setError("Invalid e-mail");
                }
                if (TextUtils.isEmpty(reg_password)) {
                    binding.regInputPassword.setError("Invalid password");
                }
                if (TextUtils.isEmpty(reg_confirm) || !reg_confirm.equals(reg_password)){
                    binding.regInputConfirmPassword.setError("Invalid password confirmation");
                }
                if (!TextUtils.isEmpty(reg_name) && reg_name.matches(regexName) && !TextUtils.isEmpty(reg_email) &&
                        reg_email.matches(regexEmail) && !TextUtils.isEmpty(reg_password) &&
                        !TextUtils.isEmpty(reg_confirm) && reg_password.equals(reg_confirm)) {

                    LoadingDialog loadingDialog = new LoadingDialog(RegisterActivity.this);

                    loadingDialog.startDialog();

                    mAuth.createUserWithEmailAndPassword(reg_email, reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();

                                mDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference mUserDatabase = mDatabase.getReference().child("Users").child(uid);

                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("username", reg_name);
                                userMap.put("email", reg_email);
                                userMap.put("thumbnail", "default");
                                mUserDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            loadingDialog.dismissDialog();

                                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        Intent homeIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(homeIntent);
                                                        finish();

                                                        Toast.makeText(getApplicationContext(), "Account Successfully Register", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        loadingDialog.dismissDialog();

                                                        Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_SHORT).show();

                                                    }

                                                }
                                            });

                                        }
                                    }

                                });

                            } else {

                                Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }

            }
        });

        binding.regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(returnLoginIntent);
                finish();

            }
        });

    }
}