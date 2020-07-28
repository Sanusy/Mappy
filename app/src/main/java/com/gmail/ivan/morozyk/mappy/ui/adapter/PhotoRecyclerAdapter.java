package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.databinding.ItemPhotoListBinding;
import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.PhotoListHolder;

import androidx.annotation.NonNull;

public class PhotoRecyclerAdapter extends BaseRecyclerAdapter<String, ItemPhotoListBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.item_photo_list;
    }

    @Override
    public BaseViewHolder<String, ItemPhotoListBinding> createViewHolder(@NonNull View view) {
        return new PhotoListHolder(ItemPhotoListBinding.bind(view));
    }

    @Override
    public void remove(@NonNull String entityId) {

    }

    @Override
    public void edit(@NonNull String entity) {

    }

    public void clear() {
        entityList.clear();
        notifyDataSetChanged();
    }
}
