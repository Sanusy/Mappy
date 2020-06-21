package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.data.model.UserModel;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.AddUserContract;

import java.util.List;

import androidx.annotation.NonNull;

public class AddUserPresenter extends BasePresenter<AddUserContract.View>
        implements AddUserContract.Presenter {

    @NonNull
    private final List<String> addedUsers;

    @NonNull
    private final UserModel userModel = new FirestoreUserModel();

    public AddUserPresenter(@NonNull List<String> addedUsers) {
        this.addedUsers = addedUsers;
    }

    @Override
    public void addUser(@NonNull String email) {
        email = email.trim().toLowerCase();
        if (addedUsers.contains(email)) {
            getViewState().alreadyAdded();
        } else {
            userModel.getUser(email)
                     .subscribe(user -> getViewState().success(user.getEmail()),
                                error -> getViewState().invalidEmail());
        }
    }
}
