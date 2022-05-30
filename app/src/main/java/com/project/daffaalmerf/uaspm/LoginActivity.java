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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.daffaalmerf.uaspm.databinding.ActivityHomeBinding;
import com.project.daffaalmerf.uaspm.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);

            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login_email = binding.loginInputEmail.getText().toString();
                String login_password = binding.loginInputPassword.getText().toString();

                if (TextUtils.isEmpty(login_email)) {
                    binding.loginInputEmail.setError("Invalid e-mail");
                }
                if (TextUtils.isEmpty(login_password)) {
                    binding.loginInputPassword.setError("Invalid password");
                }

                if (!TextUtils.isEmpty(login_email) && !TextUtils.isEmpty(login_password)) {

                    mAuth.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }
}