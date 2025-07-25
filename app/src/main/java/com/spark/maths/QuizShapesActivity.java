package com.spark.maths;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class QuizShapesActivity extends Activity {

    private TextView questionText;
    private Button option1, option2, option3, nextButton;
    private Spinner languageSpinner;
    private ImageView shapeImage, starImage;

    private TextToSpeech tts;
    private String currentLanguage = "en";
    private Locale selectedLocale = Locale.ENGLISH;

    private ArrayList<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_shapes);

        // Initialize UI
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        languageSpinner = findViewById(R.id.languageSpinner);
        shapeImage = findViewById(R.id.shapeImage);
        starImage = findViewById(R.id.starImage);

        // Setup TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage(selectedLocale);
            }
        });

        // Language selector
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

        prepareQuestions();

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

        loadQuestion();
    }

    private void prepareQuestions() {
        questions.clear();
        questions.add(new Question("Which shape is this?", "Circle", new String[]{"Square", "Circle", "Triangle"}, R.drawable.shape_circle));
        questions.add(new Question("Which shape is this?", "Square", new String[]{"Circle", "Rectangle", "Square"}, R.drawable.shape_square));
        questions.add(new Question("Which shape is this?", "Triangle", new String[]{"Triangle", "Circle", "Star"}, R.drawable.shape_triangle));
        questions.add(new Question("Which shape is this?", "Rectangle", new String[]{"Square", "Rectangle", "Circle"}, R.drawable.shape_rectangle));
        questions.add(new Question("Which shape is this?", "Star", new String[]{"Heart", "Star", "Diamond"}, R.drawable.shape_star));
        questions.add(new Question("Which shape is this?", "Heart", new String[]{"Heart", "Oval", "Circle"}, R.drawable.shape_heart));
        questions.add(new Question("Which shape is this?", "Diamond", new String[]{"Star", "Diamond", "Triangle"}, R.drawable.shape_diamond));
        questions.add(new Question("Which shape is this?", "Oval", new String[]{"Oval", "Circle", "Rectangle"}, R.drawable.shape_oval));
        questions.add(new Question("Which shape is this?", "Pentagon", new String[]{"Pentagon", "Hexagon", "Square"}, R.drawable.shape_pentagon));
        questions.add(new Question("Which shape is this?", "Hexagon", new String[]{"Hexagon", "Circle", "Triangle"}, R.drawable.shape_hexagon));
    }

    private void loadQuestion() {
        Question q = questions.get(currentQuestionIndex);

        questionText.setText(getLocalizedQuestion(q.getQuestionText()));
        option1.setText(q.options[0]);
        option2.setText(q.options[1]);
        option3.setText(q.options[2]);

        shapeImage.setImageResource(q.imageResId);
        starImage.setVisibility(View.GONE);

        speakText(q.getQuestionText());
    }

    private void checkAnswer(String selected) {
        Question q = questions.get(currentQuestionIndex);
        if (selected.equals(q.correctAnswer)) {
            starImage.setVisibility(View.VISIBLE);
            speakText(getLocalizedAnswerText("Correct"));
        } else {
            starImage.setVisibility(View.GONE);
            speakText(getLocalizedAnswerText("Wrong"));
        }
    }

    private String getLocalizedAnswerText(String result) {
        HashMap<String, String> en = new HashMap<>();
        en.put("Correct", "Correct");
        en.put("Wrong", "Wrong");

        HashMap<String, String> hi = new HashMap<>();
        hi.put("Correct", "सही उत्तर");
        hi.put("Wrong", "गलत उत्तर");

        HashMap<String, String> mr = new HashMap<>();
        mr.put("Correct", "बरोबर उत्तर");
        mr.put("Wrong", "चुकिचे उत्तर");

        switch (currentLanguage) {
            case "hi":
                return hi.getOrDefault(result, result);
            case "mr":
                return mr.getOrDefault(result, result);
            default:
                return en.getOrDefault(result, result);
        }
    }

    private String getLocalizedQuestion(String question) {
        switch (currentLanguage) {
            case "hi":
                return "यह कौन सा आकार है?";
            case "mr":
                return "हा कोणता आकार आहे?";
            default:
                return question;
        }
    }

    private void speakText(String text) {
        if (tts != null) {
            tts.setSpeechRate(0.6f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SHAPE_ID");
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

    static class Question {
        private final String questionText;
        private final String correctAnswer;
        private final String[] options;
        private final int imageResId;

        public Question(String questionText, String correctAnswer, String[] options, int imageResId) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
            this.options = options;
            this.imageResId = imageResId;
        }

        public String getQuestionText() {
            return questionText;
        }
    }
}
