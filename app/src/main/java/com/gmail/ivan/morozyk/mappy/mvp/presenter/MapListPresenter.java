package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapListContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import androidx.annotation.NonNull;

public class MapListPresenter extends BasePresenter<MapListContract.View>
        implements MapListContract.Presenter {

    @NonNull
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private final FirestoreUserModel userModel = new FirestoreUserModel();

    @Override
    public void loadMaps() {
        getViewState().showMaps(userModel.getSelf()
                                         .map(userModel::getMaps)
                                         .toFlowable()
                                         .flatMap(maps -> maps.cache()
                                                              .doAfterNext(map -> getViewState().hideProgress())
                                                 ));
        observeMapChanges();
    }

    @Override
    public void emptyMap(boolean empty) {
        getViewState().showEmpty(empty);
    }

    @Override
    public void newMap() {
        getViewState().openNewMap();
    }

    @Override
    public void openMap(@NonNull Map map) {
        getViewState().openMap(map);
    }

    @Override
    public void logOut() {
        auth.signOut();
        getViewState().logOut();
    }

    private void observeMapChanges() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userModel.getSelf()
                 .subscribe(user -> {
                     db.collection("users")
                       .document(user.getId())
                       .collection("maps")
                       .addSnapshotListener((snapshot, error) -> {
                           for (DocumentChange dc : Objects.requireNonNull(
                                   snapshot)
                                                           .getDocumentChanges()) {
                               if (dc.getType() == DocumentChange.Type.REMOVED) {
                                   getViewState().removeMap(dc.getDocument()
                                                              .getId());
                               }
                           }
                       });
                 });

        db.collection("maps")
          .addSnapshotListener((snapshot, error) -> {
              for (DocumentChange dc : Objects.requireNonNull(snapshot)
                                              .getDocumentChanges()) {
                  if (dc.getType() == DocumentChange.Type.MODIFIED) {
                      getViewState().editMap(dc.getDocument()
                                               .toObject(Map.class));
                  }
              }
          });
    }
}
