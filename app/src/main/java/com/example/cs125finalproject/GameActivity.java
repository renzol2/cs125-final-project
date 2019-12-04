package com.example.cs125finalproject;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import twitter4j.Status;
import twitter4j.TwitterException;

public class GameActivity extends AppCompatActivity {

    /** Displays the tweet. */
    private TextView tweetView;
    private boolean realTweet;
    private TweetGetter getter;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tweetView = findViewById(R.id.tweet);

        random = new Random();
        //Initial Tweet
        chooseRandomTweet();

        // Getting fake tweets.
        getter = new TweetGetter("DeepDrumpf");
        getter.grabTweets();

        // Make the button generate and display a new Tweet.
        Button newTweetButton = findViewById(R.id.submit);
        newTweetButton.setOnClickListener(unused -> chooseRandomTweet());

        //Buttons for guessing whether Tweet is real or fake
        Button realButton = findViewById(R.id.realButton);
        Button fakeButton = findViewById(R.id.fakeButton);

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
     *
     * APIs used:
     * https://docs.tronalddump.io/
     * https://whatdoestrumpthink.com/api-docs/index.html
     */
    public void displayRandomTrumpTweet() {
        // Found two APIs with real Donald Trump tweets/quotes, so I figured we could use both!
        Random r = new Random();
        String url, quoteName;
        if (r.nextBoolean()) {
            url = "https://api.tronalddump.io/random/quote";
            quoteName = "value";
        } else {
            url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";
            quoteName = "message";
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                tweetView.setText(response.getString(quoteName));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> System.out.println("ERROR!! " + error.getMessage()));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void displayRandomFakeTweet() {
        List<twitter4j.Status> tweetsList = getter.getTweetsList();
        Random r = new Random();
        int randIndex = r.nextInt(tweetsList.size());
        String tweet = tweetsList.get(randIndex).getText();
        tweetView.setText(tweet);
    }
    private void chooseRandomTweet() {
        Random r = new Random();
        if (random.nextBoolean()) {
            displayRandomTrumpTweet();
        } else {
            displayRandomFakeTweet();
        }
    }
}
