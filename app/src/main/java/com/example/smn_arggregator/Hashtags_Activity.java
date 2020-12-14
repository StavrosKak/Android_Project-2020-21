package com.example.smn_arggregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Hashtags_Activity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtags_);

        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ArrayList<String> Hashtags;

        GetTwitterData TwitterObject = new GetTwitterData();

        Hashtags = TwitterObject.getTrends();

        searchView = findViewById(R.id.search_bar);
        listView = findViewById(R.id.list_item);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,Hashtags);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Hashtags_Activity.this, "You Click: "+ parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Hashtags_Activity.this,PostViewActivity.class);
                intent.putExtra("hashtag",parent.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Hashtags_Activity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Hashtags_Activity.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //for(String has : Hashtags){
        //    Log.d("Bull ", has);
        //}


    }
}