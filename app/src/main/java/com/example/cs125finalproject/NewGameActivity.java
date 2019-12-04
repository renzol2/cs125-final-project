package com.example.cs125finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
