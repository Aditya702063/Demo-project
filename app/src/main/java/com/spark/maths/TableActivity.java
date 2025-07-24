package com.spark.maths;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TableActivity extends AppCompatActivity {

    TextToSpeech tts;
    String selectedLanguage = "English";
    int selectedTable = 2;

    Spinner languageSpinner, tableSpinner;
    Button speakButton;
    TextView tableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        languageSpinner = findViewById(R.id.language_spinner);
        tableSpinner = findViewById(R.id.table_spinner);
        speakButton = findViewById(R.id.speak_button);
        tableTextView = findViewById(R.id.table_text);

        initTTS();
        setupLanguageSpinner();
        setupTableSpinner();

        speakButton.setOnClickListener(v -> {
            String tableStr = getTableText(selectedTable);
            tableTextView.setText(tableStr);
            tts.speak(tableStr, TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }

    private void initTTS() {
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                setTTSLanguage(Locale.ENGLISH);
            }
        });
    }

    private void setTTSLanguage(Locale locale) {
        int result = tts.setLanguage(locale);
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(this, "TTS language not supported!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();
                switch (selectedLanguage) {
                    case "English":
                        setTTSLanguage(Locale.ENGLISH);
                        break;
                    case "Hindi":
                        setTTSLanguage(new Locale("hi", "IN"));
                        break;
                    case "Marathi":
                        setTTSLanguage(new Locale("mr", "IN"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupTableSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.table_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableSpinner.setAdapter(adapter);

        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTable = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String getTableText(int number) {
        StringBuilder table = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (selectedLanguage.equals("English")) {
                table.append(number).append(" × ").append(i).append(" = ").append(number * i).append("\n");
            } else if (selectedLanguage.equals("Hindi")) {
                table.append(number).append(" × ").append(i).append(" = ").append(number * i).append("   ");
                table.append(convertHindi(number)).append(" गुना ")
                        .append(convertHindi(i)).append(" बराबर ")
                        .append(convertHindi(number * i)).append("\n");
            } else if (selectedLanguage.equals("Marathi")) {
                table.append(convertMarathi(number)).append(" ")
                        .append(getMarathiMultiplierWord(i)).append(" ")
                        .append(convertMarathi(number * i)).append("\n");
            }
        }
        return table.toString();
    }



    private String convertHindi(int n) {
        String[] hindi = {"", "एक", "दो", "तीन", "चार", "पांच", "छह", "सात", "आठ", "नौ", "दस",
                "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह", "सोलह", "सत्रह", "अठारह", "उन्नीस", "बीस",
                "इक्कीस", "बाईस", "तेईस", "चौबीस", "पच्चीस", "छब्बीस", "सत्ताइस", "अट्ठाइस", "उनतीस", "तीस",
                "इकतीस", "बत्तीस", "तैंतीस", "चौंतीस", "पैंतीस", "छत्तीस", "सैंतीस", "अड़तीस", "उनतालीस", "चालीस",
                "इकतालीस", "बयालीस", "तैंतालीस", "चवालीस", "पैंतालीस", "छयालिस", "सैंतालीस", "अड़तालीस", "उनचास", "पचास"};
        return n <= 50 ? hindi[n] : String.valueOf(n);
    }

    private String convertMarathi(int n) {
        String[] marathi = {
                "", "एक", "दोन", "तीन", "चार", "पाच", "सहा", "सात", "आठ", "नऊ", "दहा",
                "अकरा", "बारा", "तेरा", "चौदा", "पंधरा", "सोळा", "सतरा", "अठरा", "एकोणीस", "वीस",
                "एकवीस", "बावीस", "तेवीस", "चोवीस", "पंचवीस", "सव्वीस", "सत्तावीस", "अठ्ठावीस", "एकोणतीस", "तीस",
                "एकतीस", "बत्तीस", "तेहतीस", "चौतीस", "पस्तीस", "छत्तीस", "सदतीस", "अडतीस", "एकोणचाळीस", "चाळीस",
                "एक्केचाळीस", "बेचाळीस", "त्रेचाळीस", "चव्वेचाळीस", "पंचेचाळीस", "सेहेचाळीस", "सत्तेचाळीस", "अठ्ठेचाळीस", "एकोणपन्नास", "पन्नास",
                "एक्कावन्न", "बावन्न", "त्रेपन्न", "चव्वेपन्न", "पंचावन्न", "छप्पन्न", "सत्तावन्न", "अठ्ठावन्न", "एकोणसाठ", "साठ",
                "एकसष्ठ", "बासष्ठ", "त्रेसष्ठ", "चौसष्ठ", "पासष्ठ", "सहासष्ठ", "सत्तासष्ठ", "अठ्ठासष्ठ", "एकोणसत्तर", "सत्तर",
                "एक्काहत्तर", "बाहत्तर", "त्र्याहत्तर", "चौर्‍याहत्तर", "पंच्याहत्तर", "शहात्तर", "सत्त्याहत्तर", "अठ्ठ्याहत्तर", "एकोणऐंशी", "ऐंशी",
                "एक्क्याऐंशी", "ब्याऐंशी", "त्र्याऐंशी", "चौऱ्याऐंशी", "पंच्याऐंशी", "शहाऐंशी", "सत्त्याऐंशी", "अठ्ठ्याऐंशी", "एकोणनव्वद", "नव्वद",
                "एक्क्याण्णव", "ब्याण्णव", "त्र्याण्णव", "चौऱ्याण्णव", "पंच्याण्णव", "शहाण्णव", "सत्त्याण्णव", "अठ्ठ्याण्णव", "नव्याण्णव", "शंभर"
        };

        return n <= 100 ? marathi[n] : String.valueOf(n);
    }

    private String getMarathiMultiplierWord(int n) {
        String[] words = {
                "", "एके", "दुनी", "तीने", "चौकी", "पंचे",
                "सहाशे", "साती", "आठी", "नववी", "दाही"
        };
        return (n <= 10) ? words[n] : String.valueOf(n);
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
