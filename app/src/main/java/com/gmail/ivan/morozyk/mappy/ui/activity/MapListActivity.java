package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreMapModel;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestoreUserModel;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityMapListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapListContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapListPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.MapListAdapter;

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
    MapListAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().addMapFab.setOnClickListener(view -> {
            presenter.newMap();

            // TODO: 6/5/2020 temp fab action. Will be deleted in another task
            Map map = new Map("Test Map", "Test Description");

            FirestoreUserModel userModel = new FirestoreUserModel();
            userModel.getSelf()
                     .subscribe(user -> new FirestoreMapModel().addMap(
                             map,
                             user));
        });

        recyclerAdapter = new MapListAdapter(presenter);
        getBinding().mapRecycler.setAdapter(recyclerAdapter);
        getBinding().mapRecycler.setLayoutManager(new LinearLayoutManager(this));

        getMvpDelegate().onAttach();
        presenter.loadMaps();
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
        // TODO: 6/5/2020 opens NewMapActivity
    }

    @Override
    public void openMap(@NonNull Map map) {
        // TODO: 6/5/2020 opens MapFragment
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
