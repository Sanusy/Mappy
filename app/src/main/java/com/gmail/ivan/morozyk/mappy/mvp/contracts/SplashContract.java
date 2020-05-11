package com.gmail.ivan.morozyk.mappy.mvp.contracts;

public interface SplashContract {

    interface View extends BaseContract.View {

    }

    interface Presenter {

        void checkAuth();
    }

    interface Router {

        void openLogIn();

        void openMapList();
    }
}
