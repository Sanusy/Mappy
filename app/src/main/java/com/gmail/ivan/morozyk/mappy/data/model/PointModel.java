package com.gmail.ivan.morozyk.mappy.data.model;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface PointModel {

    Single<Point> addPoint(@NonNull Point point);

    void deletePoint(@NonNull Point point);

    void editPoint(@NonNull Point point);

    void addPhoto(@NonNull Uri photoUri, @NonNull Point point);

    @NonNull
    Flowable<Point> getPoints();
}
