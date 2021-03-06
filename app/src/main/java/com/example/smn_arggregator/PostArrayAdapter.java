package com.example.smn_arggregator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class PostArrayAdapter extends ArrayAdapter<SMN_Posts> {

    private ArrayList<SMN_Posts> postList;
    private final LayoutInflater inflater;
    private final int layoutResource;



    public PostArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SMN_Posts> objects) {
        super(context, resource, objects);
        postList = objects;
        layoutResource = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(layoutResource,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SMN_Posts currentPost = postList.get(position);

        if(currentPost.getPost_id()!=-1){

            viewHolder.UserName.setText(currentPost.getPost_username());
            viewHolder.postTxt.setText(currentPost.getPost_caption());
            Picasso.get().load(currentPost.getPost_user_image()).into(viewHolder.UserImage);
            Picasso.get().load(currentPost.getPost_media_url()).into(viewHolder.PostImage);
            Picasso.get().load(R.drawable.twitter_logo).into(viewHolder.smnLogo);

        }else {
            viewHolder.UserName.setText(currentPost.getPost_username());
            viewHolder.postTxt.setText(currentPost.getPost_caption());
            Picasso.get().load(R.drawable.blank_image).into(viewHolder.UserImage);
            Picasso.get().load(currentPost.getPost_media_url()).into(viewHolder.PostImage);
            Picasso.get().load(R.drawable.instagram_logo).into(viewHolder.smnLogo);

        }

        return convertView;

    }

    private class ViewHolder{
        final TextView UserName;
        final ImageView UserImage;
        final TextView postTxt;
        final ImageView PostImage;
        final ImageView smnLogo;

        ViewHolder(View view){
            UserName = view.findViewById(R.id.UserName);
            UserImage = view.findViewById(R.id.UserImage);
            postTxt = view.findViewById(R.id.PostText);
            PostImage = view.findViewById(R.id.PostImage);
            smnLogo = view.findViewById(R.id.SMNlogo);
        }
    }
}
