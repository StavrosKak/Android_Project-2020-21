package com.example.smn_arggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.URLEntity;

public class PostViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);


        final List<Status> posts;
        final GetTwitterData TwitterObject = new GetTwitterData();
        posts =  TwitterObject.getPost(getIntent().getStringExtra("hashtag"));


        ListView postListView = findViewById(R.id.PostListView);
        PostArrayAdapter postArrayAdapter = new PostArrayAdapter(PostViewActivity.this,R.layout.list_record,posts);
        postListView.setAdapter(postArrayAdapter);

        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Status currentPost = posts.get(position);
                Toast.makeText(PostViewActivity.this, "You Click: "+ currentPost.getUser().getName(), Toast.LENGTH_SHORT).show();

                setContentView(R.layout.post_details);
                Button returnBtn = findViewById(R.id.returnBtn);
                TextView createdAt = findViewById(R.id.created_at);
                TextView favourites = findViewById(R.id.favourites);
                TextView retweets = findViewById(R.id.retweet);

                ArrayList<Status> replies;
                replies = TwitterObject.getReplies(currentPost.getUser().getScreenName(),currentPost.getId());
                ListView postDetailsListView = findViewById(R.id.PostDetailsListView);
                PostDetailsArrayAdapter postDetailsArrayAdapter = new PostDetailsArrayAdapter(PostViewActivity.this,R.layout.post_details_list_record,replies);
                postDetailsListView.setAdapter(postDetailsArrayAdapter);

                for (Status tweet : replies) {

                    Log.d("Bull","REPLIES??? @" + tweet.getUser().getName()+ " ---- " + tweet.getText());
                }

                createdAt.append(currentPost.getCreatedAt().toString());
                Log.d("Bull","Created at : "+currentPost.getCreatedAt().toString());
                favourites.append(currentPost.getFavoriteCount()+"");
                retweets.append(currentPost.getRetweetCount()+"");
                returnBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        setContentView(R.layout.activity_post_view);
                    }
                });



            }
        });



    }
}