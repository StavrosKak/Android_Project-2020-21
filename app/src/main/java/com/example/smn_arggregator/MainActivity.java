package com.example.smn_arggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button hastags_btn = findViewById(R.id.hastagBtn);
        Button posts_btn = findViewById(R.id.postBtn);
        Button story_btn = findViewById(R.id.storyBtn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buttonClicked = (Button)view;
                String BtnText = (String) buttonClicked.getText();

                if(BtnText.equals("Trending Hastags")){
                    Log.d("Bull",BtnText);
                    Intent intent = new Intent(MainActivity.this,Hashtags_Activity.class);
                    startActivity(intent);
                }
                else if(BtnText.equals("Post")){
                    Intent intent = new Intent(MainActivity.this,Post_Activity.class);
                    startActivity(intent);
                }else if(BtnText.equals("Story")){
                    Intent intent = new Intent(MainActivity.this,Story_Activity.class);
                    startActivity(intent);
                }

            }
        };

        hastags_btn.setOnClickListener(listener);
        posts_btn.setOnClickListener(listener);
        story_btn.setOnClickListener(listener);

    }
}