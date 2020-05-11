package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import android.graphics.Bitmap;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;

import java.util.List;

import androidx.annotation.NonNull;

public interface MapChatContract {

    interface View extends BaseContract.View {

        void showMessages(@NonNull List<Message> messageList);

        void showPoints(@NonNull List<Point> pointList);

        void showPhotos();

        void showDeleteMode();

        void showChatMode();
    }

    interface Presenter extends BaseContract.Presenter {

        void loadMessages();

        void loadPoints();

        void loadPhotos();

        void sendMessage(@NonNull Message message);

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
