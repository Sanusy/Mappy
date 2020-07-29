package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.gmail.ivan.morozyk.mappy.mvp.contracts.BaseContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.BasePresenter;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import moxy.MvpAppCompatActivity;

public abstract class BaseActivity<B extends ViewBinding>
        extends MvpAppCompatActivity
        implements BaseContract.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Nullable
    private B binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateBinding();
        setContentView(getBinding().getRoot());
        setTitle(getToolBarTitle());
    }

    @NonNull
    public B getBinding() {
        return Objects.requireNonNull(binding);
    }

    @NonNull
    protected abstract B inflateBinding();

    public abstract String getToolBarTitle();

    @Override
    public void onError(@Nullable Throwable error) {
        Log.e(TAG, "onError: ", error);
    }
}
