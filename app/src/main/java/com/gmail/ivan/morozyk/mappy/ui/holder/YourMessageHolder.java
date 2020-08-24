package com.gmail.ivan.morozyk.mappy.ui.holder;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.databinding.ItemYourMessageBinding;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;

public class YourMessageHolder extends BaseViewHolder<Message, ItemYourMessageBinding> {

    public YourMessageHolder(@NonNull ItemYourMessageBinding binding) {
        super(binding);
    }

    @Override
    public void bind(@NonNull Message entity) {
        getBinding().yourNicknameText.setText(entity.getSenderName());
        getBinding().yourMessageText.setText(entity.getText());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        getBinding().yourSendTimeText.setText(dateFormat.format(entity.getDate()));
    }
}
