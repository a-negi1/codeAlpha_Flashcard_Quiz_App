package com.example.flashcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitAnswerButton;
    private TextView scoreTextView;

    private List<String[]> flashcards;
    private int currentCardIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        loadFlashcards();
        if (flashcards.size() > 0) {
            showNextFlashcard();
        } else {
            Log.d("QuizActivity", "No flashcards available");
            Toast.makeText(this, "No flashcards available", Toast.LENGTH_SHORT).show();
            finish();
        }

        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void loadFlashcards() {
        SharedPreferences sharedPreferences = getSharedPreferences("flashcards", Context.MODE_PRIVATE);
        Set<String> flashcardsSet = sharedPreferences.getStringSet("flashcards_set", new HashSet<String>());

        flashcards = new ArrayList<>();
        if (flashcardsSet != null) {
            for (String card : flashcardsSet) {
                String[] parts = card.split(":");
                if (parts.length == 2) {
                    flashcards.add(parts);
                }
            }
        }
        Log.d("QuizActivity", "Loaded flashcards: " + flashcards.size());
    }

    private void showNextFlashcard() {
        if (currentCardIndex < flashcards.size()) {
            questionTextView.setText(flashcards.get(currentCardIndex)[0]);
        } else {
            Log.d("QuizActivity", "Quiz finished");
            Toast.makeText(this, "Quiz finished!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer() {
        if (currentCardIndex < flashcards.size()) {
            String correctAnswer = flashcards.get(currentCardIndex)[1];
            String userAnswer = answerEditText.getText().toString().trim();

            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                score++;
            }

            currentCardIndex++;
            scoreTextView.setText("Score: " + score);

            answerEditText.setText("");
            showNextFlashcard();
        }
    }
}
