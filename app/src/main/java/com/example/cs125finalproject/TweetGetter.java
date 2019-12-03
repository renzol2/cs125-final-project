package com.example.cs125finalproject;

import android.os.AsyncTask;
import android.text.PrecomputedText;

import androidx.core.text.PrecomputedTextCompat;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetGetter {
    /** User to grab Tweets from. */
    private final String user;

    /** Tokens for Twitter Authentication. Will probably move these to a string resources page. */
    private static String AccessToken = "747317971110899712-C4T143wdNfFxnSQTBEnyi6LD0zT4eGX";
    private static String AccessSecret = "Cg0BOv7SqikaObUOX9i1OEz6Pz7bbj78QnvhskdpiCgGK";
    private static String ConsumerKey = "HTjt6QQpZ9pFqi3ojCKrF5ZEP";
    private static String ConsumerSecret = "5EyKqzk9n3JrxrOpB2pel6XBIPiQ5VoRzEn13LwTTMBMLIs3Rj";

    /** Twitter instance variable for getting Tweets.*/
    private Twitter twitter;

    /** List of tweets from Twitter4J. */
    private List<twitter4j.Status> tweetsList;

    /**
     * Sets instance variables.
     * @param setUser user screen name
     */
    public TweetGetter(String setUser) {
        // Setting user
        user = setUser;

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
     * Skeleton from https://stackoverflow.com/questions/2943161/get-tweets-of-a-public-twitter-profile
     * TODO: Not functional yet
     */
    private void grabTweets() {
        try {
            tweetsList = twitter.getUserTimeline(user);
            for (twitter4j.Status tweet : tweetsList) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }
}
