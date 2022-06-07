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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.daffaalmerf.uaspm.R;
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

                final String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                        ")*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

                String login_email = binding.loginInputEmail.getText().toString();
                String login_password = binding.loginInputPassword.getText().toString();

                if (TextUtils.isEmpty(login_email) || !(login_email.matches(regexEmail))) {
                    binding.loginInputEmail.setError("Invalid e-mail");
                }
                if (TextUtils.isEmpty(login_password)) {
                    binding.loginInputPassword.setError("Invalid password");
                }

                if (!TextUtils.isEmpty(login_email) && !TextUtils.isEmpty(login_password) && login_email.matches(regexEmail)) {

                    mAuth.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                if(mAuth.getCurrentUser().isEmailVerified()){

                                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(homeIntent);
                                    finish();

                                } else {

                                    Toast.makeText(getApplicationContext(), "Verify Your E-mail Before Logging In. Check Your Inbox.", Toast.LENGTH_SHORT).show();

                                }

                            } else {

                                Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }

            }
        });

        binding.loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent forgetPasswordIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetPasswordIntent);

            }
        });

    }
}