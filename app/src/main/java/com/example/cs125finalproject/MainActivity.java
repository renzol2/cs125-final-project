package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;

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

        // Following instructions from:
        // https://github.com/twitter-archive/twitter-kit-android/wiki/Getting-Started

        // Initializing Twitter Kit...
        Twitter.initialize(this);
    }
}
