package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMapModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityMapListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapListContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapListPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.MapListAdapter;

import java.util.Collections;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.rxjava3.core.Flowable;
import moxy.presenter.InjectPresenter;

public class MapListActivity extends BaseActivity<MapListPresenter, ActivityMapListBinding>
        implements MapListContract.View {

    @InjectPresenter
    MapListPresenter presenter;

    @Nullable
    private MapListAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().addMapFab.setOnClickListener(view -> {
            presenter.newMap();
        });

        recyclerAdapter = new MapListAdapter(presenter);
        getBinding().mapRecycler.setAdapter(recyclerAdapter);
        getBinding().mapRecycler.setLayoutManager(new LinearLayoutManager(this));

        getMvpDelegate().onAttach();
        presenter.loadMaps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out_item) {
            presenter.logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMaps(@NonNull Flowable<Map> maps) {
        Objects.requireNonNull(recyclerAdapter)
               .observeAdd(maps);
    }

    @Override
    public void showEmpty(boolean empty) {
        getBinding().emptyMapListText.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void removeMap(@NonNull String mapId) {
        Objects.requireNonNull(recyclerAdapter)
               .remove(mapId);
    }

    @Override
    public void editMap(@NonNull Map map) {
        Objects.requireNonNull(recyclerAdapter)
               .edit(map);
    }

    @Override
    public void openNewMap() {
        startActivity(new Intent(this, NewMapActivity.class));
    }

    @Override
    public void openMap(@NonNull Map map) {
        startActivity(MapActivity.newIntent(this, map));
    }

    @Override
    public void logOut() {
        startActivity(new Intent(this, SplashActivity.class));
    }

    @NonNull
    @Override
    protected ActivityMapListBinding inflateBinding() {
        return ActivityMapListBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void showProgress() {
        getBinding().mapListProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().mapListProgressBar.setVisibility(View.GONE);
    }
}
