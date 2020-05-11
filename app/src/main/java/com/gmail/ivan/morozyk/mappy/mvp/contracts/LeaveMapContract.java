package com.gmail.ivan.morozyk.mappy.mvp.contracts;

public interface LeaveMapContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        void leaveMap(int mapId);
    }

    interface Router extends BaseContract.Router {

    }
}
