package com.example.cs125finalproject;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Grabs Tweets directly from Twitter using Twitter4J.
 */
class TweetGetter {
    /** User to grab Tweets from. */
    private final String user;

    /** Tokens for Twitter Authentication. Will probably move these to a string resources page. */
    private static final String AccessToken = "747317971110899712-C4T143wdNfFxnSQTBEnyi6LD0zT4eGX";
    private static final String AccessSecret = "Cg0BOv7SqikaObUOX9i1OEz6Pz7bbj78QnvhskdpiCgGK";
    private static final String ConsumerKey = "HTjt6QQpZ9pFqi3ojCKrF5ZEP";
    private static final String ConsumerSecret = "5EyKqzk9n3JrxrOpB2pel6XBIPiQ5VoRzEn13LwTTMBMLIs3Rj";

    /** Twitter instance variable for getting Tweets.*/
    private Twitter twitter;

    /** List of tweets from Twitter4J. */
    private List<twitter4j.Status> tweetsList;

    /**
     * Sets instance variables.
     * @param setUser user screen name
     */
    TweetGetter(String setUser) {
        // Setting user
        user = setUser;
        tweetsList = new ArrayList<>();

        // Setting authorization keys for Twitter API Authorization
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(ConsumerKey)
                .setOAuthConsumerSecret(ConsumerSecret)
                .setOAuthAccessToken(AccessToken)
                .setOAuthAccessTokenSecret(AccessSecret);
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    /**
     * Public method to invoke AsyncTask that grabs Tweets.
     */
    void grabTweets() {
        AsyncTask<?,?,?> tweetGrabber = new TweetsAsyncTask();
        tweetGrabber.execute();
    }

    List<twitter4j.Status> getTweetsList() {
        try {
            tweetsList = twitter.getUserTimeline(user);
            return tweetsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweetsList;
    }

    /**
     * Private class that essentially creates a new thread not within the Activity/UI threads
     * that will safely grab Tweets using Twitter4J.
     */
    private final class TweetsAsyncTask extends AsyncTask<Object, Object, Object> {

        /**
         * Main function of class that actually gets Tweets.
         *
         * TODO: Need to send list of tweets to GameActivity instead of just printing them
         *
         * Taken primarily from:
         * https://github.com/Twitter4J/Twitter4J/blob/master/twitter4j-examples/src/main/java/twitter4j/examples/timeline/GetUserTimeline.java
         *
         * @param params useless, only needed to override function
         * @return also useless, only needed to override function
         */
        @Override
        protected String doInBackground(Object... params) {
            String resp = "success";
            try {
                tweetsList = twitter.getUserTimeline(user);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return resp;
        }
    }
}
