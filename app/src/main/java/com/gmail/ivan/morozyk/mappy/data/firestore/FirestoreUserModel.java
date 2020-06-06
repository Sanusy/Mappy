package com.gmail.ivan.morozyk.mappy.data.firestore;

import android.util.Log;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreUserModel implements UserModel {

    @NonNull
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void addUser(@NonNull User user) {
        db.collection("users")
          .whereEqualTo("email", user.getEmail())
          .get()
          .addOnCompleteListener(task -> {
              if (task.isSuccessful() && Objects.requireNonNull(task.getResult())
                                                .isEmpty()) {
                  db.collection("users")
                    .document(user.getEmail())
                    .set(user);
              }
          });
    }

    @NonNull
    @Override
    public Flowable<Map> getMaps(@NonNull User user) {
        return Flowable.create(subscriber -> {
            FirebaseFirestore.getInstance()
                             .collection("users")
                             .document(user.getId())
                             .collection("maps")
                             .addSnapshotListener((snapshot, error) -> {
                                 for (DocumentChange dc : Objects.requireNonNull(snapshot)
                                                                 .getDocumentChanges()) {
                                     if (dc.getType() == DocumentChange.Type.ADDED) {
                                         Objects.requireNonNull(dc.getDocument()
                                                                  .getDocumentReference("mapRef"))
                                                .get()
                                                .addOnSuccessListener(mapSnapshot -> {
                                                    subscriber.onNext(mapSnapshot.toObject(Map.class));
                                                })
                                                .addOnFailureListener(subscriber::onError);
                                     }
                                 }
                             });
        }, BackpressureStrategy.BUFFER);
    }

    @NonNull
    @Override
    public Single<User> getUser(@NonNull String email) {
        return Single.create(subscriber -> {
            db.collection("users")
              .document(email)
              .get()
              .addOnSuccessListener(documentSnapshot -> {
                  subscriber.onSuccess(documentSnapshot.toObject(User.class));
              })
              .addOnFailureListener(subscriber::onError);
        });
    }

    @NonNull
    @Override
    public Single<User> getSelf() {
        return Single.create(subscriber -> {
            FirebaseUser user = auth.getCurrentUser();
            String email;
            if (user != null) {
                email = user.getEmail();

                db.collection("users")
                  .document(Objects.requireNonNull(email))
                  .get()
                  .addOnSuccessListener(documentSnapshot -> {
                      subscriber.onSuccess(documentSnapshot.toObject(User.class));
                  })
                  .addOnFailureListener(subscriber::onError);
            } else {
                subscriber.onError(null);
            }
        });
    }
}
