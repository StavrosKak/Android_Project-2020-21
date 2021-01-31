# Android_Project-2020-21
## What the app does :  
In this app a user can see the trending hashtags coming from twitter and then by choosing one he/she can see posts from twitter
and instagram containing this particular hashtag. Then by choosing one post he/she can see some details about the post and additionally,
regarding twitter posts someone can also see some replies to that post. Furthermore, a user is able to post a text with or without an image
on a facebook page or twitter and a random picture with or without caption on instagram.  

## Some configuration before using the app :  
Firstly, you have to create a twitter developer account and a facebook developer account in order to get the required tokens by creating an app in
both of them. The required permissions for the twitter app are read and write. For the facebook app you need to go to the Facebook Graph API Explorer
and choose the following permissions:  

- read_insights  
- pages_show_list  
- ads_management  
- business_management  
- instagram_basic  
- instagram_manage_comments  
- instagram_manage_insights  
- instagram_content_publish  
- pages_read_engagement  
- pages_read_user_content  
- pages_manage_posts  
- pages_manage_engagement  

You will also need a facebook page and an instagram business account connected to that facebook page. Once you are done with all of that
and you are also acquired the tokens then you have to write them down in gradle.properties file under the .gradle directory
as followed :  

The twitter tokens:  

CONSUMER_KEY="your_consumer_key"  
CONSUMER_SECRET_KEY="your_consumer_secrete_key"  
ACCESS_TOKEN="your_access_token"  
ACCESS_SECRET_TOKEN="your_access_secrete_token"  
</br>

The facebook tokens :  

APP_ID="your_app_id"  
APP_SECRET="your_app_secret"  
PAGE_ID="your_page_id"  
ACCESSFB_TOKEN="your_page_access_token"  

In my app i only use the PAGE_ID and ACCESSFB_TOKEN (which is the page acces token). The other ones are there in case of improvements or additional functionalities.
To get your page_id and page_access_token you need to go to the Facebook Graph API Explorer and make a GET request : me/accounts  

Finaly you need to give the app the permission to access internal storage so you need to go to Settings -> Apps -> SMN_Aggregetor -> Permissions and then
enable Storage.

## Enjoy !!!
