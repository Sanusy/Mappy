package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMapModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewMapContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class NewMapPresenter extends BasePresenter<NewMapContract.View>
        implements NewMapContract.Presenter {

    @NonNull
    private final Subject<User> users = ReplaySubject.create();

    @NonNull
    private final List<User> usersToAdd = new ArrayList<>();

    @NonNull
    private final FirestoreMapModel mapModel = new FirestoreMapModel();

    @NonNull
    private final FirestoreUserModel userModel = new FirestoreUserModel();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        userModel.getSelf()
                 .subscribe(user -> {
                     usersToAdd.add(user);
                     users.onNext(user);
                 });
    }

    @Override
    public void loadUsers() {
        getViewState().hideProgress();
        getViewState().showUsers(users.toFlowable(BackpressureStrategy.BUFFER));
    }

    @Override
    public void createAndOpen(@NonNull Map map) {
        if (map.getTitle()
               .isEmpty()) {
            getViewState().emptyTitle();
        } else {
            mapModel.addMap(map, usersToAdd.get(0), usersToAdd.subList(1, usersToAdd.size()))
                    .subscribe(addedMap -> getViewState().openCreatedMap(addedMap));
        }
    }

    @Override
    public void cancel() {
        getViewState().cancel();
    }

    @Override
    public void addUser() {
        getViewState().openNewUser(usersToAdd);
    }

    @Override
    public void userAdded(@NonNull String userEmail) {
        userModel.getUser(userEmail)
                 .subscribe(user -> {
                     usersToAdd.add(user);
                     users.onNext(user);
                 });
    }
}
