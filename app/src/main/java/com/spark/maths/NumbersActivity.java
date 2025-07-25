package com.spark.maths;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Locale;

public class NumbersActivity extends AppCompatActivity {

    TextToSpeech tts;
    Button btnSpeakOne, btnSpeakTwo, btnSpeakThree, btnSpeakFour, btnSpeakFive,
            btnSpeakSix, btnSpeakSeven, btnSpeakEight, btnSpeakNine, btnSpeakTen,
            btnSpeakEleven, btnSpeakTwelve, btnSpeakThirteen, btnSpeakForteen, btnSpeakFifteen,
            btnSpeakSixteen, btnSpeakSeventeen, btnSpeakEighteen, btnSpeakNineteen, btnSpeakTwenty,
            btnSpeakTwentyone, btnSpeakTwentytwo, btnSpeakTwentythree, btnSpeakTwentyfour, btnSpeakTwentyfive;

    Spinner languageSpinner;

    HashMap<String, String[]> translations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Initialize buttons 1–25
        btnSpeakOne = findViewById(R.id.btnSpeakOne);
        btnSpeakTwo = findViewById(R.id.btnSpeakTwo);
        btnSpeakThree = findViewById(R.id.btnSpeakThree);
        btnSpeakFour = findViewById(R.id.btnSpeakFour);
        btnSpeakFive = findViewById(R.id.btnSpeakFive);
        btnSpeakSix = findViewById(R.id.btnSpeakSIX);
        btnSpeakSeven = findViewById(R.id.btnSpeakSeven);
        btnSpeakEight = findViewById(R.id.btnSpeakEight);
        btnSpeakNine = findViewById(R.id.btnSpeakNine);
        btnSpeakTen = findViewById(R.id.btnSpeakTen);
        btnSpeakEleven = findViewById(R.id.btnSpeakEleven);
        btnSpeakTwelve = findViewById(R.id.btnSpeakTwelve);
        btnSpeakThirteen = findViewById(R.id.btnSpeakThirteen);
        btnSpeakForteen = findViewById(R.id.btnSpeakForteen);
        btnSpeakFifteen = findViewById(R.id.btnSpeakFifteen);
        btnSpeakSixteen = findViewById(R.id.btnSpeakSixteen);
        btnSpeakSeventeen = findViewById(R.id.btnSpeakSeventeen);
        btnSpeakEighteen = findViewById(R.id.btnSpeakEighteen);
        btnSpeakNineteen = findViewById(R.id.btnSpeakNineteen);
        btnSpeakTwenty = findViewById(R.id.btnSpeakTwenty);
        btnSpeakTwentyone = findViewById(R.id.btnSpeakTwentyone);
        btnSpeakTwentytwo = findViewById(R.id.btnSpeakTwentytwo);
        btnSpeakTwentythree = findViewById(R.id.btnSpeakTwentythree);
        btnSpeakTwentyfour = findViewById(R.id.btnSpeakTwentyfour);
        btnSpeakTwentyfive = findViewById(R.id.btnSpeakTwentyfive);

        languageSpinner = findViewById(R.id.languageSpinner);

        // Setup spinner
        String[] languages = {"English", "Hindi", "Marathi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(adapter);

        // Translation map
        translations = new HashMap<>();
        translations.put("English", new String[]{
                "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
                "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty",
                "Twenty-one", "Twenty-two", "Twenty-three", "Twenty-four", "Twenty-five"
        });
        translations.put("Hindi", new String[]{
                "एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस",
                "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह",
                "सोलह", "सत्रह", "अठारह", "उन्नीस", "बीस",
                "इक्कीस", "बाईस", "तेईस", "चौबीस", "पच्चीस"
        });
        translations.put("Marathi", new String[]{
                "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ", "दहा",
                "अकरा", "बारा", "तेरा", "चौदा", "पंधरा",
                "सोळा", "सतरा", "अठरा", "एकोणीस", "वीस",
                "एकवीस", "बावीस", "तेवीस", "चोवीस", "पंचवीस"
        });

        // Initialize TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });

        // Set listeners for buttons 1–25
        btnSpeakOne.setOnClickListener(view -> speakNumber(0));
        btnSpeakTwo.setOnClickListener(view -> speakNumber(1));
        btnSpeakThree.setOnClickListener(view -> speakNumber(2));
        btnSpeakFour.setOnClickListener(view -> speakNumber(3));
        btnSpeakFive.setOnClickListener(view -> speakNumber(4));
        btnSpeakSix.setOnClickListener(view -> speakNumber(5));
        btnSpeakSeven.setOnClickListener(view -> speakNumber(6));
        btnSpeakEight.setOnClickListener(view -> speakNumber(7));
        btnSpeakNine.setOnClickListener(view -> speakNumber(8));
        btnSpeakTen.setOnClickListener(view -> speakNumber(9));
        btnSpeakEleven.setOnClickListener(view -> speakNumber(10));
        btnSpeakTwelve.setOnClickListener(view -> speakNumber(11));
        btnSpeakThirteen.setOnClickListener(view -> speakNumber(12));
        btnSpeakForteen.setOnClickListener(view -> speakNumber(13));
        btnSpeakFifteen.setOnClickListener(view -> speakNumber(14));
        btnSpeakSixteen.setOnClickListener(view -> speakNumber(15));
        btnSpeakSeventeen.setOnClickListener(view -> speakNumber(16));
        btnSpeakEighteen.setOnClickListener(view -> speakNumber(17));
        btnSpeakNineteen.setOnClickListener(view -> speakNumber(18));
        btnSpeakTwenty.setOnClickListener(view -> speakNumber(19));
        btnSpeakTwentyone.setOnClickListener(view -> speakNumber(20));
        btnSpeakTwentytwo.setOnClickListener(view -> speakNumber(21));
        btnSpeakTwentythree.setOnClickListener(view -> speakNumber(22));
        btnSpeakTwentyfour.setOnClickListener(view -> speakNumber(23));
        btnSpeakTwentyfive.setOnClickListener(view -> speakNumber(24));
    }

    private void speakNumber(int index) {
        String selectedLang = languageSpinner.getSelectedItem().toString();
        String[] numbers = translations.get(selectedLang);
        if (numbers != null && index < numbers.length) {
            String text = numbers[index];

            // Set correct language for TTS
            switch (selectedLang) {
                case "Hindi":
                    tts.setLanguage(new Locale("hi", "IN"));
                    break;
                case "Marathi":
                    tts.setLanguage(new Locale("mr", "IN"));
                    break;
                default:
                    tts.setLanguage(Locale.ENGLISH);
            }

            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
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
