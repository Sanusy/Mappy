package com.gmail.ivan.morozyk.mappy.ui.holder;

import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.databinding.ItemUserListBinding;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class UserListHolder extends BaseViewHolder<User, ItemUserListBinding> {

    @Nullable
    private User user;

    public UserListHolder(@NonNull ItemUserListBinding binding) {
        super(binding);
    }

    @Override
    public void bind(@NonNull User entity) {
        user = entity;

        getBinding().userNameText.setText(user.getName());
        getBinding().userEmailText.setText(user.getEmail());
    }
}
