package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;

public interface EditPointContract {

    interface View extends BaseContract.View {

        void showDetails(@NonNull Point point);

        void showPhotos();
    }

    interface Presenter extends BaseContract.Presenter {

        void addPhoto();

        void save(@NonNull Point point);

        void cancel();

        void delete(@NonNull Point point);
    }

    interface Router extends BaseContract.Router {

        void openCamera();
    }
}
