package com.example.hw_3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int digitCount;
    private int randomNumber;
    private int attemptsLeft = 10;
    private final ArrayList<Integer> previousGuesses = new ArrayList<>();

    private TextView tvLastGuess, tvAttempts, tvHint;
    private EditText etGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        digitCount = getIntent().getIntExtra("digitCount", 2);
        generateRandomNumber();

        tvLastGuess = findViewById(R.id.textViewLastGuess);
        tvAttempts = findViewById(R.id.textViewRemainingAttempts);
        tvHint = findViewById(R.id.textViewHint);
        etGuess = findViewById(R.id.editTextGuess);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(v -> handleGuess());
    }

    private void generateRandomNumber() {
        int min = (int) Math.pow(10, digitCount - 1);
        int max = (int) Math.pow(10, digitCount) - 1;
        randomNumber = new Random().nextInt((max - min) + 1) + min;
    }

    @SuppressLint("SetTextI18n")
    private void handleGuess() {
        String guessInput = etGuess.getText().toString().trim();

        if (guessInput.length() != digitCount) {
            tvHint.setText("Please enter a " + digitCount + "-digit number.");
            return;
        }

        int guessedNumber;
        try {
            guessedNumber = Integer.parseInt(guessInput);
        } catch (NumberFormatException e) {
            tvHint.setText("Invalid number.");
            return;
        }

        previousGuesses.add(guessedNumber);
        tvLastGuess.setText("Your Last Guess: " + guessedNumber);
        attemptsLeft--;
        tvAttempts.setText("Remaining Attempts: " + attemptsLeft);

        if (guessedNumber == randomNumber) {
            showWinDialog();
        } else if (attemptsLeft == 0) {
            showGameOverDialog();
        } else {
            if (guessedNumber < randomNumber) {
                tvHint.setText("Try a bigger number.");
            } else {
                tvHint.setText("Try a smaller number.");
            }
        }

        etGuess.setText("");
    }

    private void showWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!");
        builder.setMessage("You guessed the correct number:" + randomNumber + "!!!" +
                "\n\nWould you like to play again?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> resetGame());
        builder.setNegativeButton("No", (dialog, which) -> finish());
        builder.show();
    }

    private void showGameOverDialog() {
        StringBuilder guessesString = new StringBuilder();
        for (int guess : previousGuesses) {
            guessesString.append(guess).append(", ");
        }
        if (guessesString.length() > 0)
            guessesString.setLength(guessesString.length() - 2); // remove last comma

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" Number Guessing Game -❌Game Over!");
        builder.setMessage("You've used all your attempts" +
                "\nThe number was: " + randomNumber +
                "\nYour guesses: [" + guessesString + "]" +
                "\n\nWould you like to play again?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> resetGame());
        builder.setNegativeButton("No", (dialog, which) -> finish());
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    private void resetGame() {
        previousGuesses.clear();
        attemptsLeft = 10;
        tvLastGuess.setText("Your Last Guess: ");
        tvHint.setText("");
        tvAttempts.setText("Remaining Attempts: 10");
        generateRandomNumber();
    }
}
