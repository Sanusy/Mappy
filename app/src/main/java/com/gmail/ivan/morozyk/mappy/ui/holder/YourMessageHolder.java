package com.gmail.ivan.morozyk.mappy.ui.holder;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.databinding.ItemYourMessageBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;

import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class YourMessageHolder extends BaseViewHolder<Message, ItemYourMessageBinding> {

    @NonNull
    private final ChatPresenter presenter;

    @Nullable
    private Message message;

    public YourMessageHolder(@NonNull ItemYourMessageBinding binding, @NonNull ChatPresenter presenter) {
        super(binding);

        this.presenter = presenter;

        getBinding().yourMessagePointButton.setOnClickListener(view -> {
            message.getText();
            presenter.getViewState();
            // TODO: 9/4/2020 open point on map
        });
    }

    @Override
    public void bind(@NonNull Message entity) {
        message = entity;

        getBinding().yourNicknameText.setText(entity.getSenderName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        getBinding().yourSendTimeText.setText(dateFormat.format(entity.getDate()));
        if (entity.getText() != null) {
            getBinding().yourMessageText.setVisibility(View.VISIBLE);
            getBinding().yourMessagePointButton.setVisibility(View.GONE);

            getBinding().yourMessageText.setText(entity.getText());
        } else if (entity.getPoint() != null) {
            getBinding().yourMessageText.setVisibility(View.GONE);
            getBinding().yourMessagePointButton.setVisibility(View.VISIBLE);

            getBinding().yourMessagePointButton.setText(entity.getPoint()
                                                                .getTitle());
        }
    }
}
