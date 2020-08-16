package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.databinding.ItemUserListBinding;
import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.UserListHolder;

import androidx.annotation.NonNull;

public class UserListAdapter extends BaseRecyclerAdapter<User, ItemUserListBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.item_user_list;
    }

    @Override
    public void remove(@NonNull String entityId) {
        User user;
        for (int i = 0; i < entityList.size(); i++) {
            user = entityList.get(i);
            if (user.getId()
                    .equals(entityId)) {
                entityList.remove(user);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void edit(@NonNull User entity) {
        //none
    }

    @Override
    public BaseViewHolder<User, ItemUserListBinding> createViewHolder(@NonNull View view) {
        return new UserListHolder(ItemUserListBinding.bind(view));
    }
}
