package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public interface PointModel {

    void addPoint(@NonNull Point point);

    void deletePoint(@NonNull Point point);

    void editPoint(@NonNull Point point);

    @NonNull
    Flowable<Point> getPoints();
}
