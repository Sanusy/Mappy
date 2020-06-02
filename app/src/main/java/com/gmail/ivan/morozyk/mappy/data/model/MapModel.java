package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Flowable;

public interface MapModel {

    @NonNull
    Flowable<User> getUsers(@NonNull Map map);

    void leaveMap(@NonNull User user, @NonNull Map map);

    void addUser(@NonNull User user, @NonNull Map map);

    void addMap(@NonNull Map map, @NonNull User creator, @Nullable User... moreUsers);

    void editMap(@NonNull Map map);
}
