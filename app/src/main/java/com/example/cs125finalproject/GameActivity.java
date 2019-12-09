package com.example.cs125finalproject;

import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private final int scoreDecrease = -250;
    private static int highScore = 0;
    private int gameDuration;
    private boolean useGeoffChallen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tweetView = findViewById(R.id.tweet);

        // Getting information from previous intent.
        Intent intent = getIntent();
        gameDuration = intent.getIntExtra("gameDuration", 60000);
        useGeoffChallen = intent.getBooleanExtra("useGeoffChallen", false);

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
        scoreText = "Score: " + score + "\t\tHigh Score: " + highScore;
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
        CountDownTimer timer = new CountDownTimer(gameDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                int remainingSeconds = (int) millisUntilFinished / 1000;
                String time = "Time: " + remainingSeconds;
                timerText.setText(time);
            }

            public void onFinish() {
                if (score > highScore) {
                    highScore = score;
                }
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
     * @param url URL with HTTP GET request
     * @param objectName name of object in JSON
     */
    public void displayFromJSON(String url, String objectName) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                tweetView.setText(response.getString(objectName));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> System.out.println("ERROR!! " + error.getMessage()));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    /**
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

        displayFromJSON(url, quoteName);
    }

    public void displayRandomFakeTweet() {
        boolean decider;
        if (useGeoffChallen) {
            decider = random.nextBoolean();
        } else {
            decider = true;
        }
        if (decider) {
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
                tweetView.setText(tweet);
            } catch(IllegalArgumentException e) {
                String url = "https://api.whatdoestrumpthink.com/api/v1/quotes/personalized?q=Bernie%20Sanders";
                String objName = "message";
                displayFromJSON(url, objName);
            }
        } else {
            String url = "https://api.whatdoestrumpthink.com/api/v1/quotes/personalized?q=Geoff%20Challen";
            String objName = "message";
            displayFromJSON(url, objName);
        }

    }

    private void correctDialog() {
        // Update score.
        updateScore(scoreIncrease);

        // Show Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Correct! This Tweet is " + (realTweet ? "real" : "fake"));
        //builder.setPositiveButton("OK", (dialog, which) -> chooseRandomTweet());
        builder.setOnDismissListener(unused -> chooseRandomTweet());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void incorrectDialog() {
        // Update score.
        updateScore(scoreDecrease);

        // Show Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("TRUMPED! This Tweet is " + (realTweet ? "real" : "fake"));
        //builder.setPositiveButton("OK", (dialog, which) -> chooseRandomTweet());
        builder.setOnDismissListener(unused -> chooseRandomTweet());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateScore(int changeScore) {
        score += changeScore;
        scoreText = "Score: " + score + "\t\tHigh Score: " + highScore;
        scoreView.setText(scoreText);
    }

    private void showFinalScore() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Time's up! Your final score is: " + score + "\nHigh score: " + highScore);
        builder.setOnDismissListener(unused -> finish());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
