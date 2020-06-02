package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.BasePresenter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import moxy.MvpAppCompatActivity;
import moxy.presenter.ProvidePresenter;

public abstract class BaseActivity<P extends BasePresenter> extends MvpAppCompatActivity
        implements BaseContract.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @ProvidePresenter
    protected abstract P providePresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setTitle(getToolBarTitle());
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    public abstract String getToolBarTitle();

    @Override
    public void onError(@Nullable Throwable error) {
        Log.e(TAG, "onError: ", error);
    }
}
