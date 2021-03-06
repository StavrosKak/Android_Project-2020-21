package com.example.smn_arggregator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Story_Activity extends AppCompatActivity {

    private static final String AppId = BuildConfig.AppId;
    private ImageView imageView ;
    private String imagePath="";
    private Uri photoURI;
    private static final int GALLERY_REQUEST = 9;
    private static final int CAMERA_REQUEST = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_);

        imageView= findViewById(R.id.storyImage);
        final Button postBtn = findViewById(R.id.postStory);
        final Button imageBtn = findViewById(R.id.loadStory);
        final CheckBox twitterBox = findViewById(R.id.twitterStory);
        final CheckBox facebookBox = findViewById(R.id.fbStory);
        final CheckBox instagramBox = findViewById(R.id.igStory);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open dialog
                showImageOptionDialog();


            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(instagramBox.isChecked()){

                    Uri backgroundAssetUri = Uri.parse(imagePath);

                    Intent storiesIntent = new Intent("com.instagram.share.ADD_TO_STORY");
                    storiesIntent.setDataAndType(backgroundAssetUri,  "image/*");
                    storiesIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    storiesIntent.setPackage("com.instagram.android");
                    startActivity(storiesIntent);
                }

                if(facebookBox.isChecked()){
                    Uri backgroundAssetUri = Uri.parse(imagePath);

                    Intent intent = new Intent("com.facebook.stories.ADD_TO_STORY");
                    intent.setDataAndType(backgroundAssetUri, "image/*");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", AppId);
                    startActivity(intent);
                }

            }
        });


    }

    private void showImageOptionDialog() {
        final String[] options = getResources().getStringArray(R.array.image_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_image_options)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                getImageFromGallery();
                                break;
                            case 1:
                                capturePictureFromCamera();
                                break;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void capturePictureFromCamera() {
        //Intent cameraIntent = new Intent();
        //cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(cameraIntent, CAMERA_REQUEST);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.smn_arggregator",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }



    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        return image;
    }


    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the intent was to pick image, was successful and an image was picked
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null){


            //Get selected image uri here
            Uri selectedImage = data.getData();
            // Set the Image in ImageView
            imageView.setImageURI(selectedImage);

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

        }
        //Handle camera request
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            imageView.setImageURI(photoURI);

        }
    }


}