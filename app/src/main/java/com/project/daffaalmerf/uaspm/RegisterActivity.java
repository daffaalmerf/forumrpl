package com.project.daffaalmerf.uaspm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

                final String reg_name = binding.regInputName.getText().toString();
                final String reg_email = binding.regInputEmail.getText().toString();
                final String reg_password = binding.regInputPassword.getText().toString();
                final String reg_confirm = binding.regInputConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(reg_name)) {
                    binding.regInputName.setError("Invalid name");
                }
                if (TextUtils.isEmpty(reg_email)) {
                    binding.regInputEmail.setError("Invalid e-mail");
                }
                if (TextUtils.isEmpty(reg_password)) {
                    binding.regInputPassword.setError("Invalid password");
                }
                if (TextUtils.isEmpty(reg_confirm) || !reg_confirm.equals(reg_password)){
                    binding.regInputConfirmPassword.setError("Invalid password confirmation");
                }
                if (!TextUtils.isEmpty(reg_name) && !TextUtils.isEmpty(reg_email) && !TextUtils.isEmpty(reg_password) &&
                        !TextUtils.isEmpty(reg_confirm) && reg_password.equals(reg_confirm)) {
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

                                            Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(homeIntent);
                                            finish();

                                        } else {

                                            Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();

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

    }
}