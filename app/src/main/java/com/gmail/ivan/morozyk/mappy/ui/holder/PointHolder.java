package com.gmail.ivan.morozyk.mappy.ui.holder;

import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.databinding.ItemPointListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PointHolder extends BaseViewHolder<Point, ItemPointListBinding> {

    @NonNull
    private final ChatPresenter presenter;

    @Nullable
    private Point point;

    public PointHolder(@NonNull ItemPointListBinding binding, @NonNull ChatPresenter presenter) {
        super(binding);

        this.presenter = presenter;

        itemView.setOnClickListener(view -> presenter.sendPointMessage(Objects.requireNonNull(point)));
    }

    @Override
    public void bind(@NonNull Point entity) {
        getBinding().sharePointTitleText.setText(entity.getTitle());

        point = entity;
    }
}
