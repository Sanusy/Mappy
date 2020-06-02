package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.BasePresenter;
import com.gmail.ivan.morozyk.mappy.ui.activity.BaseActivity;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.ProvidePresenter;

public abstract class BaseFragment<P extends BasePresenter> extends MvpAppCompatFragment
        implements BaseContract.View {

    @ProvidePresenter
    protected abstract P providePresenter();

    @LayoutRes
    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onError(@Nullable Throwable error) {
        requireBaseActivity().onError(error);
    }

    @Override
    public void showProgress() {
        requireBaseActivity().showProgress();
    }

    @Override
    public void hideProgress() {
        requireBaseActivity().hideProgress();
    }

    @NonNull
    public BaseActivity<?> requireBaseActivity() {
        return (BaseActivity<?>) requireActivity();
    }
}
