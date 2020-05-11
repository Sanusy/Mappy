package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;

import androidx.annotation.NonNull;

public interface MapContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        void showDetails(@NonNull Map map);

        void showMap(@NonNull Map map);

        void showChat(@NonNull Map map);
    }

    interface Router extends BaseContract.Router {

        void showDetails(@NonNull Map map);

        void showMap(@NonNull Map map);

        void showChat(@NonNull Map map);
    }
}
