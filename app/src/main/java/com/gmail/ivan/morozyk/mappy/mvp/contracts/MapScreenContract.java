package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapScreenContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        @StateStrategyType(OneExecutionStateStrategy.class)
        void showPoints(@NonNull Flowable<Point> points);

        void showPointDetails(@NonNull Point point);

        void openNewPoint(@NonNull LatLng latLng);

        void openEdit(@NonNull Point point);

        @StateStrategyType(SkipStrategy.class)
        void deletePoint(@NonNull Point point);

        void editPoint(@NonNull Point point);
    }

    interface Presenter extends BaseContract.Presenter {

        void loadPoints();

        void createPoint(@NonNull LatLng latLng);

        void loadPointDetails(@NonNull Point point);

        void showPhoto();

        void openEdit(@NonNull Point point);
    }

    interface Router extends BaseContract.Router {

        void openNewPoint();

        void openEdit(@NonNull Point point);
    }
}
