
package com.spark.maths;

public class QuizModel {
    String question;
    String[] options;
    String correctAnswer;

    public QuizModel(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

