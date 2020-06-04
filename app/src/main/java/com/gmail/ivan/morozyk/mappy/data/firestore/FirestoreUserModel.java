package com.gmail.ivan.morozyk.mappy.data.firestore;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
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
        List<Task<?>> tasks = new ArrayList<>();

        return Flowable.create(subscriber -> {
            tasks.add(db.collection("users")
                        .document(user.getId())
                        .collection("maps")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot mapIdSnapshot : Objects.requireNonNull(
                                        task.getResult())) {
                                    tasks.add(Objects.requireNonNull(mapIdSnapshot.getDocumentReference(
                                            "mapRef"))
                                                     .get()
                                                     .addOnSuccessListener(mapSnapshot -> {
                                                         subscriber.onNext(mapSnapshot.toObject(Map.class));
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
