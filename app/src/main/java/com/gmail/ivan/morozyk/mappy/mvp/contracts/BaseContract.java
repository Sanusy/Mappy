package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import androidx.annotation.Nullable;
import moxy.MvpView;

public interface BaseContract {

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
