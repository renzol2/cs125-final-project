package com.example.cs125finalproject;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.*;

public class TweetCrawler extends AppCompatActivity {
    private static String AccessToken = "AAAAAAAAAAAAAAAAAAAAAOG%2FAwEAAAAA8DxwzCrhKnTvrJSfxHF02gxSdEY%3DIrmYPtSyRUsu1UqzTdsQxgWQnICb1QbEtJmlHCJvkP9218u60l";
    private static String AccessSecret = "xxx";
    private static String ConsumerKey = "HTjt6QQpZ9pFqi3ojCKrF5ZEP";
    private static String ConsumerSecret = "5EyKqzk9n3JrxrOpB2pel6XBIPiQ5VoRzEn13LwTTMBMLIs3Rj";

    public TweetCrawler() { }

    public void grabTweet() {
        String url = "https://www.google.com/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
