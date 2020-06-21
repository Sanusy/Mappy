package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.databinding.ItemMapListBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapListPresenter;
import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.MapListHolder;

import java.util.Collections;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public class MapListAdapter extends BaseRecyclerAdapter<Map, ItemMapListBinding> {

    @NonNull
    private final MapListPresenter presenter;

    public MapListAdapter(@NonNull MapListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_map_list;
    }

    @Override
    public BaseViewHolder<Map, ItemMapListBinding> createViewHolder(@NonNull View view) {
        return new MapListHolder(ItemMapListBinding.bind(view), presenter);
    }

    @Override
    public void observeAdd(Flowable<Map> entitities) {
        super.observeAdd(entitities.doOnSubscribe(map -> presenter.emptyMap(entityList.isEmpty()))
                                   .doAfterNext(map -> {
                                       presenter.emptyMap(entityList.isEmpty());
                                       Collections.sort(entityList,
                                                        (map1, map2) -> map1.getTimeStamp()
                                                                            .compareTo(map2.getTimeStamp()));
                                   }));
    }

    @Override
    public void remove(@NonNull String entityId) {
        Map map;
        for (int i = 0; i < entityList.size(); i++) {
            map = entityList.get(i);
            if (map.getId()
                   .equals(entityId)) {
                entityList.remove(map);
                notifyDataSetChanged();
            }
        }

        if (entityList.isEmpty()) {
            presenter.emptyMap(true);
        }
    }

    @Override
    public void edit(@NonNull Map entity) {
        for (Map map : entityList) {
            if (map.equals(entity)) {
                map.setTitle(entity.getTitle());
                map.setDescription(entity.getDescription());
                notifyDataSetChanged();
            }
        }
    }
}
