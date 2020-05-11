package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;

import java.util.List;

import androidx.annotation.NonNull;

public interface MapListContract {

    interface View extends BaseContract.View {

        void showMaps(@NonNull List<Map> mapList);

        void showEmpty();
    }

    interface Presenter extends BaseContract.Presenter {

        void loadMaps();

        void newMap();

        void openMap(@NonNull Map map);

        void signOut();
    }

    interface Router {

        void openNewMap();

        void openMap(@NonNull Map map);
    }
}
