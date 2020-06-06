package com.gmail.ivan.morozyk.mappy.ui.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder<E, B extends ViewBinding> extends RecyclerView.ViewHolder {

    @NonNull
    private final B binding;

    public BaseViewHolder(@NonNull B binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public abstract void bind(@NonNull E entity);

    @NonNull
    protected B getBinding() {
        return binding;
    }
}
