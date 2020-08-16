package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMapModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.data.model.MapModel;
import com.gmail.ivan.morozyk.mappy.data.model.UserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapDetailsContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class MapDetailsPresenter extends BasePresenter<MapDetailsContract.View>
        implements MapDetailsContract.Presenter {

    @NonNull
    private final String mapId;

    @NonNull
    private final MapModel mapModel = new FirestoreMapModel();

    @NonNull
    private final UserModel userModel = new FirestoreUserModel();

    @NonNull
    private final List<User> users = new ArrayList<>();

    @Nullable
    private Map map;

    public MapDetailsPresenter(@NonNull String mapId) {
        this.mapId = mapId;
    }

    @Override
    public void loadDetails() {
        mapModel.getMap(mapId)
                .subscribe(map -> {
                    getViewState().showDetails(Objects.requireNonNull(map));
                    this.map = map;
                    getViewState().showUsers(mapModel.getUsers(map)
                                                     .doOnNext(users::add));
                });
    }

    @Override
    public void showAddUser() {
        getViewState().openNewUser(users);
    }

    @Override
    public void userAdded(@NonNull String userEmail) {
        userModel.getUser(userEmail)
                 .subscribe(user -> {
                     mapModel.addUser(user, Objects.requireNonNull(map));
                 });
    }

    @Override
    public void leave() {
        userModel.getSelf()
                 .subscribe(user -> mapModel.leaveMap(user, Objects.requireNonNull(map)));

        getViewState().closeMap();
    }
}
