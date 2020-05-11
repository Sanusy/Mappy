package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;

import androidx.annotation.NonNull;
import moxy.MvpPresenter;

public abstract class BasePresenter<V extends BaseContract.View, R extends BaseContract.Router>
        extends MvpPresenter<V> implements BaseContract.Presenter {

    @NonNull
    private final R router;

    public BasePresenter(@NonNull R router) {
        this.router = router;
    }

    @NonNull
    public R getRouter() {
        return router;
    }

    @Override
    public void back() {
        router.back();
    }
}
