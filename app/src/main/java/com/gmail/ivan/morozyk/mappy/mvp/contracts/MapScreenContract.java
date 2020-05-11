package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;

public interface MapScreenContract {

    interface View extends BaseContract.View {

        void showPoints();

        void showPointDetails(@NonNull Point point);
    }

    interface Presenter extends BaseContract.Presenter {

        void loadPoints();

        void createPoint();

        void loadPointDetails(@NonNull Point point);

        void showPhoto();

        void openEdit(@NonNull Point point);
    }

    interface Router extends BaseContract.Router {

        void openNewPoint();

        void openEdit(@NonNull Point point);
    }
}
