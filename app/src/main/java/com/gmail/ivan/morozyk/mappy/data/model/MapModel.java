package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MapModel {

    @NonNull
    Flowable<User> getUsers(@NonNull Map map);

    @NonNull
    Single<Map> getMap(@NonNull String mapId);

    void leaveMap(@NonNull User user, @NonNull Map map);

    void addUser(@NonNull User user, @NonNull Map map);

    @NonNull
    Single<Map> addMap(@NonNull Map map, @NonNull User creator, @Nullable List<User> moreUsers);

    void editMap(@NonNull Map map);
}
