package com.example.hw_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroupDigits;
    Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupDigits = findViewById(R.id.radioGroupDigit);
        btnStartGame = findViewById(R.id.btnStart);

        btnStartGame.setOnClickListener(v -> {
            int selectedId = radioGroupDigits.getCheckedRadioButtonId();

            int digitCount;
            if (selectedId == R.id.radioButton2Digit) {
                digitCount = 2;
            } else if (selectedId == R.id.radioButton3Digit) {
                digitCount = 3;
            } else if (selectedId == R.id.radioButton4Digit) {
                digitCount = 4;
            } else {
                Toast.makeText(this, "Please select a difficulty level", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("digitCount", digitCount);
            startActivity(intent);
        });
    }
}
