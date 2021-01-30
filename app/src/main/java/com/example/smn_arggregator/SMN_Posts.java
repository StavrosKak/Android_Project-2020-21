package com.example.smn_arggregator;

import android.provider.Telephony;
import android.util.Log;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;

public class SMN_Posts implements Serializable {
    private long post_id;
    private String post_username;
    private String post_user_image;
    private String post_caption;
    private int post_like_count;
    private String post_media_url;
    private int post_comments_count;
    private String post_timestamp;

    public SMN_Posts(){

    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public long getPost_id() {
        return post_id;
    }

    public String getPost_caption() {
        return post_caption;
    }

    public void setPost_caption(String post_caption) {
        this.post_caption = post_caption;
    }

    public int getPost_like_count() {
        return post_like_count;
    }

    public void setPost_like_count(int post_like_count) {
        this.post_like_count = post_like_count;
    }

    public String getPost_media_url() {
        return post_media_url;
    }

    public void setPost_media_url(String post_media_url) {
        this.post_media_url = post_media_url;
    }

    public int getPost_comments_count() {
        return post_comments_count;
    }

    public void setPost_comments_count(int post_comments_count) {
        this.post_comments_count = post_comments_count;
    }

    public String getPost_timestamp() {
        return post_timestamp;
    }

    public void setPost_timestamp(String post_timestamp) {
        this.post_timestamp = post_timestamp;
    }

    public String getPost_username() {
        return post_username;
    }

    public void setPost_username(String post_username) {
        this.post_username = post_username;
    }

    public String getPost_user_image() {
        return post_user_image;
    }

    public void setPost_user_image(String post_user_image) {
        this.post_user_image = post_user_image;
    }

    public ArrayList<SMN_Posts> convert_twitterPost_to_SMN_post(List<Status> twitterPosts){

        ArrayList<SMN_Posts> smn_posts = new ArrayList<>();

        for (Status tweet :twitterPosts ) {

            SMN_Posts post = new SMN_Posts();

            post.post_id = tweet.getId();
            post.post_username = tweet.getUser().getScreenName();
            post.post_user_image = tweet.getUser().get400x400ProfileImageURL();
            post.post_caption = tweet.getText();
            if (tweet.getMediaEntities().length > 0) {
                post.post_media_url = tweet.getMediaEntities()[0].getMediaURL();
            }
            //Date date = tweet.getCreatedAt();
           // DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            post.post_timestamp = tweet.getCreatedAt().toString(); //dateFormat.format(date);
            post.post_like_count = tweet.getFavoriteCount();
            post.post_comments_count = tweet.getRetweetCount();

            smn_posts.add(post);

        }

        return smn_posts;
    }

}
