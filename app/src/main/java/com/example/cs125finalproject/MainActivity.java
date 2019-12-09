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
 * The main screen of the app. App made by Renzo and Saurav :)
 *
 * App name: TRUMPED
 *
 * The game presents you with a Tweet/quote, and you have to guess if it was written by Trump or an AI.
 * The more questions you get right within the time limit, the higher your score!
 *
 * This class simply starts the game.
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
