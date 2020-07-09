package com.gmail.ivan.morozyk.mappy.ui.adapter;

import com.gmail.ivan.morozyk.mappy.ui.fragment.BaseFragment;
import com.gmail.ivan.morozyk.mappy.ui.fragment.TestFragment;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MapPagerAdapter extends BasePagerAdapter {

    @NonNull
    private final String mapId;

    public MapPagerAdapter(@NonNull FragmentActivity fragmentActivity, @NonNull String mapId) {
        super(fragmentActivity);

        this.mapId = mapId;
    }

    @Override
    public List<Fragment> setFragmentList() {
        return Arrays.asList(TestFragment.newInstance("1", "1"),
                             TestFragment.newInstance("2", "2"),
                             TestFragment.newInstance("3", "3"));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getFragmentList().get(position);
    }
}
