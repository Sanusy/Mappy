package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface NewPointContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        @StateStrategyType(SkipStrategy.class)
        void showPhotos(@NonNull Flowable<String> photos);

        void invalidTitle();

        void invalidDescription();

        void back();
    }

    interface Presenter extends BaseContract.Presenter {

        void savePoint(@NonNull Point point);

        void cancel();

        void addPhoto(@NonNull Uri photoUri);
    }

    interface Router extends BaseContract.Router {

        void openNewPhoto();
    }
}
