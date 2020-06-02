package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import androidx.annotation.Nullable;
import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface BaseContract {

    @StateStrategyType(AddToEndStrategy.class)
    interface View extends MvpView {

        void onError(@Nullable Throwable error);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void back();
    }

    interface Router {

        void back();
    }
}
