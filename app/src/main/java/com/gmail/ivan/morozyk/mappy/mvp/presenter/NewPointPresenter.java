package com.gmail.ivan.morozyk.mappy.mvp.presenter;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewPointContract;

import androidx.annotation.NonNull;

public class NewPointPresenter extends BasePresenter<NewPointContract.View>
        implements NewPointContract.Presenter {

    @Override
    public void savePoint(@NonNull Point point) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void addPhoto() {

    }
}
