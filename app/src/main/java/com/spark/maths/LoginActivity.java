package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    TextView regesterText;
    Button loginButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Stay logged in check
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginBtn);
        regesterText = findViewById(R.id.regesterText);

        mAuth = FirebaseAuth.getInstance();

        regesterText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

        loginButton.setOnClickListener(v -> {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


    }
}