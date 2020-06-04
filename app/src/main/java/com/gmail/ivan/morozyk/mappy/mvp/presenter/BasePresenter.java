package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;

import moxy.MvpPresenter;

public abstract class BasePresenter<V extends BaseContract.View>
        extends MvpPresenter<V> implements BaseContract.Presenter {

    @Override
    public void back() {
    }
}
