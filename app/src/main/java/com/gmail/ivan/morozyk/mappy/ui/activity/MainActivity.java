package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePhotoModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePointModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//}

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();

    Button capture;

    private static final int REQUEST_PHOTO_CAPTURE = 0;

    String currentPhotoPath;

    Uri photoURI;

    ImageView imageView;

    Map map = new Map("WF8vg6lm7yODL5GrmFIG", "очень тебя люблю", "");

    FirestorePointModel pointModel = new FirestorePointModel(map.getId());

    Point newPoint = new Point("",
                               "new point to delete",
                               "new point description",
                               5.15,
                               25.3);

    Point pointToOperate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointModel.addPoint(newPoint).subscribe(point -> pointToOperate = point);

        imageView = findViewById(R.id.image);

        capture = findViewById(R.id.capture_photo);
        capture.setOnClickListener(v -> {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (captureIntent.resolveActivity(getPackageManager()) != null) {
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
                                                          "com.gmail.ivan.morozyk.mappy",
                                                          photoFile);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(captureIntent, REQUEST_PHOTO_CAPTURE);
                }
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
                                        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_PHOTO_CAPTURE == requestCode && resultCode == RESULT_OK) {
            Log.d("TAG", "onActivityResult: " + photoURI.toString());

            pointModel.addPhoto(photoURI, newPoint);
        }
    }
}