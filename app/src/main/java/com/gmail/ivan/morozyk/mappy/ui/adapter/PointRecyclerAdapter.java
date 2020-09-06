package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.databinding.ItemPointListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;
import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.PointHolder;

import androidx.annotation.NonNull;

public class PointRecyclerAdapter extends BaseRecyclerAdapter<Point, ItemPointListBinding> {

    @NonNull
    private final ChatPresenter presenter;

    public PointRecyclerAdapter(@NonNull ChatPresenter presenter) {this.presenter = presenter;}

    @Override
    protected int getLayoutId() {
        return R.layout.item_point_list;
    }

    @Override
    public BaseViewHolder<Point, ItemPointListBinding> createViewHolder(@NonNull View view) {
        return new PointHolder(ItemPointListBinding.bind(view), presenter);
    }

    @Override
    public void remove(@NonNull String entityId) {
        Point point;
        for (int i = 0; i < entityList.size(); i++) {
            point = entityList.get(i);
            if (point.getId()
                     .equals(entityId)) {
                entityList.remove(point);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void edit(@NonNull Point entity) {
        for (Point point : entityList) {
            if (point.equals(entity)) {
                point.setTitle(entity.getTitle());
                point.setDescription(entity.getDescription());
                notifyDataSetChanged();
            }
        }
    }
}
