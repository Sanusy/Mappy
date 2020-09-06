package com.gmail.ivan.morozyk.mappy.data.firestore;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.model.MessageModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

public class FirestoreMessageModel implements MessageModel {

    @NonNull
    private final FirebaseFirestore db;

    @NonNull
    private final String messageCollectionPath;

    public FirestoreMessageModel(@NonNull Map map) {
        db = FirebaseFirestore.getInstance();
        this.messageCollectionPath = "maps/" + map.getId() + "/messages";
    }

    @Override
    public void sendMessage(@NonNull Message message) {
        if (message.getPoint() == null) {
            db.collection(messageCollectionPath)
              .add(message);
        }

        if (message.getPoint() != null) {
            HashMap<String, Object> messageData = new HashMap<>();
            DocumentReference pointRef = Objects.requireNonNull(db.collection(messageCollectionPath)
                                                                  .getParent())
                                                .collection("points")
                                                .document(message.getPoint()
                                                                 .getId());

            messageData.put("senderName", message.getSenderName());
            messageData.put("senderEmail", message.getSenderEmail());
            messageData.put("point", pointRef);
            messageData.put("date", message.getDate());

            db.collection(messageCollectionPath)
              .add(messageData);
        }
    }

    @Override
    public void deleteMessage(@NonNull Message message) {
        db.collection(messageCollectionPath)
          .document(message.getId())
          .delete();

        if (message.getPhotoLink() != null) {
            FirestorePhotoModel.deletePhoto(message.getPhotoLink());
        }
    }

    @NonNull
    @Override
    public Flowable<Message> getMessages() {
//        // TODO: 6/1/2020 make this readable
//        List<Task<?>> tasks = new ArrayList<>();
//
//        return Flowable.create(subscriber -> {
//            tasks.add(db.collection(messageCollectionPath)
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())) {
//                                    java.util.Map<String, Object> data = snapshot.getData();
//
//                                    String text = data.get("text") != null
//                                                  ? Objects.requireNonNull(data.get("text"))
//                                                           .toString()
//                                                  : null;
//
//                                    String photoLink = data.get("photoLink") != null
//                                                       ? Objects.requireNonNull(data.get("photoLink"))
//                                                                .toString()
//                                                       : null;
//
//                                    Message message = new Message(snapshot.getId(),
//                                                                  Objects.requireNonNull(data.get(
//                                                                          "senderName"))
//                                                                         .toString(),
//                                                                  Objects.requireNonNull(data.get(
//                                                                          "senderEmail"))
//                                                                         .toString(),
//                                                                  text,
//                                                                  photoLink,
//                                                                  null,
//                                                                  Objects.requireNonNull(snapshot.getDate(
//                                                                          "date")));
//
//                                    if (snapshot.getDocumentReference("point") != null) {
//                                        Task pointT =
//                                                Objects.requireNonNull(snapshot.getDocumentReference(
//                                                        "point"))
//                                                       .get()
//                                                       .addOnSuccessListener(pointTask -> {
//                                                           message.setPoint(pointTask.toObject(
//                                                                   Point.class));
//                                                       })
//                                                       .addOnFailureListener(subscriber::onError);
//                                        tasks.add(pointT);
//                                        Tasks.whenAllComplete(pointT)
//                                             .addOnCompleteListener(completeTask -> subscriber.onNext(
//                                                     message));
//                                    } else {
//                                        subscriber.onNext(message);
//                                    }
//                                }
//                            }
//                            Tasks.whenAllComplete(tasks)
//                                 .addOnCompleteListener(t -> subscriber.onComplete());
//                        })
//                        .addOnFailureListener(subscriber::onError));
//        }, BackpressureStrategy.BUFFER);

        return Flowable.create(subscriber -> {
            db.collection(messageCollectionPath)
              .orderBy("date")
              .addSnapshotListener((snapshot, error) -> {
                  for (DocumentChange dc : Objects.requireNonNull(snapshot)
                                                  .getDocumentChanges()) {
                      if (dc.getType() == DocumentChange.Type.ADDED) {
                          DocumentSnapshot messageSnapshot = dc.getDocument();
                          java.util.Map<String, Object> data = messageSnapshot.getData();

                          String text = Objects.requireNonNull(data)
                                               .get("text") != null
                                        ? Objects.requireNonNull(data.get("text"))
                                                 .toString()
                                        : null;

                          String photoLink = data.get("photoLink") != null
                                             ? Objects.requireNonNull(data.get("photoLink"))
                                                      .toString()
                                             : null;

                          Message message = new Message(messageSnapshot.getId(),
                                                        Objects.requireNonNull(data.get(
                                                                "senderName"))
                                                               .toString(),
                                                        Objects.requireNonNull(data.get(
                                                                "senderEmail"))
                                                               .toString(),
                                                        text,
                                                        photoLink,
                                                        null,
                                                        Objects.requireNonNull(messageSnapshot.getDate(
                                                                "date")));

                          if (messageSnapshot.getDocumentReference("point") != null) {
                              Task pointT =
                                      Objects.requireNonNull(messageSnapshot.getDocumentReference(
                                              "point"))
                                             .get()
                                             .addOnSuccessListener(pointTask -> {
                                                 message.setPoint(pointTask.toObject(
                                                         Point.class));
                                             })
                                             .addOnFailureListener(subscriber::onError);

                              Tasks.whenAllComplete(pointT)
                                   .addOnCompleteListener(completeTask -> subscriber.onNext(
                                           message));
                          } else {
                              subscriber.onNext(message);
                          }
                      }
                  }
              });
        }, BackpressureStrategy.BUFFER);
    }
}
