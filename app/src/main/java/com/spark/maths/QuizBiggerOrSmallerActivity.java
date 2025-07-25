package com.spark.maths;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class QuizBiggerOrSmallerActivity extends AppCompatActivity {

    private TextView questionText;
    private Button option1, option2, option3, nextButton;
    private Spinner languageSpinner;
    private ImageView starImage;

    private List<String[]> questions;
    private List<String> answers;
    private int currentQuestion = 0;
    private String selectedLanguage = "English";

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bigger_or_smaller);

        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        languageSpinner = findViewById(R.id.languageSpinner);
        starImage = findViewById(R.id.starImage);

        setupQuestions();

        // Initialize TextToSpeech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage();
            }
        });

        loadQuestion();

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();
                setTTSLanguage();
                loadQuestion();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        View.OnClickListener answerClickListener = v -> {
            Button clicked = (Button) v;
            String selected = clicked.getText().toString();
            String correct = getLocalizedAnswer(answers.get(currentQuestion));

            if (selected.equals(correct)) {
                starImage.setVisibility(View.VISIBLE);
                speak(getLocalizedText("Correct!"));
            } else {
                starImage.setVisibility(View.GONE);
                speak(getLocalizedText("Wrong!"));
            }
        };

        option1.setOnClickListener(answerClickListener);
        option2.setOnClickListener(answerClickListener);
        option3.setOnClickListener(answerClickListener);

        nextButton.setOnClickListener(v -> {
            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                starImage.setVisibility(View.GONE);
                loadQuestion();
            } else {
                Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setupQuestions() {
        // Format: {"Which is bigger: 2 or 5?", "2", "5", "3"} → Correct: "5"
        questions = Arrays.asList(
                new String[]{"Which is bigger: 2 or 5?", "2", "5", "3"},
                new String[]{"Which is smaller: 8 or 3?", "8", "5", "3"},
                new String[]{"Which is bigger: 9 or 7?", "6", "7", "9"},
                new String[]{"Which is smaller: 1 or 4?", "4", "2", "1"},
                new String[]{"Which is bigger: 6 or 4?", "4", "5", "6"},
                new String[]{"Which is bigger: 3 or 8?", "8", "7", "3"},
                new String[]{"Which is smaller: 2 or 1?", "1", "3", "2"},
                new String[]{"Which is bigger: 0 or 5?", "5", "0", "4"},
                new String[]{"Which is smaller: 10 or 6?", "6", "10", "8"},
                new String[]{"Which is bigger: 4 or 4?", "4", "3", "2"}
        );

        answers = Arrays.asList("5", "3", "9", "1", "6", "8", "1", "5", "6", "4");
    }

    private void loadQuestion() {
        String[] q = questions.get(currentQuestion);

        String prompt = q[0];
        if (selectedLanguage.equals("Hindi")) {
            prompt = "कौन बड़ा है: " + q[1] + " या " + q[2] + "?";
        } else if (selectedLanguage.equals("Marathi")) {
            prompt = "कोण मोठा आहे: " + q[1] + " की " + q[2] + "?";
        }

        questionText.setText(prompt);
        speak(prompt);

        option1.setText(getLocalizedAnswer(q[1]));
        option2.setText(getLocalizedAnswer(q[2]));
        option3.setText(getLocalizedAnswer(q[3]));
    }

    private String getLocalizedAnswer(String number) {
        Map<String, String> en = new HashMap<>();
        en.put("0", "zero"); en.put("1", "one"); en.put("2", "two"); en.put("3", "three"); en.put("4", "four");
        en.put("5", "five"); en.put("6", "six"); en.put("7", "seven"); en.put("8", "eight"); en.put("9", "nine"); en.put("10", "ten");

        Map<String, String> hi = new HashMap<>();
        hi.put("0", "शून्य"); hi.put("1", "एक"); hi.put("2", "दो"); hi.put("3", "तीन"); hi.put("4", "चार");
        hi.put("5", "पाँच"); hi.put("6", "छह"); hi.put("7", "सात"); hi.put("8", "आठ"); hi.put("9", "नौ"); hi.put("10", "दस");

        Map<String, String> mr = new HashMap<>();
        mr.put("0", "शून्य"); mr.put("1", "एक"); mr.put("2", "दोन"); mr.put("3", "तीन"); mr.put("4", "चार");
        mr.put("5", "पाच"); mr.put("6", "सहा"); mr.put("7", "सात"); mr.put("8", "आठ"); mr.put("9", "नऊ"); mr.put("10", "दहा");

        switch (selectedLanguage) {
            case "Hindi":
                return hi.getOrDefault(number, number);
            case "Marathi":
                return mr.getOrDefault(number, number);
            default:
                return en.getOrDefault(number, number);
        }
    }

    private String getLocalizedText(String baseText) {
        if (selectedLanguage.equals("Hindi")) {
            return baseText.equals("Correct!") ? "सही!" : "गलत!";
        } else if (selectedLanguage.equals("Marathi")) {
            return baseText.equals("Correct!") ? "बरोबर!" : "चूक!";
        } else {
            return baseText;
        }
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void setTTSLanguage() {
        Locale locale;
        switch (selectedLanguage) {
            case "Hindi":
                locale = new Locale("hi", "IN");
                break;
            case "Marathi":
                locale = new Locale("mr", "IN");
                break;
            default:
                locale = Locale.ENGLISH;
        }
        int result = tts.setLanguage(locale);
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(this, "TTS language not supported!", Toast.LENGTH_SHORT).show();
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
