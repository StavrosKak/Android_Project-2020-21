package com.example.smn_arggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Post_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_);

        Button postBtn = findViewById(R.id.postButton);

       postBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CheckBox twitterBox = findViewById(R.id.twitterBox);
               CheckBox facebookBox = findViewById(R.id.facebookBox);
               CheckBox instagramBox = findViewById(R.id.instagramBox);
               EditText postEditText = findViewById(R.id.textField);

               Log.d("Bull",postEditText.getText().toString());
           }
       });


    }
}