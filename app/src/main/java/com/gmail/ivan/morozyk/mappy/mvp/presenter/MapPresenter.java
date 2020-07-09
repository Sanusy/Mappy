package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapContract;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showMap();
    }

    @Override
    public void attachView(MapContract.View view) {
        super.attachView(view);

        getViewState().hideProgress();
    }
}
