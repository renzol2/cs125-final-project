package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;


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

        // Set image.
        ImageView trumpImage = findViewById(R.id.trumpImage);
        String url = "https://api.tronalddump.io/random/meme";
        Picasso.get().load(url).into(trumpImage);
        // Reset image by clicking it (doesn't seem to work lol)
        trumpImage.setOnClickListener(v -> Picasso.get().load(url).into(trumpImage));

        final Button newGameButton = findViewById(R.id.newGame);
        newGameButton.setOnClickListener(unused -> startActivity());
    }

    private void startActivity() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

}
