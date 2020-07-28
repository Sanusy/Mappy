package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapPagerContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        void openMapDetails();

        void openMap();

        void openChat();
    }

    interface Presenter extends BaseContract.Presenter {

        void showMapDetails();

        void showMap();

        void showChat();
    }
}
