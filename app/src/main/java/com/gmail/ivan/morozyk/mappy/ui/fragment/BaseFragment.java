package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.BasePresenter;
import com.gmail.ivan.morozyk.mappy.ui.activity.BaseActivity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import moxy.MvpAppCompatFragment;

public abstract class BaseFragment<B extends ViewBinding>
        extends MvpAppCompatFragment
        implements BaseContract.View {

    protected abstract B inflateBinding(@NonNull LayoutInflater inflater,
                                        @Nullable ViewGroup container,
                                        boolean attachToRoot);

    @Nullable
    private B binding;

    @NonNull
    public B getBinding() {
        return Objects.requireNonNull(binding);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = inflateBinding(inflater, container, false);

        return getBinding().getRoot();
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
