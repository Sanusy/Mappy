package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import android.util.Log;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePointModel;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapScreenContract;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MapScreenPresenter extends BasePresenter<MapScreenContract.View>
        implements MapScreenContract.Presenter {

    private static final String TAG = MapScreenPresenter.class.getSimpleName();

    @NonNull
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    private final String mapId;

    @NonNull
    private final PointModel pointModel;

    public MapScreenPresenter(@NonNull String mapId) {
        this.mapId = mapId;
        pointModel = new FirestorePointModel(mapId);
    }

    @Override
    public void loadPoints() {
        getViewState().showPoints(pointModel.getPoints());
        observeChanges();
    }

    @Override
    public void createPoint(@NonNull LatLng latLng) {
        getViewState().openNewPoint(latLng);
    }

    @Override
    public void loadPointDetails(@NonNull Point point) {
        getViewState().showPointDetails(point);
    }

    @Override
    public void showPhoto() {

    }

    @Override
    public void openEdit(@NonNull Point point) {
        getViewState().openEdit(point, mapId);
    }

    private void observeChanges() {
        db.collection("maps")
          .document(mapId)
          .collection("points")
          .addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot value,
                                  @Nullable FirebaseFirestoreException error) {
                  if (error != null) {
                      Log.e(TAG, "Error occurred while listen to point collection", error);
                      return;
                  }

                  for (DocumentChange dc : Objects.requireNonNull(value)
                                                  .getDocumentChanges()) {

                      Point point = dc.getDocument()
                                      .toObject(Point.class);

                      switch (dc.getType()) {
                          case MODIFIED:
                              getViewState().editPoint(point);
                              break;
                          case REMOVED:
                              getViewState().deletePoint(point);
                              break;
                      }
                  }
              }
          });
    }
}
