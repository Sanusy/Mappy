package com.gmail.ivan.morozyk.mappy.ui.holder;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.databinding.ItemMapListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapListPresenter;

import androidx.annotation.NonNull;

public class MapListHolder extends BaseViewHolder<Map, ItemMapListBinding>
        implements View.OnClickListener {

    @NonNull
    private final MapListPresenter presenter;

    @NonNull
    private Map map;

    public MapListHolder(@NonNull ItemMapListBinding binding,
                         @NonNull MapListPresenter presenter) {
        super(binding);
        this.presenter = presenter;
        binding.getRoot()
               .setOnClickListener(this);
    }

    @Override
    public void bind(@NonNull Map entity) {
        map = entity;

        getBinding().mapItemTitle.setText(map.getTitle());
        getBinding().mapItemDescription.setText(map.getDescription());
    }

    @Override
    public void onClick(View v) {
        presenter.openMap(map);
    }
}
