package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LearningActivity extends AppCompatActivity {

    Button btnNumbers, btnTable, btnAddition, btnSubtraction, btnMultiplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        btnNumbers = findViewById(R.id.btnNumbers);
        btnTable = findViewById(R.id.btnTable);
        btnAddition = findViewById(R.id.btnAddition);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnMultiplication = findViewById(R.id.btnMultiplication);

        btnNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, NumbersActivity.class));
            }
        });
        btnTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, TableActivity.class));
            }
        });
        btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, AdditionActivity.class));
            }
        });
        btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, SubtractionActivity.class));
            }
        });
        btnMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LearningActivity.this, MultiplicationActivity.class));
            }
        });





    }
}
