package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import android.net.Uri;
import android.util.Log;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePhotoModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePointModel;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewPointContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class NewPointPresenter extends BasePresenter<NewPointContract.View>
        implements NewPointContract.Presenter {

    private final List<Uri> photosToAdd = new ArrayList<>();

    private final Subject<String> photos = PublishSubject.create();

    private final PointModel pointModel;

    public NewPointPresenter(@NonNull String mapId) {
        pointModel = new FirestorePointModel(mapId);
    }

    @Override
    public void attachView(NewPointContract.View view) {
        super.attachView(view);
        getViewState().hideProgress();
        getViewState().showPhotos(photos.toFlowable(BackpressureStrategy.BUFFER));
    }

    @Override
    public void savePoint(@NonNull Point point) {
        boolean error = false;
        if ("".equals(point.getTitle())) {
            error = true;
            getViewState().invalidTitle();
        }
        if ("".equals(point.getDescription())) {
            error = true;
            getViewState().invalidDescription();
        }
        if (error) {
            return;
        }

        pointModel.addPoint(point)
                  .subscribe(addedPoint -> {
                      for (Uri photoUri : photosToAdd) {
                          pointModel.addPhoto(photoUri, addedPoint);
                      }
                  });

        getViewState().back();
        photos.onComplete();
    }

    @Override
    public void cancel() {
        getViewState().back();
        photos.onComplete();
    }

    @Override
    public void addPhoto(@NonNull Uri photoUri) {
        photosToAdd.add(photoUri);
        photos.onNext(photoUri.toString());
    }
}
