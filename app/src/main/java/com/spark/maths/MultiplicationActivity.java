package com.spark.maths;


import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class MultiplicationActivity extends Activity {

    EditText etNumber1, etNumber2;
    TextView tvResult;
    Button btnSolve, btnClear;
    Spinner spinnerLanguage;
    TextToSpeech tts;
    String selectedLanguage = "English";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);

        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult = findViewById(R.id.tvResult);
        btnSolve = findViewById(R.id.btnSolve);
        btnClear = findViewById(R.id.btnClear);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);

        // Initialize TTS with English by default
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                setTTSLanguage("English");
            }
        });

        // Spinner selection listener
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLanguage = adapterView.getItemAtPosition(i).toString();
                setTTSLanguage(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // Solve button
        btnSolve.setOnClickListener(view -> {
            String num1Str = etNumber1.getText().toString().trim();
            String num2Str = etNumber2.getText().toString().trim();

            if (num1Str.isEmpty() || num2Str.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            int num1 = Integer.parseInt(num1Str);
            int num2 = Integer.parseInt(num2Str);
            int result = num1 * num2;
            String resultText = "Answer: " + result;
            tvResult.setText(resultText);

            String speakText = getMultiplicationText(num1, num2, result, selectedLanguage);
            speakSlowly(speakText);
        });

        // Clear button
        btnClear.setOnClickListener(view -> {
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
            default:
                tts.setLanguage(Locale.US);
                break;
        }
    }

    private void speakSlowly(String text) {
        if (tts != null) {
            tts.setSpeechRate(0.5f); // Slow for kids
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private String getMultiplicationText(int num1, int num2, int result, String lang) {
        if (lang.equals("Marathi")) {
            return getMarathiNumber(num1) + " गुणिले " + getMarathiNumber(num2) + " बरोबर " + getMarathiNumber(result);
        } else if (lang.equals("Hindi")) {
            return getHindiNumber(num1) + " गुना " + getHindiNumber(num2) + " बराबर " + getHindiNumber(result);
        } else {
            return num1 + " times " + num2 + " equals " + result;
        }
    }

    private String getMarathiNumber(int number) {
        String[] numbers = {
                "शून्य", "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ",
                "दहा", "अकरा", "बारा", "तेरा", "चौदा", "पंधरा", "सोळा", "सतरा", "अठरा", "एकोणीस", "वीस"
        };
        if (number >= 0 && number <= 20) return numbers[number];
        return String.valueOf(number);
    }

    private String getHindiNumber(int number) {
        String[] numbers = {
                "शून्य", "एक", "दो", "तीन", "चार", "पाँच", "छह", "सात", "आठ", "नौ",
                "दस", "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह", "सोलह", "सत्रह", "अठारह", "उन्नीस", "बीस"
        };
        if (number >= 0 && number <= 20) return numbers[number];
        return String.valueOf(number);
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
