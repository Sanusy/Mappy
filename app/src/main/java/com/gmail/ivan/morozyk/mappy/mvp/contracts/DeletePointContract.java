package com.gmail.ivan.morozyk.mappy.mvp.contracts;

public interface DeletePointContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        void deletePoint(int pointId);
    }

    interface Router extends BaseContract.Router {

    }
}
