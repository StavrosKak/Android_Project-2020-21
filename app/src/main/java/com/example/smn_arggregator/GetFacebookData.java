package com.example.smn_arggregator;


import android.util.Log;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.instagram.IgUser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GetFacebookData {

    private static final String AppId = BuildConfig.AppId;
    private static final String AppSecret = BuildConfig.AppSecret;

    //private final FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(AppId,AppSecret);
    //private FacebookClient fbClientMe;

    private static final String pageAccessToken = BuildConfig.AccessFBToken;

    private final String pageID = "101977808564841";
    private FacebookClient fbClientPage;

    private IgUser igUser;




    public GetFacebookData(){
        try {

            fbClientPage = new DefaultFacebookClient(pageAccessToken);
            Page page = fbClientPage.fetchObject(pageID, Page.class,
                    Parameter.with("fields", "instagram_business_account"));

            igUser = page.getInstagramBusinessAccount();
            //String token=accessToken.getAccessToken();
            //fbClientMe = new DefaultFacebookClient(token);
        } catch (FacebookException ex) {     //So that you can see what went wrong
            ex.printStackTrace();  //in case you did anything incorrectly
        }


    }


    public ArrayList<SMN_Posts> getIGPosts(String hashtag){
        Log.d("IG","igUser ID :  "+igUser.getId());


        //FacebookType nodeID = fbClientPage.fetchObject("ig_hashtag_search", FacebookType.class,
                //Parameter.with("user_id", igUser.getId()),Parameter.with("q","coke"));


        JsonObject nodeId = fbClientPage.fetchObject("ig_hashtag_search",JsonObject.class,
                Parameter.with("user_id",igUser.getId()),Parameter.with("q",hashtag.replace("#","")));

        JsonArray nodeArray = nodeId.get("data").asArray();

        String hashtagID = null;

        if (nodeArray.size() > 0) {

            hashtagID = nodeArray.get(0).asObject().getString("id",null);

            Log.d("IG","node ID :   "+ hashtagID);

        }

        JsonObject igMediaID = fbClientPage.fetchObject(hashtagID+"/top_media",JsonObject.class,
                Parameter.with("user_id",igUser.getId()),Parameter.with("fields","id,caption,like_count,media_url,comments_count,timestamp"));
        //17843833456057225/recent_media?fields=id,caption,like_count,media_url,comments_count,timestamp&user_id=17841445611557468

        JsonArray mediaArray = igMediaID.get("data").asArray();

        ArrayList<SMN_Posts> igMediaIDArrayList = new ArrayList<>();


        if(mediaArray.size() > 0){

            for(int i = 0; i < mediaArray.size(); i++){

                SMN_Posts SMN_posts = new SMN_Posts();
                SMN_posts.setPost_id(-1);
                SMN_posts.setPost_caption(mediaArray.get(i).asObject().getString("caption",null));
                SMN_posts.setPost_like_count(mediaArray.get(i).asObject().getInt("like_count",111));
                SMN_posts.setPost_media_url(mediaArray.get(i).asObject().getString("media_url",null));
                SMN_posts.setPost_comments_count(mediaArray.get(i).asObject().getInt("comments_count",11111));
                SMN_posts.setPost_timestamp(mediaArray.get(i).asObject().getString("timestamp",null));
                SMN_posts.setPost_username("IG user");
                SMN_posts.setPost_user_image("src/main/res/drawable/blank_image.jpg");

                igMediaIDArrayList.add(SMN_posts);
            }
        }

        // *****for debug*******
        for (SMN_Posts igPosts :igMediaIDArrayList ) {

            Log.d("IG","IG media id : " + igPosts.getPost_id());
            Log.d("IG","IG media caption :  "+ igPosts.getPost_caption());
        }
        //**********************

        return igMediaIDArrayList;

    }



    public void igPost(String text){

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
