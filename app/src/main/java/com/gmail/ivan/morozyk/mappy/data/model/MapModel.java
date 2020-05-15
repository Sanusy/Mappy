package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public interface MapModel {

    @NonNull
    Flowable<User> getUsers(@NonNull Map map);

    void leaveMap(@NonNull User user);

    void addUser(@NonNull User user);

    void addMap(@NonNull Map map);

    void editMap(@NonNull Map map);
}
