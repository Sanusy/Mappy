package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import android.graphics.Bitmap;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapChatContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        void showMessages(@NonNull Flowable<Message> messageList);

        void showPoints(@NonNull Flowable<Point> pointList);

        void showPhotos();

        void showDeleteMode();

        void showChatMode();

        void clearMessage();

        void removePoint(@NonNull String pointId);

        void editPoint(@NonNull Point pointToEdit);
    }

    interface Presenter extends BaseContract.Presenter {

        void loadMessages();

        void loadPoints();

        void loadPhotos();

        void sendTextMessage(@NonNull String message);

        void sendPointMessage(@NonNull Point point);

        void deleteMessages(@NonNull List<Message> messagesToDelete);

        void showPhoto(@NonNull Bitmap photo);

        void showPoint(@NonNull Point point);

        void makePhoto();
    }

    interface Router extends BaseContract.Router {

        void openCamera();

        void openPhoto();

        void openPoint();
    }
}
