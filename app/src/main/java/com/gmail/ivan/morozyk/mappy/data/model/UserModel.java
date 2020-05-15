package com.gmail.ivan.morozyk.mappy.data.model;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public interface UserModel {

    void addUser(@NonNull User user);

    @NonNull
    Flowable<Map> getMaps(@NonNull User user);

    boolean exists(@NonNull User user);
}
