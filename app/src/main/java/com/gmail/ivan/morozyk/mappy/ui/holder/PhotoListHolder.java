package com.gmail.ivan.morozyk.mappy.ui.holder;

import com.bumptech.glide.Glide;
import com.gmail.ivan.morozyk.mappy.databinding.ItemPhotoListBinding;

import androidx.annotation.NonNull;

public class PhotoListHolder extends BaseViewHolder<String, ItemPhotoListBinding> {

    public PhotoListHolder(@NonNull ItemPhotoListBinding binding) {
        super(binding);
    }

    @Override
    public void bind(@NonNull String entity) {
        Glide.with(itemView).load(entity).into(getBinding().itemRecyclerPhoto);
    }
}
