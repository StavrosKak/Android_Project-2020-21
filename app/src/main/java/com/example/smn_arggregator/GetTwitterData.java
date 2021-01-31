package com.example.smn_arggregator;

import android.telephony.AccessNetworkConstants;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
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
        ArrayList<Integer> woeid = new ArrayList<>();
        woeid.add(23424975);
        woeid.add(23424977);
        woeid.add(1100968);
        woeid.add(2459115);

        for(int i=0 ; i<4;i++)
        {

            try{
                // UK = 23424975  United States: 23424977 New York: 2459115
                Trends trends = twitter.getPlaceTrends(woeid.get(i));
                int count =0;

                for(Trend trend : trends.getTrends()){

                    if(trend.getName().startsWith("#")){
                        if(!Hashtags.contains(trend.getName())){
                            Hashtags.add(trend.getName());
                        }

                    }
                    Log.d("Bull",count+"   "+trend.getName());
                    count++;

                }

            }catch(TwitterException exception){
                exception.printStackTrace();
            }

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

    public void postTweet(String text, String path){

        File file = new File(path);

        StatusUpdate status = new StatusUpdate(text); // set the text to be uploaded here.

        if(!path.isEmpty())
            status.setMedia(file); // set the image to be uploaded here.

        try {
            twitter.updateStatus(status);
            Log.d("Bull","Manage to post tweet: ");

        } catch (TwitterException e) {
            e.printStackTrace();
            Log.d("Bull","Failed to post tweet: " + e.getMessage());
        }

    }

    public ArrayList<Status> getReplies(String screenName, long tweetID) {
        ArrayList<Status> replies = new ArrayList<>();

        try {
            Query query = new Query("to:" + screenName + " since_id:" + tweetID);
            //query.setCount(10);
            //query.setLang("en");
            QueryResult results;

            // if you want all the replies just do the do - while loop
            //do {
                results = twitter.search(query);
                List<Status> tweets = results.getTweets();

                for (Status tweet : tweets)
                    if (tweet.getInReplyToStatusId() == tweetID)
                        replies.add(tweet);
            //} while ((query = results.nextQuery()) != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replies;
    }



}
