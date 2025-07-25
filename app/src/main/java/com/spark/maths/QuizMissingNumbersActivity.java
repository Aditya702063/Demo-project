package com.spark.maths;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;

import java.util.*;

public class QuizMissingNumbersActivity extends Activity {

    private TextView questionText, instructionText;
    private Button option1, option2, option3, nextButton;
    private Spinner languageSpinner;
    private ImageView starImage;

    private TextToSpeech tts;
    private String currentLanguage = "en";
    private Locale selectedLocale = Locale.ENGLISH;

    private List<Map<String, Object>> questions = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_missing_numbers);

        instructionText = findViewById(R.id.instructionText);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.nextButton);
        languageSpinner = findViewById(R.id.languageSpinner);
        starImage = findViewById(R.id.starImage);

        // Setup TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                setTTSLanguage(selectedLocale);
                String instruction = getLocalizedInstruction();
                instructionText.setText(instruction);
                speakInstruction(instruction);
            }
        });

        // Language selection
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String lang = parent.getItemAtPosition(pos).toString();
                switch (lang) {
                    case "Hindi":
                        currentLanguage = "hi";
                        selectedLocale = new Locale("hi", "IN");
                        break;
                    case "Marathi":
                        currentLanguage = "mr";
                        selectedLocale = new Locale("mr", "IN");
                        break;
                    default:
                        currentLanguage = "en";
                        selectedLocale = Locale.ENGLISH;
                }
                setTTSLanguage(selectedLocale);
                String instruction = getLocalizedInstruction();
                instructionText.setText(instruction);
                speakInstruction(instruction);
                loadQuestion();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Option click listeners
        View.OnClickListener optionClickListener = view -> {
            Button clicked = (Button) view;
            checkAnswer(clicked.getText().toString());
        };
        option1.setOnClickListener(optionClickListener);
        option2.setOnClickListener(optionClickListener);
        option3.setOnClickListener(optionClickListener);

        nextButton.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % questions.size();
            loadQuestion();
        });

        prepareQuestions();
        loadQuestion();
    }

    private void prepareQuestions() {
        questions.clear();
        questions.add(createQuestion("1, 2, __, 4", "3", new String[]{"3", "5", "6"}));
        questions.add(createQuestion("__, 6, 7", "5", new String[]{"4", "5", "8"}));
        questions.add(createQuestion("8, __, 10", "9", new String[]{"9", "7", "11"}));
        questions.add(createQuestion("3, __, 5", "4", new String[]{"6", "4", "2"}));
        questions.add(createQuestion("__, 2, 3", "1", new String[]{"0", "1", "4"}));
        questions.add(createQuestion("4, 5, __", "6", new String[]{"7", "6", "8"}));
        questions.add(createQuestion("6, __, 8", "7", new String[]{"5", "9", "7"}));
        questions.add(createQuestion("2, __, 4", "3", new String[]{"5", "1", "3"}));
        questions.add(createQuestion("__, 1, 2", "0", new String[]{"1", "0", "3"}));
        questions.add(createQuestion("5, 6, __", "7", new String[]{"8", "6", "7"}));
    }

    private Map<String, Object> createQuestion(String qText, String answer, String[] options) {
        Map<String, Object> q = new HashMap<>();
        q.put("question", qText);
        q.put("answer", answer);
        q.put("options", options);
        return q;
    }

    private void loadQuestion() {
        starImage.setVisibility(View.GONE);
        Map<String, Object> q = questions.get(currentIndex);
        String questionStr = (String) q.get("question");
        String[] opts = (String[]) q.get("options");

        questionText.setText(questionStr);
        option1.setText(opts[0]);
        option2.setText(opts[1]);
        option3.setText(opts[2]);

        speakText(questionStr.replace("__", "blank"));
    }

    private void checkAnswer(String selected) {
        Map<String, Object> q = questions.get(currentIndex);
        String correct = (String) q.get("answer");

        if (selected.equals(correct)) {
            starImage.setVisibility(View.VISIBLE);
            speakText(getLocalizedAnswer(correct));
        } else {
            starImage.setVisibility(View.GONE);
            speakText("Try again");
        }
    }

    private String getLocalizedAnswer(String num) {
        Map<String, String> en = new HashMap<>();
        en.put("0", "zero"); en.put("1", "one"); en.put("2", "two");
        en.put("3", "three"); en.put("4", "four"); en.put("5", "five");
        en.put("6", "six"); en.put("7", "seven"); en.put("8", "eight");
        en.put("9", "nine"); en.put("10", "ten");

        Map<String, String> hi = new HashMap<>();
        hi.put("0", "शून्य"); hi.put("1", "एक"); hi.put("2", "दो");
        hi.put("3", "तीन"); hi.put("4", "चार"); hi.put("5", "पाँच");
        hi.put("6", "छह"); hi.put("7", "सात"); hi.put("8", "आठ");
        hi.put("9", "नौ"); hi.put("10", "दस");

        Map<String, String> mr = new HashMap<>();
        mr.put("0", "शून्य"); mr.put("1", "एक"); mr.put("2", "दोन");
        mr.put("3", "तीन"); mr.put("4", "चार"); mr.put("5", "पाच");
        mr.put("6", "सहा"); mr.put("7", "सात"); mr.put("8", "आठ");
        mr.put("9", "नऊ"); mr.put("10", "दहा");

        switch (currentLanguage) {
            case "hi":
                return hi.getOrDefault(num, num);
            case "mr":
                return mr.getOrDefault(num, num);
            default:
                return en.getOrDefault(num, num);
        }
    }

    private void speakText(String text) {
        if (tts != null) {
            tts.setSpeechRate(0.75f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void speakInstruction(String text) {
        if (tts != null && !tts.isSpeaking()) {
            tts.setSpeechRate(0.75f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private String getLocalizedInstruction() {
        switch (currentLanguage) {
            case "hi":
                return "गुम संख्या खोजें"; // Hindi
            case "mr":
                return "गहाळ संख्या शोधा"; // Marathi
            default:
                return "Find the Missing Numbers"; // English
        }
    }

    private void setTTSLanguage(Locale locale) {
        if (tts != null) {
            int result = tts.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
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
}
