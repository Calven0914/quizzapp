package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment.databinding.ActivityDashboardBinding;

import android.os.Bundle;
import android.content.Intent; // Import Intent
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;

    private static final String USERNAME_KEY = "USERNAME";
    private static final String DEFAULT_USERNAME = "Unknown";

    private String loggedInUsername; // Declare loggedInUsername variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new HomeFragment());

        // Retrieve the username passed from the login page
        Intent intent = getIntent();
        if(intent!= null && intent.hasExtra(USERNAME_KEY)) {
            loggedInUsername = intent.getStringExtra(USERNAME_KEY);
        } else {
            // Handle case where username is not passed properly
            loggedInUsername = DEFAULT_USERNAME;
            // You could also show an error message or log an error here
        }

        // Set the item selected listener for bottom navigation view
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home){
                replacefragment(new HomeFragment());
            } else if (itemId == R.id.ranking ) {
                replacefragment(new RankingFragment());
            } else if (itemId == R.id.setting ) {
                replacefragment(new SettingFragment());
            }
            return true;
        });
    }

    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }


}