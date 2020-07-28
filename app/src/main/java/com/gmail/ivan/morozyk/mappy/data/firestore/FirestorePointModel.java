package com.gmail.ivan.morozyk.mappy.data.firestore;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class FirestorePointModel implements PointModel {

    @NonNull
    private final FirebaseFirestore db;

    @NonNull
    private final String pointsCollectionPath;

    public FirestorePointModel(@NonNull String mapId) {
        this.db = FirebaseFirestore.getInstance();
        this.pointsCollectionPath = "maps/" + mapId + "/points";
    }

    @Override
    public void addPoint(@NonNull Point point) {
        db.collection(pointsCollectionPath)
          .add(point);
    }

    @Override
    public void deletePoint(@NonNull Point point) {
        db.collection(pointsCollectionPath)
          .document(point.getId())
          .delete();

        // TODO: 5/30/2020 make subcollections also delete
    }

    @Override
    public void editPoint(@NonNull Point point) {
        db.collection(pointsCollectionPath)
          .document(point.getId())
          .update("title", point.getTitle(), "description", point.getDescription());
    }

    @Override
    public void addPhoto(@NonNull Uri photoUri, @NonNull Point point) {
        FirestorePhotoModel.addPhoto(photoUri);

        HashMap<String, Object> photoName = new HashMap<>();
        photoName.put("photoName", photoUri.getLastPathSegment());

        db.collection(pointsCollectionPath)
          .document(point.getId())
          .collection("photos")
          .document(
                  Objects.requireNonNull(photoUri.getLastPathSegment()))
          .set(photoName);
    }

    @NonNull
    @Override
    public Flowable<Point> getPoints() {
        return Flowable.create(subscriber -> {
            db.collection(pointsCollectionPath)
              .addSnapshotListener((snapshot, error) -> {
                  for (DocumentChange dc : Objects.requireNonNull(snapshot)
                                                  .getDocumentChanges()) {
                      if (dc.getType() == DocumentChange.Type.ADDED) {
                          Point point = dc.getDocument()
                                          .toObject(Point.class);
                          Flowable links =
                                  Flowable.defer(() -> Flowable.create(linkSubscriber -> {
                                      db.collection(pointsCollectionPath)
                                        .document(point.getId())
                                        .collection("photos")
                                        .get()
                                        .addOnCompleteListener(taskPhotoLink -> {
                                            if (taskPhotoLink.isSuccessful()) {
                                                for (QueryDocumentSnapshot linkSnapshot : Objects.requireNonNull(
                                                        taskPhotoLink.getResult())) {
                                                    if (linkSnapshot.get(
                                                            "photoName") != null) {
                                                        linkSubscriber.onNext(linkSnapshot.get(
                                                                "photoName"));
                                                    }
                                                }
                                                linkSubscriber.onComplete();
                                            }
                                        });
                                  }, BackpressureStrategy.BUFFER));
                          point.setPhotoLinks(links);
                          subscriber.onNext(point);
                      }
                  }
              });
        }, BackpressureStrategy.BUFFER);
    }
}
