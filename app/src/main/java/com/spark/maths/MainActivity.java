package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText firstName, lastName, email, password, confirmPassword;
    TextView loginText;
    CheckBox termsCheckbox;
    Button registerButton;

    FirebaseAuth mAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Stay logged in check
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);

        registerButton.setOnClickListener(v -> registerUser());
        loginText.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }

    private void registerUser() {
        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirm = confirmPassword.getText().toString().trim();

        if (fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Please accept the terms", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnSuccessListener(authResult -> {
                    String uid = mAuth.getCurrentUser().getUid();
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("firstName", fname);
                    userMap.put("lastName", lname);
                    userMap.put("email", mail);
                    userMap.put("password", pass);

                    dbRef.child(uid).setValue(userMap)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "DB Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
