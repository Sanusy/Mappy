package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapPagerContract;

public class MapPagerPresenter extends BasePresenter<MapPagerContract.View>
        implements MapPagerContract.Presenter {

    @Override
    public void attachView(MapPagerContract.View view) {
        super.attachView(view);

        getViewState().hideProgress();
    }

    @Override
    public void showMapDetails() {
        getViewState().openMapDetails();
    }

    @Override
    public void showMap() {
        getViewState().openMap();
    }

    @Override
    public void showChat() {
        getViewState().openChat();
    }
}
