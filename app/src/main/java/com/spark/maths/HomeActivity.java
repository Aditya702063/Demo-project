package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    LinearLayout homeTab, settingsTab, profileTab;
    ImageView homeIcon, settingsIcon, profileIcon;
    TextView homeText, settingsText, profileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Check if user is logged in
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home); // Set your layout

        // Initialize views
        homeTab = findViewById(R.id.homeTab);
        settingsTab = findViewById(R.id.settingsTab);
        profileTab = findViewById(R.id.profileTab);
        homeIcon = homeTab.findViewById(R.id.homeIcon);
        homeText = homeTab.findViewById(R.id.homeText);
        settingsIcon = settingsTab.findViewById(R.id.settingsIcon);
        settingsText = settingsTab.findViewById(R.id.settingsText);
        profileIcon = profileTab.findViewById(R.id.profileIcon);
        profileText = profileTab.findViewById(R.id.profileText);

        // Load default fragment
        loadFragment(new HomeFragment());
        highlightTab(homeTab);

        // Handle tab clicks
        homeTab.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            highlightTab(homeTab);
        });

        settingsTab.setOnClickListener(v -> {
            loadFragment(new SettingsFragment());
            highlightTab(settingsTab);
        });

        profileTab.setOnClickListener(v -> {
            loadFragment(new EditProfileFragment());
            highlightTab(profileTab);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private void highlightTab(LinearLayout selectedTab) {
        // Reset all to default
        homeIcon.setColorFilter(getResources().getColor(R.color.black));
        homeText.setTextColor(getResources().getColor(R.color.black));
        settingsIcon.setColorFilter(getResources().getColor(R.color.black));
        settingsText.setTextColor(getResources().getColor(R.color.black));
        profileIcon.setColorFilter(getResources().getColor(R.color.black));
        profileText.setTextColor(getResources().getColor(R.color.black));

        // Highlight selected
        if (selectedTab == homeTab) {
            homeIcon.setColorFilter(getResources().getColor(R.color.teal_700));
            homeText.setTextColor(getResources().getColor(R.color.teal_700));
        } else if (selectedTab == settingsTab) {
            settingsIcon.setColorFilter(getResources().getColor(R.color.teal_700));
            settingsText.setTextColor(getResources().getColor(R.color.teal_700));
        } else if (selectedTab == profileTab) {
            profileIcon.setColorFilter(getResources().getColor(R.color.teal_700));
            profileText.setTextColor(getResources().getColor(R.color.teal_700));
        }
    }
}
