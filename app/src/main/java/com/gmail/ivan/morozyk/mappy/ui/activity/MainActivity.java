package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//
//    Button capture;
//
//    private static final int REQUEST_PHOTO_CAPTURE = 0;
//
//    String currentPhotoPath;
//
//    Uri photoURI;
//
//    ImageView imageView;
//
//    Map map = new Map("xC5UxkJ22rX6rlLbcnAa", "OLOLOLO", "test");
//
//    FirestorePointModel pointModel = new FirestorePointModel(map.getId());
//
//    Button load;
//
//    Point newPoint = new Point("jVn2DCbYMV8lp9uYMAWT",
//                               "new point to delete",
//                               "new point description",
//                               5.15,
//                               25.3,
//                               null);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imageView = findViewById(R.id.image);
//
//        capture = findViewById(R.id.capture_photo);
//        capture.setOnClickListener(v -> {
//            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (captureIntent.resolveActivity(getPackageManager()) != null) {
//                // Create the File where the photo should go
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                }
//                // Continue only if the File was successfully created
//                if (photoFile != null) {
//                    photoURI = FileProvider.getUriForFile(this,
//                                                          "com.gmail.ivan.morozyk.mappy",
//                                                          photoFile);
//                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    startActivityForResult(captureIntent, REQUEST_PHOTO_CAPTURE);
//                }
//            }
//        });
//
//        load = findViewById(R.id.load_photo);
//        load.setOnClickListener(v -> {
//            pointModel.getPoints().flatMap(point -> point.getPhotoLinks()).subscribe(link -> {
//                Log.d("TAG", "onCreate: " + link);
//                FirestorePhotoModel.getDownloadUri(link).addOnCompleteListener(uri -> {
//                    Glide.with(MainActivity.this).load(uri.getResult()).into(imageView);
//                });
//            });
//        });
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//                                        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (REQUEST_PHOTO_CAPTURE == requestCode && resultCode == RESULT_OK) {
//            Log.d("TAG", "onActivityResult: " + photoURI.toString());
//
//            pointModel.addPhoto(photoURI, newPoint);
//        }
//    }
//}