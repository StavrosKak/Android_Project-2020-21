package com.example.smn_arggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import twitter4j.Status;

public class PostViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);


        List<Status> posts;
        GetTwitterData TwitterObject = new GetTwitterData();
        posts =  TwitterObject.getPost(getIntent().getStringExtra("hashtag"));

        ListView postListView = findViewById(R.id.PostListView);
        PostArrayAdapter postArrayAdapter = new PostArrayAdapter(PostViewActivity.this,R.layout.list_record,posts);
        postListView.setAdapter(postArrayAdapter);



    }
}