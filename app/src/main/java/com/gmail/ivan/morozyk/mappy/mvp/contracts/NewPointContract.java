package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;

public interface NewPointContract {

    interface View extends BaseContract.View {

        void showPhotos();
    }

    interface Presenter extends BaseContract.Presenter {

        void savePoint(@NonNull Point point);

        void cancel();

        void addPhoto();
    }

    interface Router extends BaseContract.Router {

        void openNewPhoto();
    }
}
