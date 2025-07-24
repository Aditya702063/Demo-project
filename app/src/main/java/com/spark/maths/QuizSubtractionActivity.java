package com.spark.maths;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class QuizSubtractionActivity extends Activity {

    TextView questionText;
    Button option1, option2, option3, nextButton;
    Spinner languageSpinner;
    ImageView starImage;

    TextToSpeech tts;
    MediaPlayer correctSound, wrongSound;

    String currentLanguage = "en";

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String[]> options = new ArrayList<>();
    ArrayList<String> correctAnswers = new ArrayList<>();
    int currentQuizIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_subtraction);

        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        languageSpinner = findViewById(R.id.languageSpinner);
        starImage = findViewById(R.id.starImage);

        correctSound = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI);
        wrongSound = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage(currentLanguage);
                loadQuiz();
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentLanguage = position == 1 ? "hi" : (position == 2 ? "mr" : "en");
                setTTSLanguage(currentLanguage);
                speakCurrentQuestion();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        option1.setOnClickListener(v -> checkAnswer(option1));
        option2.setOnClickListener(v -> checkAnswer(option2));
        option3.setOnClickListener(v -> checkAnswer(option3));

        nextButton.setOnClickListener(v -> {
            starImage.setVisibility(View.GONE);
            if (currentQuizIndex < questions.size() - 1) {
                currentQuizIndex++;
                loadQuiz();
            } else {
                speak("Quiz complete!");
                questionText.setText("🎉 Quiz Complete!");
                disableOptions();
            }
        });

        initQuizData();
    }

    private void initQuizData() {
        questions.add("5 - 2 = ?");
        options.add(new String[]{"2", "3", "4"});
        correctAnswers.add("3");

        questions.add("3 - 1 = ?");
        options.add(new String[]{"1", "2", "3"});
        correctAnswers.add("2");

        questions.add("6 - 3 = ?");
        options.add(new String[]{"4", "3", "2"});
        correctAnswers.add("3");

        questions.add("8 - 4 = ?");
        options.add(new String[]{"4", "5", "3"});
        correctAnswers.add("4");

        questions.add("9 - 5 = ?");
        options.add(new String[]{"3", "4", "5"});
        correctAnswers.add("4");

        questions.add("10 - 7 = ?");
        options.add(new String[]{"3", "2", "4"});
        correctAnswers.add("3");

        questions.add("7 - 2 = ?");
        options.add(new String[]{"5", "4", "6"});
        correctAnswers.add("5");

        questions.add("4 - 0 = ?");
        options.add(new String[]{"4", "3", "5"});
        correctAnswers.add("4");

        questions.add("3 - 3 = ?");
        options.add(new String[]{"0", "1", "2"});
        correctAnswers.add("0");

        questions.add("6 - 1 = ?");
        options.add(new String[]{"5", "6", "4"});
        correctAnswers.add("5");
    }

    private void loadQuiz() {
        String q = questions.get(currentQuizIndex);
        String[] opts = options.get(currentQuizIndex);
        questionText.setText(q);
        option1.setText(opts[0]);
        option2.setText(opts[1]);
        option3.setText(opts[2]);
        enableOptions();
        speakCurrentQuestion();
    }

    private void setTTSLanguage(String langCode) {
        Locale locale = langCode.equals("hi") ? new Locale("hi", "IN") :
                langCode.equals("mr") ? new Locale("mr", "IN") :
                        Locale.ENGLISH;
        tts.setLanguage(locale);
    }

    private void speakCurrentQuestion() {
        String q = questions.get(currentQuizIndex);
        String text;
        switch (currentLanguage) {
            case "hi":
                text = translateQuestion(q, "hi");
                break;
            case "mr":
                text = translateQuestion(q, "mr");
                break;
            default:
                text = "What is " + q.replace("=", "") + "?";
        }
        speak(text);
    }

    private String translateQuestion(String question, String lang) {
        if (lang.equals("hi")) {
            if (question.contains("5 - 2")) return "पाँच में से दो घटाने पर कितना बचेगा?";
            if (question.contains("3 - 1")) return "तीन में से एक घटाने पर कितना बचेगा?";
            if (question.contains("6 - 3")) return "छह में से तीन घटाने पर कितना बचेगा?";
            if (question.contains("8 - 4")) return "आठ में से चार घटाने पर कितना बचेगा?";
            if (question.contains("9 - 5")) return "नौ में से पाँच घटाने पर कितना बचेगा?";
            if (question.contains("10 - 7")) return "दस में से सात घटाने पर कितना बचेगा?";
            if (question.contains("7 - 2")) return "सात में से दो घटाने पर कितना बचेगा?";
            if (question.contains("4 - 0")) return "चार में से शून्य घटाने पर कितना बचेगा?";
            if (question.contains("3 - 3")) return "तीन में से तीन घटाने पर कितना बचेगा?";
            if (question.contains("6 - 1")) return "छह में से एक घटाने पर कितना बचेगा?";
        } else if (lang.equals("mr")) {
            if (question.contains("5 - 2")) return "पाचातून दोन वजा केल्यास किती उरतात?";
            if (question.contains("3 - 1")) return "तीन मधून एक वजा केल्यास किती उरतात?";
            if (question.contains("6 - 3")) return "सहा मधून तीन वजा केल्यास किती उरतात?";
            if (question.contains("8 - 4")) return "आठातून चार वजा केल्यास किती उरतात?";
            if (question.contains("9 - 5")) return "नऊ मधून पाच वजा केल्यास किती उरतात?";
            if (question.contains("10 - 7")) return "दहा मधून सात वजा केल्यास किती उरतात?";
            if (question.contains("7 - 2")) return "सातातून दोन वजा केल्यास किती उरतात?";
            if (question.contains("4 - 0")) return "चारातून शून्य वजा केल्यास किती उरतात?";
            if (question.contains("3 - 3")) return "तीन मधून तीन वजा केल्यास किती उरतात?";
            if (question.contains("6 - 1")) return "सहा मधून एक वजा केल्यास किती उरतात?";
        }
        return question;
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void checkAnswer(Button selectedButton) {
        String selected = selectedButton.getText().toString();
        String correctAnswer = correctAnswers.get(currentQuizIndex);
        starImage.setVisibility(View.GONE);

        if (selected.equals(correctAnswer)) {
            correctSound.start();
            starImage.setVisibility(View.VISIBLE);
            speakFeedback(true);
        } else {
            wrongSound.start();
            speakFeedback(false);
        }
        disableOptions();
    }

    private void speakFeedback(boolean isCorrect) {
        String feedback;
        switch (currentLanguage) {
            case "hi":
                feedback = isCorrect ? "सही उत्तर!" : "गलत उत्तर!";
                break;
            case "mr":
                feedback = isCorrect ? "बरोबर उत्तर!" : "चूक उत्तर!";
                break;
            default:
                feedback = isCorrect ? "Correct answer!" : "Wrong answer!";
        }
        speak(feedback);
    }

    private void enableOptions() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
    }

    private void disableOptions() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (correctSound != null) correctSound.release();
        if (wrongSound != null) wrongSound.release();
        super.onDestroy();
    }
}
