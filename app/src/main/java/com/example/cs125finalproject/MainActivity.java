package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;



/**
 * The main screen of the app. Made by Renzo and Saurav :)
 *
 * 12/3/2019:
 * OKAY so making an unscrambling sentence game is going to be hard, so instead I think we should
 * try a different idea that still uses APIs.
 *
 * App name: TRUMPED
 *
 * The game presents you with a Tweet, and you have to guess if it was written by Trump or an AI.
 * The more questions you get right within the time limit, the higher your score!
 *
 * Trump tweets taken from:
 * https://www.tronalddump.io/
 *
 * Fake trump tweets from:
 * https://twitter.com/deepdrumpf?lang=en
 *
 * Current challenges:
 * ** TODO: Get JSONRequests to work for DeepDrumpf tweets.
 * Possible solution: Twitter4J (unofficial Java library for Twitter API)
 *
 * ** TODO: Get scoring system to work
 * Honestly? shouldn't be that hard
 *
 * ** TODO: Making the UI pretty
 * Secondary to app functionality
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: make a button that goes to NewGameActivity
        final TextView textView = findViewById(R.id.textView);
        final Button newGameButton = findViewById(R.id.newGame);
        newGameButton.setOnClickListener(unused -> startActivity());
    }

    private void startActivity() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }


}
