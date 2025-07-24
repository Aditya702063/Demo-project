package com.spark.maths;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import java.util.Locale;
import java.util.ArrayList;
import java.util.HashMap;

public class QuizCountingActivity extends Activity {

    private TextView questionText;
    private Button option1, option2, option3, nextButton;
    private Spinner languageSpinner;
    private ImageView starImage;

    private TextToSpeech tts;
    private String currentLanguage = "en";
    private ArrayList<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private Locale selectedLocale = Locale.ENGLISH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_counting);

        // Initialize UI
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        starImage = findViewById(R.id.starImage);
        languageSpinner = findViewById(R.id.languageSpinner);

        // Setup TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage(selectedLocale);
            }
        });

        // Setup language selector
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                switch (selected) {
                    case "English":
                        currentLanguage = "en";
                        selectedLocale = Locale.ENGLISH;
                        break;
                    case "Hindi":
                        currentLanguage = "hi";
                        selectedLocale = new Locale("hi", "IN");
                        break;
                    case "Marathi":
                        currentLanguage = "mr";
                        selectedLocale = new Locale("mr", "IN");
                        break;
                }
                setTTSLanguage(selectedLocale);
                loadQuestion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Add sample questions
        prepareQuestions();

        // Set listeners
        View.OnClickListener answerClickListener = view -> {
            Button clicked = (Button) view;
            checkAnswer(clicked.getText().toString());
        };

        option1.setOnClickListener(answerClickListener);
        option2.setOnClickListener(answerClickListener);
        option3.setOnClickListener(answerClickListener);

        nextButton.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
            loadQuestion();
        });

        // Load first question
        loadQuestion();
    }

    private void prepareQuestions() {
        questions.clear();
        questions.add(new Question("How many apples?", "3", new String[]{"2", "3", "4"}));
        questions.add(new Question("How many stars?", "5", new String[]{"5", "6", "4"}));
        questions.add(new Question("How many cats?", "2", new String[]{"1", "2", "3"}));
        questions.add(new Question("How many balls?", "4", new String[]{"3", "5", "4"}));
        questions.add(new Question("How many pencils?", "1", new String[]{"2", "1", "3"}));
        questions.add(new Question("How many birds?", "5", new String[]{"4", "5", "6"}));
        questions.add(new Question("How many trees?", "3", new String[]{"3", "2", "1"}));
        questions.add(new Question("How many cups?", "2", new String[]{"2", "3", "1"}));
        questions.add(new Question("How many dolls?", "5", new String[]{"4", "5", "3"}));
        questions.add(new Question("How many frogs?", "1", new String[]{"1", "2", "3"}));
        questions.add(new Question("How many kites?", "5", new String[]{"4", "5", "6"}));
        questions.add(new Question("How many books?", "3", new String[]{"2", "3", "4"}));
        questions.add(new Question("How many oranges?", "2", new String[]{"1", "3", "2"}));
    }

    private void loadQuestion() {
        starImage.setVisibility(View.GONE);
        Question q = questions.get(currentQuestionIndex);

        questionText.setText(q.getQuestionText());

        option1.setText(q.options[0]);
        option2.setText(q.options[1]);
        option3.setText(q.options[2]);

        // Set image based on question
        ImageView questionImage = findViewById(R.id.questionImage);
        questionImage.setVisibility(View.VISIBLE);

        switch (q.getQuestionText()) {
            case "How many apples?":
                questionImage.setImageResource(R.drawable.apples);
                break;
            case "How many stars?":
                questionImage.setImageResource(R.drawable.stars);
                break;
            case "How many cats?":
                questionImage.setImageResource(R.drawable.cats);
                break;
            case "How many balls?":
                questionImage.setImageResource(R.drawable.balls);
                break;
            case "How many pencils?":
                questionImage.setImageResource(R.drawable.penscils);
                break;
            case "How many birds?":
                questionImage.setImageResource(R.drawable.birds);
                break;
            case "How many trees?":
                questionImage.setImageResource(R.drawable.trees);
                break;
            case "How many cups?":
                questionImage.setImageResource(R.drawable.cups);
                break;
            case "How many dolls?":
                questionImage.setImageResource(R.drawable.dolls);
                break;
            case "How many frogs?":
                questionImage.setImageResource(R.drawable.frogs);
                break;
            case "How many kites?":
                questionImage.setImageResource(R.drawable.kites);
                break;
            case "How many books?":
                questionImage.setImageResource(R.drawable.books);
                break;
            case "How many oranges?":
                questionImage.setImageResource(R.drawable.orenges);
                break;
            default:
                questionImage.setImageDrawable(null);
                break;
        }

        // Speak question
        speakText(q.getQuestionText());
    }


    private void checkAnswer(String selected) {
        Question q = questions.get(currentQuestionIndex);
        if (selected.equals(q.correctAnswer)) {
            starImage.setVisibility(View.VISIBLE);
            speakText(getLocalizedAnswerText(q.correctAnswer));
        } else {
            starImage.setVisibility(View.GONE);
            speakText(getLocalizedTryAgain());
        }
    }

    private String getLocalizedAnswerText(String number) {
        HashMap<String, String> en = new HashMap<>();
        en.put("1", "One");
        en.put("2", "Two");
        en.put("3", "Three");
        en.put("4", "Four");
        en.put("5", "Five");

        HashMap<String, String> hi = new HashMap<>();
        hi.put("1", "एक");
        hi.put("2", "दो");
        hi.put("3", "तीन");
        hi.put("4", "चार");
        hi.put("5", "पाँच");

        HashMap<String, String> mr = new HashMap<>();
        mr.put("1", "एक");
        mr.put("2", "दोन");
        mr.put("3", "तीन");
        mr.put("4", "चार");
        mr.put("5", "पाच");

        switch (currentLanguage) {
            case "hi":
                return hi.getOrDefault(number, number);
            case "mr":
                return mr.getOrDefault(number, number);
            default:
                return en.getOrDefault(number, number);
        }
    }

    private String getLocalizedTryAgain() {
        switch (currentLanguage) {
            case "hi":
                return "फिर से प्रयास करें";
            case "mr":
                return "पुन्हा प्रयत्न करा";
            default:
                return "Try again";
        }
    }

    private String getLocalizedQuestion(String question) {
        // Optionally localize the question itself
        switch (currentLanguage) {
            case "hi":
                return question.replace("How many", "कितने") + "?";
            case "mr":
                return question.replace("How many", "किती") + "?";
            default:
                return question;
        }
    }

    private void speakText(String text) {
        if (tts != null) {
            tts.setSpeechRate(0.6f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID");
        }
    }

    private void setTTSLanguage(Locale locale) {
        if (tts != null) {
            int result = tts.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "TTS language not supported", Toast.LENGTH_SHORT).show();
            }
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

    // Inner class for question
    static class Question {
        private final String questionText;
        private final String correctAnswer;
        private final String[] options;

        public Question(String questionText, String correctAnswer, String[] options) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getQuestionText() {
            return questionText;
        }
    }
}
