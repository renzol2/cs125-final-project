package com.example.cs125finalproject;

import java.util.List;
import java.util.Random;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.AlteredCharSequence;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

public class GameActivity extends AppCompatActivity {

    /** Displays the tweet. */
    private TextView tweetView;
    private TextView scoreView;
    private String scoreText;
    private boolean realTweet;
    private TweetGetter getter;
    private Random random;
    private int score;
    private final int scoreIncrease = 1000;
    private final int scoreDecrease = -50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tweetView = findViewById(R.id.tweet);

        // Getting fake tweets.
        getter = new TweetGetter("DeepDrumpf");
        getter.grabTweets();

        random = new Random();
        //Initial Tweet
        chooseRandomTweet();

        // Make the button generate and display a new Tweet.
        Button newTweetButton = findViewById(R.id.submit);
        newTweetButton.setOnClickListener(unused -> chooseRandomTweet());

        //Buttons for guessing whether Tweet is real or fake
        Button realButton = findViewById(R.id.realButton);
        Button fakeButton = findViewById(R.id.fakeButton);

        // Initialize and display score.
        scoreView = findViewById(R.id.score);
        scoreText = "Score: " + score;
        scoreView.setText(scoreText);

        //Launch AlertDialog based on correct or incorrect.
        realButton.setOnClickListener(v -> {
            if (realTweet) {
                correctDialog();
            } else {
                incorrectDialog();
            }
        });

        fakeButton.setOnClickListener(v -> {
            if (realTweet) {
                incorrectDialog();
            } else {
                correctDialog();
            }
        });

        // Set the timer.
        TextView timerText = findViewById(R.id.timer);
        CountDownTimer timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int remainingSeconds = (int) millisUntilFinished / 1000;
                String time = "Time: " + remainingSeconds;
                timerText.setText(time);
            }

            public void onFinish() {
                showFinalScore();
            }
        };

        // Start timer.
        timer.start();
    }

    /**
     * Picks between a real or fake Tweet.
     */
    private void chooseRandomTweet() {
        if (random.nextBoolean()) {
            realTweet = true;
            displayRandomTrumpTweet();
        } else {
            realTweet = false;
            displayRandomFakeTweet();
        }
    }

    /**
     * JSON Request code taken from:
     * https://developer.android.com/training/volley/request
     *
     * APIs used:
     * https://docs.tronalddump.io/
     * https://whatdoestrumpthink.com/api-docs/index.html
     */
    public void displayRandomTrumpTweet() {
        // Found two APIs with real Donald Trump tweets/quotes, so I figured we could use both!
        String url, quoteName;
        if (random.nextBoolean()) {
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
        int randIndex;
        String tweet;

        // If this function is called as the first Tweet to be displayed, TweetGetter can't grab
        // tweets fast enough to display in time, causing tweetsList to have a size of 0. So instead
        // we'll just display this meme quote LOL
        try {
            randIndex = random.nextInt(tweetsList.size());
            tweet = tweetsList.get(randIndex).getText()
                    .replace("[", "")
                    .replace("]","");
        } catch(IllegalArgumentException e) {
            tweet = "Liberal Clown Geoff Challen couldn't even respond properly to President " +
                    "Obama's State of the Union Speech without pouring sweat & chugging water!";
        }

        tweetView.setText(tweet);
    }

    private void correctDialog() {
        // Update score.
        updateScore(scoreIncrease);

        // Show Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Correct! This Tweet is " + (realTweet ? "real" : "fake"));
        builder.setPositiveButton("OK", (dialog, which) -> chooseRandomTweet());
        //builder.setOnDismissListener(unused -> chooseRandomTweet());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void incorrectDialog() {
        // Update score.
        updateScore(scoreDecrease);

        // Show Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("TRUMPED! This Tweet is " + (realTweet ? "real" : "fake"));
        builder.setPositiveButton("OK", (dialog, which) -> chooseRandomTweet());
        //builder.setOnDismissListener(unused -> chooseRandomTweet());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateScore(int changeScore) {
        score += changeScore;
        scoreText = "Score: " + score;
        scoreView.setText(scoreText);
    }

    private void showFinalScore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Time's up! Your final score is: " + score);
        builder.setOnDismissListener(unused -> finish());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
