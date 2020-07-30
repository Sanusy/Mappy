package com.gmail.ivan.morozyk.mappy.data.firestore;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.model.MapModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class FirestoreMapModel implements MapModel {

    @NonNull
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public Flowable<User> getUsers(@NonNull Map map) {
        List<Task<?>> tasks = new ArrayList<>();

        return Flowable.create(subscriber -> {
            tasks.add(db.collection("maps")
                        .document(map.getId())
                        .collection("users")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot userIdSnapshot : Objects.requireNonNull(
                                        task.getResult())) {
                                    tasks.add(Objects.requireNonNull(userIdSnapshot.getDocumentReference(
                                            "userRef"))
                                                     .get()
                                                     .addOnSuccessListener(userSnapshot -> {
                                                         subscriber.onNext(userSnapshot.toObject(
                                                                 User.class));
                                                     })
                                                     .addOnFailureListener(subscriber::onError));
                                }
                            }
                            Tasks.whenAllComplete(tasks)
                                 .addOnCompleteListener(completed -> subscriber.onComplete());
                        })
                        .addOnFailureListener(subscriber::onError));
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public void leaveMap(@NonNull User user, @NonNull Map map) {
        db.collection("users")
          .document(user.getId())
          .collection("maps")
          .document(map.getId())
          .delete();
        db.collection("maps")
          .document(map.getId())
          .collection("users")
          .document(user.getId())
          .delete();
    }

    @Override
    public void addUser(@NonNull User user, @NonNull Map map) {
        HashMap<String, Object> userRef = new HashMap<>();
        userRef.put("userRef",
                    db.collection("users")
                      .document(user.getId()));

        HashMap<String, Object> mapRef = new HashMap<>();
        mapRef.put("mapRef",
                   db.collection("maps")
                     .document(map.getId()));

        db.collection("maps")
          .document(map.getId())
          .collection("users")
          .document(user.getId())
          .set(userRef);

        db.collection("users")
          .document(user.getId())
          .collection("maps")
          .document(map.getId())
          .set(mapRef);
    }

    @Override
    @NonNull
    public Single<Map> addMap(@NonNull Map map,
                              @NonNull User creator,
                              @Nullable List<User> moreUsers) {
        List<User> users = new ArrayList<>();
        users.add(creator);
        if (moreUsers != null) {
            users.addAll(moreUsers);
        }

        Subject<Map> subject = PublishSubject.create();
        db.collection("maps")
          .add(map)
          .addOnSuccessListener(mapRef -> {

              HashMap<String, Object> mapRefContainer = new HashMap<>();
              mapRefContainer.put("mapRef", mapRef);

              for (User userToAdd : users) {
                  db.document(mapRef.getPath())
                    .collection("users")
                    .document(userToAdd.getId())
                    .set(userToAdd);

                  db.collection("users")
                    .document(userToAdd.getId())
                    .collection("maps")
                    .document(mapRef.getId())
                    .set(mapRefContainer);
              }

              mapRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        subject.onNext(documentSnapshot.toObject(Map.class));
                        subject.onComplete();
                    });
          });

        return subject.singleOrError();
    }

    @Override
    public void editMap(@NonNull Map map) {
        db.collection("maps")
          .document(map.getId())
          .update("title", map.getTitle(), "description", map.getDescription());
    }
}
