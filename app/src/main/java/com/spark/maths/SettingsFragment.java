package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Sign out
            startActivity(new Intent(getActivity(), LoginActivity.class)); // Go to login
            requireActivity().finish(); // Finish HomeActivity
        });

        return view;
    }
}
