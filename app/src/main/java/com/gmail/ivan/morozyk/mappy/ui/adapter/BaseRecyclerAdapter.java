package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import io.reactivex.rxjava3.core.Flowable;

public abstract class BaseRecyclerAdapter<E, B extends ViewBinding>
        extends RecyclerView.Adapter<BaseViewHolder<E, B>> {

    @NonNull
    protected final List<E> entityList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder<E, B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(getLayoutId(), parent, false);

        return createViewHolder(view);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public abstract BaseViewHolder<E, B> createViewHolder(@NonNull View view);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<E, B> holder, int position) {
        holder.bind(entityList.get(position));
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    public void observeAdd(Flowable<E> entitities) {
        entitities.subscribe(entity -> {
            entityList.add(entity);
            notifyDataSetChanged();
        });
    }

    public abstract void remove(@NonNull String entityId);

    public abstract void edit(@NonNull E entity);
}
