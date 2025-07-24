package com.spark.maths;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class SubtractionActivity extends Activity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
    private Spinner spinnerLanguage;
    private Button btnSolve, btnClear;
    private TextToSpeech tts;
    private String selectedLanguage = "Marathi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);

        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult = findViewById(R.id.tvResult);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        btnSolve = findViewById(R.id.btnSolve);
        btnClear = findViewById(R.id.btnClear);

        // Language selection
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();
                initTTS(); // Re-initialize TTS when language changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSolve.setOnClickListener(v -> solveSubtraction());
        btnClear.setOnClickListener(v -> clearFields());

        initTTS(); // Default init
    }

    private void initTTS() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale;
                switch (selectedLanguage) {
                    case "Marathi":
                        locale = new Locale("mr", "IN");
                        break;
                    case "Hindi":
                        locale = new Locale("hi", "IN");
                        break;
                    case "English":
                    default:
                        locale = Locale.ENGLISH;
                        break;
                }
                int result = tts.setLanguage(locale);
                tts.setSpeechRate(0.5f); // Slow speech
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void solveSubtraction() {
        String num1Str = etNumber1.getText().toString().trim();
        String num2Str = etNumber2.getText().toString().trim();

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        int num1 = Integer.parseInt(num1Str);
        int num2 = Integer.parseInt(num2Str);
        int result = num1 - num2;
        tvResult.setText("Answer: " + result);

        String spoken = getSpokenSubtraction(num1, num2, result);
        Handler handler = new Handler();
        handler.postDelayed(() -> tts.speak(spoken, TextToSpeech.QUEUE_FLUSH, null, null), 500);
    }

    private String getSpokenSubtraction(int num1, int num2, int result) {
        String num1Str, num2Str, resultStr, minusWord, equalWord;

        switch (selectedLanguage) {
            case "Marathi":
                num1Str = convertMarathi(num1);
                num2Str = convertMarathi(num2);
                resultStr = convertMarathi(result);
                minusWord = "वजा";
                equalWord = "बरोबर";
                break;
            case "Hindi":
                num1Str = convertHindi(num1);
                num2Str = convertHindi(num2);
                resultStr = convertHindi(result);
                minusWord = "घटाएं";
                equalWord = "बराबर";
                break;
            default:
                num1Str = String.valueOf(num1);
                num2Str = String.valueOf(num2);
                resultStr = String.valueOf(result);
                minusWord = "minus";
                equalWord = "equals";
                break;
        }

        return num1Str + " " + minusWord + " " + num2Str + " " + equalWord + " " + resultStr;
    }

    private void clearFields() {
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText("Answer: ");
        tts.stop();
    }

    private String convertMarathi(int number) {
        String[] marathiNumbers = {
                "शून्य", "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ", "दहा",
                "अकरा", "बारा", "तेरा", "चौदा", "पंधरा", "सोळा", "सतरा", "अठरा", "एकोणीस", "वीस"
        };
        if (number >= 0 && number <= 20) {
            return marathiNumbers[number];
        }
        return String.valueOf(number);
    }

    private String convertHindi(int number) {
        String[] hindiNumbers = {
                "शून्य", "एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस",
                "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह", "सोलह", "सत्रह", "अठारह", "उन्नीस", "बीस"
        };
        if (number >= 0 && number <= 20) {
            return hindiNumbers[number];
        }
        return String.valueOf(number);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
