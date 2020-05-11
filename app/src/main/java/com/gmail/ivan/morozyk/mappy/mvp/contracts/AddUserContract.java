package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import androidx.annotation.NonNull;

public interface AddUserContract {

    interface View extends BaseContract.View {

        void invalidEmail();

        void alreadyAdded();
    }

    interface Presenter extends BaseContract.Presenter {

        void addUser(@NonNull String email);
    }

    interface Router extends BaseContract.Router {

    }
}