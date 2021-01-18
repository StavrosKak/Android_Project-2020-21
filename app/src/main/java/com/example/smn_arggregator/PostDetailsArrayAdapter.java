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

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class PostDetailsArrayAdapter extends ArrayAdapter<Status> {
    private ArrayList<Status> replyPostList;
    private final LayoutInflater inflater;
    private final int layoutResource;


    public PostDetailsArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Status> objects) {
        super(context, resource, objects);
        replyPostList = objects;
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

        Status currentPost = replyPostList.get(position);

        viewHolder.ReplyUserName.setText(currentPost.getUser().getName());
        viewHolder.ReplyPostTxt.setText(currentPost.getText());
        Picasso.get().load(currentPost.getUser().get400x400ProfileImageURL()).into(viewHolder.ReplyUserImage);


        return convertView;

    }



    private class ViewHolder{
        final TextView ReplyUserName;
        final ImageView ReplyUserImage;
        final TextView ReplyPostTxt;

        ViewHolder(View view){
            ReplyUserName = view.findViewById(R.id.ReplyUsrName);
            ReplyUserImage = view.findViewById(R.id.ReplyUserImage);
            ReplyPostTxt = view.findViewById(R.id.ReplyText);

        }
    }

}
