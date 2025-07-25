package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash); // Create this layout

        new Handler().postDelayed(() -> {
            Intent i = new Intent(FlashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
