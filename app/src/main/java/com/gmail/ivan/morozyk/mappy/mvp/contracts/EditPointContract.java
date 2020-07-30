package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import android.net.Uri;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface EditPointContract {

    @StateStrategyType(SkipStrategy.class)
    interface View extends BaseContract.View {

        void showDetails(@NonNull Point point);

        void showPhotos(@NonNull Flowable<String> photos);

        @StateStrategyType(AddToEndSingleStrategy.class)
        void invalidTitle();

        @StateStrategyType(AddToEndSingleStrategy.class)
        void invalidDescription();

        void back();
    }

    interface Presenter extends BaseContract.Presenter {

        void addPhoto(@NonNull Uri photoUri);

        void save(@NonNull String pointTitle, @NonNull String pointDescription);

        void cancel();

        void delete();

        void loadPhotos();
    }

    interface Router extends BaseContract.Router {

        void openCamera();
    }
}
