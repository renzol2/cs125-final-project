package com.example.cs125finalproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView tweetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tweetView = findViewById(R.id.tweet);

        // Set the initial text.
        displayRandomTrumpTweet();

        // Try getting Drumpf tweets.
        // Make the button generate and display a new Tweet.
        TweetGetter getter = new TweetGetter("DeepDrumpf");
        // Here we'll grab the tweets to display, but currently it's not working since I need to
        // work on something with AsyncTasks(?)

        Button newTweetButton = findViewById(R.id.submit);
        newTweetButton.setOnClickListener(unused -> displayRandomTrumpTweet());

        // Set the timer.
        TextView timerText = findViewById(R.id.timer);
        CountDownTimer timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int remainingSeconds = (int) millisUntilFinished / 1000;
                String time = "Time: " + remainingSeconds;
                timerText.setText(time);
            }

            public void onFinish() {
                finish();
            }
        };

        // Start timer.
        timer.start();
    }

    /**
     * Code taken from:
     * https://developer.android.com/training/volley/request
     * Change the URL to get a different JSON object (theoretically...)
     *
     * Current issue: Twitter requires authentication with consumer keys/secrets, and I don't know
     * how to set those up...
     */
    public void displayRandomTrumpTweet() {
        String url = "https://api.tronalddump.io/random/quote";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                tweetView.setText(response.getString("value"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> System.out.println("ERROR!! " + error.getMessage()));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
