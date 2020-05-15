package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public interface MessageModel {

    void sendMessage(@NonNull Message message);

    void deleteMessage(@NonNull Message message);

    @NonNull
    Flowable<Message> getMessages();
}
