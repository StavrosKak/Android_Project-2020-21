package com.example.smn_arggregator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import twitter4j.Status;

public class PostArrayAdapter extends ArrayAdapter<Status> {

    private List<Status> postList;
    private final LayoutInflater inflater;
    private final int layoutResource;



    public PostArrayAdapter(@NonNull Context context, int resource, @NonNull List<Status> objects) {
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

        Status currentPost = postList.get(position);

        viewHolder.UserName.setText(currentPost.getUser().getName());
        viewHolder.postTxt.setText(currentPost.getText());
        Picasso.get().load(currentPost.getUser().get400x400ProfileImageURL()).into(viewHolder.UserImage);
        if (currentPost.getMediaEntities().length > 0) {
            Picasso.get().load(currentPost.getMediaEntities()[0].getMediaURL()).into(viewHolder.PostImage);
        }


        return convertView;

    }

    private class ViewHolder{
        final TextView UserName;
        final ImageView UserImage;
        final TextView postTxt;
        final ImageView PostImage;

        ViewHolder(View view){
            UserName = view.findViewById(R.id.UserName);
            UserImage = view.findViewById(R.id.UserImage);
            postTxt = view.findViewById(R.id.PostText);
            PostImage = view.findViewById(R.id.PostImage);
        }
    }
}
