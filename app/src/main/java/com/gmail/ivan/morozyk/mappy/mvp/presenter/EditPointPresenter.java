package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePhotoModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePointModel;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.EditPointContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class EditPointPresenter extends BasePresenter<EditPointContract.View>
        implements EditPointContract.Presenter {

    private final PointModel pointModel;

    private final List<Uri> photosToAdd = new ArrayList<>();

    private final Subject<String> photos = PublishSubject.create();

    private Point editedPoint;

    private final String pointId;

    public EditPointPresenter(@NonNull String pointId, @NonNull String mapId) {
        pointModel = new FirestorePointModel(mapId);

        this.pointId = pointId;
    }

    @Override
    public void addPhoto(@NonNull Uri photoUri) {
        photosToAdd.add(photoUri);
        photos.onNext(photoUri.toString());
    }

    @Override
    public void save(@NonNull String pointTitle, @NonNull String pointDescription) {
        boolean error = false;
        if ("".equals(pointTitle)) {
            error = true;
            getViewState().invalidTitle();
        }
        if ("".equals(pointDescription)) {
            error = true;
            getViewState().invalidDescription();
        }
        if (error) {
            return;
        }

        editedPoint.setTitle(pointTitle);
        editedPoint.setDescription(pointDescription);

        for (Uri photoUri : photosToAdd) {
            pointModel.addPhoto(photoUri, editedPoint);
        }

        pointModel.editPoint(editedPoint);

        getViewState().back();
    }

    @Override
    public void cancel() {
        getViewState().back();
        photos.onComplete();
    }

    @Override
    public void delete() {
        Objects.requireNonNull(editedPoint.getPhotoLinks())
               .subscribe(FirestorePhotoModel::deletePhoto);
        pointModel.deletePoint(editedPoint);
        getViewState().back();
    }

    @Override
    public void loadPhotos() {
        pointModel.getPoints()
                  .subscribe(point -> {
                      if (point.getId()
                               .equals(pointId)) {
                          editedPoint = point;
                          getViewState().showDetails(editedPoint);

                          Subject<String> uriSubject = PublishSubject.create();
                          Objects.requireNonNull(editedPoint.getPhotoLinks())
                                 .subscribe(uri -> {
                                     FirestorePhotoModel.getDownloadUri(uri)
                                                        .addOnCompleteListener(task -> uriSubject.onNext(
                                                                Objects.requireNonNull(task.getResult())
                                                                       .toString()));
                                 });

                          getViewState().showPhotos(uriSubject.toFlowable(BackpressureStrategy.BUFFER)
                                                              .mergeWith(Flowable.fromIterable(
                                                                      photosToAdd)
                                                                                 .map(
                                                                                         Uri::toString))
                                                              .mergeWith(photos.toFlowable(
                                                                      BackpressureStrategy.BUFFER)));

                          getViewState().hideProgress();
                      }
                  });
    }
}
