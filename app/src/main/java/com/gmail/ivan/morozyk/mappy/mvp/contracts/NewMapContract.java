package com.gmail.ivan.morozyk.mappy.mvp.contracts;

public interface NewMapContract {

    interface View extends BaseContract.View {

        void showUsers();
    }

    interface Presenter extends BaseContract.Presenter {

        void createAndOpen();

        void cancel();

        void addUser();
    }

    interface Router extends BaseContract.Router {

        void openCreatedMap();

        void cancel();

        void openNewUser();
    }
}
