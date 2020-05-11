package com.gmail.ivan.morozyk.mappy.ui.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public abstract class BasePagerAdapter<E> extends FragmentStateAdapter {

    @NonNull
    private final List<E> entityList = new ArrayList<>();

    public BasePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setEntityList(@NonNull Collection<E> entityList) {
        this.entityList.clear();
        this.entityList.addAll(entityList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }
}
