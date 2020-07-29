package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Intent;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.databinding.ActivitySplashBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.SplashContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.SplashPresenter;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import moxy.presenter.InjectPresenter;

public class SplashActivity extends BaseActivity<ActivitySplashBinding>
        implements SplashContract.View {

    private static final int REQUEST_LOG_IN = 0;

    @InjectPresenter
    SplashPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkAuth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOG_IN && resultCode == RESULT_OK) {
            presenter.addSelf();
            presenter.checkAuth();
        }
    }

    @NonNull
    @Override
    protected ActivitySplashBinding inflateBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    public void openLogIn() {
        List<AuthUI.IdpConfig> signInMethods =
                Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent intent = AuthUI.getInstance()
                              .createSignInIntentBuilder()
                              .setAvailableProviders(signInMethods)
                              .build();

        startActivityForResult(intent, REQUEST_LOG_IN);
    }

    @Override
    public void openMapList() {
        startActivity(new Intent(this, MapListActivity.class));
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void showProgress() {
        getBinding().splashProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().splashProgressBar.setVisibility(View.GONE);
    }
}
