package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    Button btnAddition, btnSubtraction, btnCounting, btnShapes, btnMissingNumbers, btnComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnAddition = findViewById(R.id.btnAddition);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnCounting = findViewById(R.id.btnCounting);
        btnShapes = findViewById(R.id.btnShapes);
        btnMissingNumbers = findViewById(R.id.btnMissingNumbers);
        btnComparison = findViewById(R.id.btnComparison);

        btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizAdditionActivity.class));
            }
        });

        btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizSubtractionActivity.class));
            }
        });
        btnCounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizCountingActivity.class));
            }
        });
        btnShapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizShapesActivity.class));
            }
        });
        btnMissingNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizMissingNumbersActivity.class));
            }
        });
        btnComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizActivity.this, QuizBiggerOrSmallerActivity.class));
            }
        });





    }
}
