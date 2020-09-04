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

        if (entity.getText() != null) {
            getBinding().yourTextMessageLayout.setVisibility(View.VISIBLE);
            getBinding().yourPointMessageLayout.setVisibility(View.GONE);
            getBinding().yourNicknameText.setText(entity.getSenderName());
            getBinding().yourMessageText.setText(entity.getText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            getBinding().yourSendTimeText.setText(dateFormat.format(entity.getDate()));
        } else if (entity.getPoint() != null) {
            getBinding().yourTextMessageLayout.setVisibility(View.GONE);
            getBinding().yourPointMessageLayout.setVisibility(View.VISIBLE);

            getBinding().yourMessagePointButton.setText(entity.getPoint()
                                                                .getTitle());
        }
    }
}
