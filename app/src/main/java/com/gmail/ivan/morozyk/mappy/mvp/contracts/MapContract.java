package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        void showMap();
    }

    interface Presenter extends BaseContract.Presenter {

    }
}
