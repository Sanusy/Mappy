package com.gmail.ivan.morozyk.mappy.ui.activity;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityNewPointBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewPointContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.NewPointPresenter;

import androidx.annotation.NonNull;

public class NewPointActivity extends BaseActivity<NewPointPresenter, ActivityNewPointBinding>
        implements NewPointContract.View {

    @Override
    public void showPhotos() {

    }

    @NonNull
    @Override
    protected ActivityNewPointBinding inflateBinding() {
        return ActivityNewPointBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.new_point_toolbar_title);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
