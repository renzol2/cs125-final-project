package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twitter.sdk.android.core.Twitter;

import org.json.JSONObject;


/**
 * The main screen of the app. Made by Renzo and Saurav :)
 *
 * Basic idea so far: Tweet Unscrambler
 *
 * The user is presented with any random tweet, except its words have been scrambled up. The user
 * has to put the tweet back together in its original form in as little time as possible.
 *
 * Points can be awarded based on completion accuracy or time of completion.
 *
 * Things to do:
 * - find out how to use Twitter API lol
 * - find out how to make a UI
 * - basically everything...
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // does this set up authentication?
        Twitter.initialize(this);

        // TODO: make a button that goes to NewGameActivity
        final TextView textView = findViewById(R.id.textView);
        final Button newGameButton = findViewById(R.id.newGame);
        newGameButton.setOnClickListener(unused -> startActivity());

        // scratching the Twitter thing... trying Volley requests instead
        grabTweet();
    }

    private void startActivity() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    /**
     * Code taken from:
     * https://developer.android.com/training/volley/request
     * Change the URL to get a different JSON object (theoretically...)
     *
     * Current issue: Twitter requires authentication with consumer keys/secrets, and I don't know
     * how to set those up...
     */
    public void grabTweet() {
        String url = "https://archive.org/download/archiveteam-twitter-stream-2019-08/twitter_stream_2019_08_01.tar/01%2F00%2F29.json.bz2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> System.out.println("Response: " + response),
                        error -> System.out.println("ERROR!! " + error.getMessage()));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
