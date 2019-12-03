package com.example.cs125finalproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

public class GameActivity extends AppCompatActivity {

    /** These are all tokens used for Twitter authentication... not sure where/when to use these yet. */

    private static String AccessToken = "AAAAAAAAAAAAAAAAAAAAAOG%2FAwEAAAAA8DxwzCrhKnTvrJSfxHF02gxSdEY%3DIrmYPtSyRUsu1UqzTdsQxgWQnICb1QbEtJmlHCJvkP9218u60l";
    private static String AccessSecret = "xxx";
    private static String ConsumerKey = "HTjt6QQpZ9pFqi3ojCKrF5ZEP";
    private static String ConsumerSecret = "5EyKqzk9n3JrxrOpB2pel6XBIPiQ5VoRzEn13LwTTMBMLIs3Rj";

    private TextView tweetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tweetView = findViewById(R.id.tweet);

        // Set the initial text.
        displayRandomTrumpTweet();

        // Make the button generate and display a new Tweet.
        Button newTweetButton = findViewById(R.id.submit);
        newTweetButton.setOnClickListener(unused -> displayRandomTrumpTweet());

        // Set the timer.
        TextView timerText = findViewById(R.id.timer);
        CountDownTimer timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int remainingSeconds = (int) millisUntilFinished / 1000;
                // TODO: Need to figure out how to get the timer to show in the text view...
                if (millisUntilFinished % 1000 == 0) {
                    timerText.setText(remainingSeconds);
                }
            }

            public void onFinish() {
                timerText.setText("Done!");
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
