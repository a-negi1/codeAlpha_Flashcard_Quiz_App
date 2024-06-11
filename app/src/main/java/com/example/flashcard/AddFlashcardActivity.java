package com.example.flashcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class AddFlashcardActivity extends AppCompatActivity {

    private EditText questionEditText;
    private EditText answerEditText;
    private Button saveFlashcardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        questionEditText = findViewById(R.id.questionEditText);
        answerEditText = findViewById(R.id.answerEditText);
        saveFlashcardButton = findViewById(R.id.saveFlashcardButton);

        saveFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionEditText.getText().toString().trim();
                String answer = answerEditText.getText().toString().trim();

                if (!question.isEmpty() && !answer.isEmpty()) {
                    Log.d("AddFlashcardActivity", "Saving Flashcard: " + question + " - " + answer);
                    saveFlashcard(question, answer);
                    finish();
                } else {
                    Log.d("AddFlashcardActivity", "Fields are empty");
                    Toast.makeText(AddFlashcardActivity.this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFlashcard(String question, String answer) {
        SharedPreferences sharedPreferences = getSharedPreferences("flashcards", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> flashcards = sharedPreferences.getStringSet("flashcards_set", new HashSet<String>());
        if (flashcards == null) {
            flashcards = new HashSet<>();
        }
        flashcards.add(question + ":" + answer);

        editor.putStringSet("flashcards_set", flashcards);
        editor.apply();
        Log.d("AddFlashcardActivity", "Flashcard Saved");
    }
}
