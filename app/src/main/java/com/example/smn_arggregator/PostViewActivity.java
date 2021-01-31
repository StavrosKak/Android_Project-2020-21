package com.example.smn_arggregator;

import androidx.annotation.NonNull;
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
import java.util.Collections;
import java.util.List;

import twitter4j.Status;

public class PostViewActivity extends AppCompatActivity {
    private ArrayList<SMN_Posts> smnPosts = new ArrayList<>();
    private PostArrayAdapter postArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);


        final List<Status> twitterPosts;
        final ArrayList<SMN_Posts> twitterConvertedPosts;

        final GetTwitterData TwitterObject = new GetTwitterData();
        GetFacebookData facebookDataObj = new GetFacebookData();

        twitterPosts =  TwitterObject.getPost(getIntent().getStringExtra("hashtag"));
        smnPosts = facebookDataObj.getIGPosts(getIntent().getStringExtra("hashtag"));

        SMN_Posts postObj = new SMN_Posts();
        twitterConvertedPosts = postObj.convert_twitterPost_to_SMN_post(twitterPosts);

        smnPosts.addAll(twitterConvertedPosts);
        Collections.shuffle(smnPosts);



        ListView postListView = findViewById(R.id.PostListView);
        postArrayAdapter = new PostArrayAdapter(PostViewActivity.this,R.layout.list_record,smnPosts);
        postListView.setAdapter(postArrayAdapter);

        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SMN_Posts currentPost = smnPosts.get(position);
                Toast.makeText(PostViewActivity.this, "You Click: "+ currentPost.getPost_username(), Toast.LENGTH_SHORT).show();

                setContentView(R.layout.post_details);
                Button returnBtn = findViewById(R.id.returnBtn);
                TextView createdAt = findViewById(R.id.created_at);
                TextView favourites = findViewById(R.id.favourites);
                TextView retweets = findViewById(R.id.retweet);

                ArrayList<Status> replies = new ArrayList<>();

                if(currentPost.getPost_id()!=-1){

                    replies = TwitterObject.getReplies(currentPost.getPost_username(),currentPost.getPost_id());
                    ListView postDetailsListView = findViewById(R.id.PostDetailsListView);
                    PostDetailsArrayAdapter postDetailsArrayAdapter = new PostDetailsArrayAdapter(PostViewActivity.this,R.layout.post_details_list_record,replies);
                    postDetailsListView.setAdapter(postDetailsArrayAdapter);
                }


                for (Status tweet : replies) {

                    Log.d("Bull","REPLIES??? @" + tweet.getUser().getName()+ " ---- " + tweet.getText());
                }

                createdAt.append(currentPost.getPost_timestamp());
                Log.d("Bull","Created at : "+currentPost.getPost_timestamp());
                favourites.append(currentPost.getPost_like_count()+"");
                retweets.setText("Comments : "+currentPost.getPost_comments_count());
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("smnArrayList", smnPosts);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.smnPosts.clear();
        this.smnPosts.addAll((ArrayList<SMN_Posts>) savedInstanceState.getSerializable("smnArrayList"));
        this.postArrayAdapter.notifyDataSetChanged();

    }


}