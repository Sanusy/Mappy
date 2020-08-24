package com.gmail.ivan.morozyk.mappy.ui.holder;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.databinding.ItemMemberMessageBinding;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;

public class MemberMessageHolder extends BaseViewHolder<Message, ItemMemberMessageBinding> {

    public MemberMessageHolder(@NonNull ItemMemberMessageBinding binding) {
        super(binding);
    }

    @Override
    public void bind(@NonNull Message entity) {
        getBinding().memberNicknameText.setText(entity.getSenderName());
        getBinding().memberMessageText.setText(entity.getText());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        getBinding().memberSendTimeText.setText(dateFormat.format(entity.getDate()));
    }
}
