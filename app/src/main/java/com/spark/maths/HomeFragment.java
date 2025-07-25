package com.spark.maths;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    private LinearLayout learningLayout, quizLayout, gamesLayout;
    private ImageButton btnLearning,btnQuiz,btnGames ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize layout containers (not just buttons)
        learningLayout = view.findViewById(R.id.learningLayout);
        quizLayout = view.findViewById(R.id.quizLayout);
        gamesLayout = view.findViewById(R.id.gamesLayout);
        btnLearning = view.findViewById(R.id.btnLearning);
        btnQuiz = view.findViewById(R.id.btnQuiz);
        btnGames = view.findViewById(R.id.btnGames);

        // Set listeners on whole layout so clicking anywhere (image or text) works
        learningLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LearningActivity.class);
            startActivity(intent);
        });

        quizLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            startActivity(intent);
        });

        gamesLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GamesActivity.class);
            startActivity(intent);
        });

        btnLearning.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LearningActivity.class);
            startActivity(intent);
        });

        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            startActivity(intent);
        });

        btnGames.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GamesActivity.class);
            startActivity(intent);
        });

        return view;
    }
}



