package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityMapBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapPagerContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapPagerPresenter;
import com.gmail.ivan.morozyk.mappy.ui.fragment.MapScreenFragment;
import com.gmail.ivan.morozyk.mappy.ui.fragment.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import moxy.presenter.InjectPresenter;

public class MapPagerActivity
        extends BaseActivity<ActivityMapBinding>
        implements MapPagerContract.View {

    @NonNull
    private static final String MAP_ID = "map_id";

    @NonNull
    private static final String MAP_NAME = "map_name";

    @NonNull
    private static final String SELECTED_TAB = "selected_tab";

    @InjectPresenter
    MapPagerPresenter presenter;

    public static Intent newIntent(@NonNull Context context, @NonNull Map map) {
        Intent intent = new Intent(context, MapPagerActivity.class);

        intent.putExtra(MAP_ID, map.getId());
        intent.putExtra(MAP_NAME, map.getTitle());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationItemSelected());

        getBinding().bottomNavigationView.setSelectedItemId(savedInstanceState != null
                                                            ? savedInstanceState.getInt(SELECTED_TAB)
                                                            : R.id.map_screen_tab);
    }

    @NonNull
    @Override
    protected ActivityMapBinding inflateBinding() {
        return ActivityMapBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getIntent().getStringExtra(MAP_NAME);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_TAB, getBinding().bottomNavigationView.getSelectedItemId());
    }

    @Override
    public void openMapDetails() {
        navigate(TestFragment.newInstance("asd", "asd"));
    }

    @Override
    public void openMap() {
        navigate(MapScreenFragment.newInstance(Objects.requireNonNull(getIntent().getStringExtra(
                MAP_ID))));
    }

    @Override
    public void openChat() {
        navigate(TestFragment.newInstance("GGG", "WWW"));
    }

    private void navigate(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }

    @Override
    public void showProgress() {
        getBinding().mapProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().mapProgressBar.setVisibility(View.GONE);
    }

    private class BottomNavigationItemSelected
            implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.map_details_tab:
                    presenter.showMapDetails();
                    return true;
                case R.id.map_screen_tab:
                    presenter.showMap();
                    return true;
                case R.id.chat_tab:
                    presenter.showChat();
                    return true;
                default:
                    return false;
            }
        }
    }
}
