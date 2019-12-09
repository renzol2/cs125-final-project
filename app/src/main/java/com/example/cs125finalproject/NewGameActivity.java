package com.example.cs125finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Possible settings to include:
 * - set time limit
 * - enable custom names
 * - set custom names to use with random quote generators
 */
public class NewGameActivity extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        // TODO: figure out setup of activities
        Button startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(unused -> startActivity());
    }

    private void startActivity() {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }
}
