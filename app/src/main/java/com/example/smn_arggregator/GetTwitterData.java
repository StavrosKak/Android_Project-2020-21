package com.example.smn_arggregator;

import android.telephony.AccessNetworkConstants;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GetTwitterData {

    private static final String ConsumerKey = BuildConfig.ConsumerKey;
    private static final String ConsumerSecretKey = BuildConfig.ConsumerSecretKey;
    private static final String AccessToken = BuildConfig.AccessToken;
    private static final String AccessSecretToken = BuildConfig.AccessSecretToken;

    private Twitter twitter;

    public GetTwitterData(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(ConsumerKey)
                .setOAuthConsumerSecret(ConsumerSecretKey)
                .setOAuthAccessToken(AccessToken)
                .setOAuthAccessTokenSecret(AccessSecretToken);

        TwitterFactory tf = new TwitterFactory(cb.build());

        twitter = tf.getInstance();

    }


    public ArrayList<String> getTrends(){

        ArrayList<String> Hashtags = new ArrayList<>();

        try{
            // UK = 23424975  United States: 23424977 New York: 2459115
            Trends trends = twitter.getPlaceTrends(23424975);
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

    public List<Status> getPost(String hashtag){
        try {
            Query query = new Query(hashtag);
            query.setCount(10);
            query.setLang("en");
            query.setResultType(Query.ResultType.popular);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            // *****for debug*******
            for (Status tweet : tweets) {

                Log.d("Bull","@" + tweet.getUser().getName()+ " ---- " + tweet.getText());
            }
            //*********
            return tweets;

        } catch (TwitterException te) {
            te.printStackTrace();
            Log.d("Bull","Failed to search tweets: " + te.getMessage());

        }

        return null;
    }



}
