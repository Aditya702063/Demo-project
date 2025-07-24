package com.spark.maths;


import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AdditionActivity extends Activity {

    EditText etNumber1, etNumber2;
    Button btnSolve, btnClear;
    TextView tvResult;
    Spinner spinnerLanguage;
    TextToSpeech tts;
    String selectedLanguage = "Marathi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        btnSolve = findViewById(R.id.btnSolve);
        btnClear = findViewById(R.id.btnClear);
        tvResult = findViewById(R.id.tvResult);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);

        // Set up Spinner
        String[] languages = {"Marathi", "Hindi", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinnerLanguage.setAdapter(adapter);

        // Initialize TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage(selectedLanguage);
                tts.setSpeechRate(0.5f);  // slow for kids
            }
        });

        spinnerLanguage.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languages[position];
                setTTSLanguage(selectedLanguage);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        btnSolve.setOnClickListener(v -> {
            String num1Str = etNumber1.getText().toString().trim();
            String num2Str = etNumber2.getText().toString().trim();

            if (num1Str.isEmpty() || num2Str.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            int num1 = Integer.parseInt(num1Str);
            int num2 = Integer.parseInt(num2Str);
            int sum = num1 + num2;

            tvResult.setText("Answer: " + sum);

            String spokenText = getSpokenAddition(num1, num2, sum, selectedLanguage);
            tts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, null);
        });

        btnClear.setOnClickListener(v -> {
            etNumber1.setText("");
            etNumber2.setText("");
            tvResult.setText("Answer: ");
        });
    }

    private void setTTSLanguage(String language) {
        switch (language) {
            case "Marathi":
                tts.setLanguage(new Locale("mr", "IN"));
                break;
            case "Hindi":
                tts.setLanguage(new Locale("hi", "IN"));
                break;
            case "English":
                tts.setLanguage(Locale.US);
                break;
        }
    }

    private String getSpokenAddition(int a, int b, int sum, String language) {
        switch (language) {
            case "Marathi":
                return convertToMarathi(a) + " मध्ये " + convertToMarathi(b) + " मिळवल्यावर उत्तर " + convertToMarathi(sum);
            case "Hindi":
                return convertToHindi(a) + " में " + convertToHindi(b) + " जोड़ने पर उत्तर " + convertToHindi(sum);
            case "English":
                return a + " plus " + b + " equals " + sum;
            default:
                return a + " plus " + b + " equals " + sum;
        }
    }

    private String convertToMarathi(int number) {
        String[] marathiNumbers = {
                "शून्य", "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ",
                "दहा", "अकरा", "बारा", "तेरा", "चौदा", "पंधरा", "सोळा", "सतरा", "अठरा", "एकोणीस",
                "वीस", "एकवीस", "बावीस", "तेवीस", "चोवीस", "पंचवीस", "सव्वीस", "सत्तावीस", "अठ्ठावीस", "एकोणतीस", "तीस"
        };
        return number <= 30 ? marathiNumbers[number] : String.valueOf(number);
    }

    private String convertToHindi(int number) {
        String[] hindiNumbers = {
                "शून्य", "एक", "दो", "तीन", "चार", "पाँच", "छह", "सात", "आठ", "नौ",
                "दस", "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह", "सोलह", "सत्रह", "अठारह", "उन्नीस",
                "बीस", "इक्कीस", "बाईस", "तेईस", "चौबीस", "पच्चीस", "छब्बीस", "सत्ताईस", "अट्ठाईस", "उनतीस", "तीस"
        };
        return number <= 30 ? hindiNumbers[number] : String.valueOf(number);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
