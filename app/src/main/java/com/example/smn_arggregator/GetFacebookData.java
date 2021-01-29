package com.example.smn_arggregator;


import android.net.Uri;
import android.util.Log;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.Account;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.restfb.types.instagram.IgMedia;
import com.restfb.types.instagram.IgUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GetFacebookData {

    private static final String AppId = BuildConfig.AppId;
    private static final String AppSecret = BuildConfig.AppSecret;

    //private final FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(AppId,AppSecret);
    //private FacebookClient fbClientMe;

    private static final String pageAccessToken = BuildConfig.AccessFBToken;

    private final String pageID = "101977808564841";
    private FacebookClient fbClientPage;





    public GetFacebookData(){
        try {

            fbClientPage = new DefaultFacebookClient(pageAccessToken);
            //String token=accessToken.getAccessToken();
            //fbClientMe = new DefaultFacebookClient(token);
        } catch (FacebookException ex) {     //So that you can see what went wrong
            ex.printStackTrace();  //in case you did anything incorrectly
        }


    }


    public void igPost(String text){

        Page page = fbClientPage.fetchObject(pageID, Page.class,
                Parameter.with("fields", "instagram_business_account"));

        IgUser igUser = page.getInstagramBusinessAccount();

        Log.d("IG","igUser ID :  "+igUser.getId());
        //17841445611557468


        FacebookType containerID;

        containerID = fbClientPage.publish(igUser.getId() + "/media", FacebookType.class,
                        Parameter.with("caption", text), Parameter.with("image_url", "https://picsum.photos/200"));


        Log.d("IG","ContainerID   "+containerID.getId());

        fbClientPage.publish(igUser.getId() + "/media_publish", FacebookType.class,
                    Parameter.with("creation_id", containerID.getId()));



    }

    public void fbPost(String text, String imagePath) {

        if(!imagePath.isEmpty()){
            try {
                fbClientPage.publish(pageID + "/photos",FacebookType.class, BinaryAttachment.with("testImage.jpg",new FileInputStream(imagePath)),
                        Parameter.with("message", text));

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }

        }
        else
            fbClientPage.publish(pageID + "/feed", FacebookType.class, Parameter.with("message",  text));



    }



}
