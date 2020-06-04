package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.SplashContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;

public class SplashPresenter extends BasePresenter<SplashContract.View>
        implements SplashContract.Presenter {

    @NonNull
    private final FirestoreUserModel userModel = new FirestoreUserModel();

    @NonNull
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public void checkAuth() {
        userModel.getSelf()
                 .subscribe(user -> {
                     getViewState().hideProgress();
                     getViewState().openMapList();
                 }, error -> getViewState().openLogIn());
    }

    @Override
    public void addSelf() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        User user = new User(Objects.requireNonNull(Objects.requireNonNull(firebaseUser)
                                                           .getEmail()),
                             Objects.requireNonNull(firebaseUser.getDisplayName()));
        userModel.addUser(user);
    }
}
