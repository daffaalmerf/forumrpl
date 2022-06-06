package com.project.daffaalmerf.uaspm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.daffaalmerf.uaspm.R;
import com.project.daffaalmerf.uaspm.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        NavController navController = Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(binding.homeNavigation, navController);

        if(currentUser == null){
            Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

    }
}