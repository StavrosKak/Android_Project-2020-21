package com.example.smn_arggregator;

import android.telephony.AccessNetworkConstants;
import android.util.Log;

import java.util.ArrayList;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GetTwitterData {

    private Twitter twitter;

    public GetTwitterData(String ConsumerKey, String ConsumerSecretKey, String AccessToken, String AccessSecreteToken){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(ConsumerKey)
                .setOAuthConsumerSecret(ConsumerSecretKey)
                .setOAuthAccessToken(AccessToken)
                .setOAuthAccessTokenSecret(AccessSecreteToken);

        TwitterFactory tf = new TwitterFactory(cb.build());

        twitter = tf.getInstance();

    }


    public ArrayList<String> getTrends(){

        ArrayList<String> Hashtags = new ArrayList<>();

        try{
            // UK = 23424975  United States: 23424977 New York: 2459115
            Trends trends = twitter.getPlaceTrends(23424977);
            int count =0;

            for(Trend trend : trends.getTrends()){

                if(trend.getName().startsWith("#")){
                    Hashtags.add(trend.getName());
                }

                Log.d("Bull",count+"   "+trend.getName());
                count++;

            }

        }catch(TwitterException exception){
            exception.printStackTrace();
        }

        return Hashtags;
    }



}
