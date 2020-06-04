package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface SplashContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        void openLogIn();

        void openMapList();
    }

    interface Presenter extends BaseContract.Presenter {

        void checkAuth();

        void addSelf();
    }

    interface Router extends BaseContract.Router {

    }
}
