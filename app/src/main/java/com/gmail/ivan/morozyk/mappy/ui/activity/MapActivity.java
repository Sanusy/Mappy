package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityMapBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.MapPagerAdapter;

import java.util.Objects;

import androidx.annotation.NonNull;
import moxy.presenter.InjectPresenter;

public class MapActivity
        extends BaseActivity<MapPresenter, ActivityMapBinding>
        implements MapContract.View {

    @NonNull
    private static final String MAP_ID = "map_id";

    @NonNull
    private static final String MAP_NAME = "map_name";

    @InjectPresenter
    MapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().mapViewPager.setAdapter(new MapPagerAdapter(this,
                                                                 Objects.requireNonNull(getIntent().getStringExtra(
                                                                         MAP_ID))));
    }

    @Override
    public void showMap() {
        getBinding().mapViewPager.setCurrentItem(1);
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
    public void showProgress() {
        getBinding().mapProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().mapProgressBar.setVisibility(View.GONE);
    }

    public static Intent newIntent(@NonNull Context context, @NonNull Map map) {
        Intent intent = new Intent(context, MapActivity.class);

        intent.putExtra(MAP_ID, map.getId());
        intent.putExtra(MAP_NAME, map.getTitle());

        return intent;
    }
}
