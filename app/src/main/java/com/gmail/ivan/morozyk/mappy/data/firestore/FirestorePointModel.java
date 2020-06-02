package com.gmail.ivan.morozyk.mappy.data.firestore;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<Task<?>> tasks = new ArrayList<>();

        return Flowable.create(sub -> {
            tasks.add(db.collection(pointsCollectionPath)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
                                    Point point = snapshot.toObject(Point.class);
                                    Flowable links =
                                            Flowable.defer(() -> Flowable.create(subscriber -> {
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
                                                                  subscriber.onNext(linkSnapshot.get(
                                                                          "photoName"));
                                                              }
                                                          }
                                                          subscriber.onComplete();
                                                      }
                                                  });
                                            }, BackpressureStrategy.BUFFER));
                                    point.setPhotoLinks(links);
                                    sub.onNext(point);
                                }
                            }
                            Tasks.whenAllComplete(tasks)
                                 .addOnCompleteListener(completed -> sub.onComplete());
                        }));
        }, BackpressureStrategy.BUFFER);
    }
}
