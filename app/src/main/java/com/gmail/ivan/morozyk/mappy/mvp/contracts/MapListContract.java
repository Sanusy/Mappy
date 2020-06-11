package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapListContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        @StateStrategyType(SkipStrategy.class)
        void showMaps(@NonNull Flowable<Map> maps);

        void showEmpty(boolean empty);

        void removeMap(@NonNull String mapId);

        void editMap(@NonNull Map map);

        void openNewMap();

        void openMap(@NonNull Map map);

        void logOut();
    }

    interface Presenter extends BaseContract.Presenter {

        void loadMaps();

        void emptyMap(boolean empty);

        void newMap();

        void openMap(@NonNull Map map);

        void logOut();
    }

    interface Router {

        void openNewMap();

        void openMap(@NonNull Map map);
    }
}
