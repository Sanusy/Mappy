package com.gmail.ivan.morozyk.mappy.ui.fragment;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.BasePresenter;
import com.gmail.ivan.morozyk.mappy.ui.activity.BaseActivity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import moxy.MvpAppCompatDialogFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public abstract class BaseDialogFragment<P extends BasePresenter> extends MvpAppCompatDialogFragment
        implements BaseContract.View {

    @Nullable
    @InjectPresenter
    private P presenter;

    @ProvidePresenter
    public abstract P providePresenter();

    @NonNull
    public P getPresenter() {
        return Objects.requireNonNull(presenter);
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

    public BaseActivity requireBaseActivity() {
        return (BaseActivity) requireActivity();
    }
}
