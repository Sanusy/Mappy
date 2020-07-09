package com.gmail.ivan.morozyk.mappy.ui.adapter;

import com.gmail.ivan.morozyk.mappy.ui.fragment.BaseFragment;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public abstract class BasePagerAdapter extends FragmentStateAdapter {

    @NonNull
    public List<Fragment> getFragmentList() {
        return Objects.requireNonNull(fragmentList);
    }

    @Nullable
    private final List<Fragment> fragmentList;

    public BasePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        fragmentList = setFragmentList();
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(fragmentList)
                      .size();
    }

    public abstract List<Fragment> setFragmentList();
}
