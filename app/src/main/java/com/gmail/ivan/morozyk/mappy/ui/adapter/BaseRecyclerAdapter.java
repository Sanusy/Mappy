package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerAdapter<E> extends RecyclerView.Adapter<BaseViewHolder<E>> {

    @NonNull
    private final List<E> entityList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder<E> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(getLayoutId(), parent, false);

        return createViewHolder(view);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public void setEntityList(Collection<E> entityList) {
        this.entityList.clear();
        this.entityList.addAll(entityList);
        notifyDataSetChanged();
    }

    public abstract BaseViewHolder<E> createViewHolder(@NonNull View view);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<E> holder, int position) {
        holder.bind(entityList.get(position));
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }
}
