package com.gmail.ivan.morozyk.mappy.data.firestore;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import androidx.annotation.NonNull;

public class FirestorePhotoModel {

    @NonNull
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();

    @NonNull
    public static UploadTask addPhoto(@NonNull Uri photoUri) {
        return storage.getReference()
                      .child("images")
                      .child(Objects.requireNonNull(photoUri.getLastPathSegment()))
                      .putFile(photoUri);
    }

    @NonNull
    public static Task<Uri> getDownloadUri(@NonNull String photoUri) {
        return storage.getReference()
                      .child("images")
                      .child(photoUri)
                      .getDownloadUrl();
    }

    public static void deletePhoto(@NonNull String photoName) {
        storage.getReference()
               .child("images")
               .child(photoName)
               .delete();
    }
}
