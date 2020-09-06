package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMapModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMessageModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePointModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.data.model.MapModel;
import com.gmail.ivan.morozyk.mappy.data.model.MessageModel;
import com.gmail.ivan.morozyk.mappy.data.model.PointModel;
import com.gmail.ivan.morozyk.mappy.data.model.UserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapChatContract;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class ChatPresenter extends BasePresenter<MapChatContract.View>
        implements MapChatContract.Presenter {

    @NonNull
    private final MapModel mapModel = new FirestoreMapModel();

    @NonNull
    private final UserModel userModel = new FirestoreUserModel();

    @NonNull
    private final PointModel pointModel;

    @NonNull
    private final String mapId;

    @Nullable
    private MessageModel messageModel;

    @Nullable
    private User user;

    @Nullable
    private Map map;

    public ChatPresenter(@NonNull String mapId) {
        this.mapId = mapId;
        userModel.getSelf()
                 .subscribe(user -> this.user = user);

        pointModel = new FirestorePointModel(mapId);
    }

    @Override
    public void loadMessages() {
        mapModel.getMap(mapId)
                .subscribe(map -> {
                    this.map = map;
                    messageModel = new FirestoreMessageModel(map);
                    getViewState().showMessages(messageModel.getMessages()
                                                            .map(message -> {
                                                                message.setMessageOwner(Objects.equals(
                                                                        message.getSenderEmail(),
                                                                        Objects.requireNonNull(
                                                                                user)
                                                                               .getEmail())
                                                                                        ? Message.MessageOwn.YOU
                                                                                        : Message.MessageOwn.OTHER_MEMBER);
                                                                Log.d("TAG",
                                                                      "loadMessages: "
                                                                              + message.getSenderEmail()
                                                                              + message.getSenderName()
                                                                              + message.getMessageOwner());

                                                                return message;
                                                            }));
                });
    }

    @Override
    public void loadPoints() {
        getViewState().showPoints(pointModel.getPoints());
    }

    @Override
    public void loadPhotos() {

    }

    @Override
    public void sendTextMessage(@NonNull String message) {
        getViewState().clearMessage();
        Objects.requireNonNull(user);
        Message messageToSend =
                new Message(user.getName(), user.getEmail(), message, null, null, new Date());
        Objects.requireNonNull(messageModel)
               .sendMessage(messageToSend);
    }

    @Override
    public void sendPointMessage(@NonNull Point point) {
        getViewState().clearMessage();
        Objects.requireNonNull(user);
        Message message =
                new Message(user.getName(), user.getEmail(), null, null, point, new Date());
        Objects.requireNonNull(messageModel)
               .sendMessage(message);
    }

    @Override
    public void deleteMessages(@NonNull List<Message> messagesToDelete) {

    }

    @Override
    public void showPhoto(@NonNull Bitmap photo) {
        // TODO: 9/2/2020 show photo. will be created in another task
    }

    @Override
    public void showPoint(@NonNull Point point) {

    }

    @Override
    public void makePhoto() {

    }
}
