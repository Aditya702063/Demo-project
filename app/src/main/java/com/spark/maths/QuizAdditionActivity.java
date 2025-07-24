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

public class QuizAdditionActivity extends Activity {

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
        setContentView(R.layout.activity_quiz_addition);

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
        questions.add("2 + 3 = ?");
        options.add(new String[]{"4", "5", "6"});
        correctAnswers.add("5");

        questions.add("1 + 1 = ?");
        options.add(new String[]{"2", "3", "1"});
        correctAnswers.add("2");

        questions.add("3 + 2 = ?");
        options.add(new String[]{"6", "4", "5"});
        correctAnswers.add("5");

        questions.add("4 + 4 = ?");
        options.add(new String[]{"6", "8", "7"});
        correctAnswers.add("8");

        questions.add("5 + 3 = ?");
        options.add(new String[]{"7", "8", "9"});
        correctAnswers.add("8");

        questions.add("6 + 2 = ?");
        options.add(new String[]{"9", "7", "8"});
        correctAnswers.add("8");

        questions.add("7 + 1 = ?");
        options.add(new String[]{"8", "7", "9"});
        correctAnswers.add("8");

        questions.add("3 + 5 = ?");
        options.add(new String[]{"7", "8", "9"});
        correctAnswers.add("8");

        questions.add("0 + 9 = ?");
        options.add(new String[]{"8", "9", "10"});
        correctAnswers.add("9");

        questions.add("10 + 0 = ?");
        options.add(new String[]{"10", "11", "9"});
        correctAnswers.add("10");
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
            if (question.contains("2 + 3")) return "दो और तीन बराबर कितना?";
            if (question.contains("1 + 1")) return "एक और एक बराबर कितना?";
            if (question.contains("3 + 2")) return "तीन और दो बराबर कितना?";
            if (question.contains("4 + 4")) return "चार और चार बराबर कितना?";
            if (question.contains("5 + 3")) return "पांच और तीन बराबर कितना?";
            if (question.contains("6 + 2")) return "छह और दो बराबर कितना?";
            if (question.contains("7 + 1")) return "सात और एक बराबर कितना?";
            if (question.contains("3 + 5")) return "तीन और पांच बराबर कितना?";
            if (question.contains("0 + 9")) return "शून्य और नौ बराबर कितना?";
            if (question.contains("10 + 0")) return "दस और शून्य बराबर कितना?";
        } else if (lang.equals("mr")) {
            if (question.contains("2 + 3")) return "दोन आणि तीन यांचे बेरीज किती?";
            if (question.contains("1 + 1")) return "एक आणि एक यांचे बेरीज किती?";
            if (question.contains("3 + 2")) return "तीन आणि दोन यांचे बेरीज किती?";
            if (question.contains("4 + 4")) return "चार आणि चार यांचे बेरीज किती?";
            if (question.contains("5 + 3")) return "पाच आणि तीन यांचे बेरीज किती?";
            if (question.contains("6 + 2")) return "सहा आणि दोन यांचे बेरीज किती?";
            if (question.contains("7 + 1")) return "सात आणि एक यांचे बेरीज किती?";
            if (question.contains("3 + 5")) return "तीन आणि पाच यांचे बेरीज किती?";
            if (question.contains("0 + 9")) return "शून्य आणि नऊ यांचे बेरीज किती?";
            if (question.contains("10 + 0")) return "दहा आणि शून्य यांचे बेरीज किती?";
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
