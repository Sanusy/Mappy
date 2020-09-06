package com.gmail.ivan.morozyk.mappy.ui.holder;

import android.view.View;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.databinding.ItemMemberMessageBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;

import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class MemberMessageHolder extends BaseViewHolder<Message, ItemMemberMessageBinding> {

    @NonNull
    private final ChatPresenter presenter;

    @Nullable
    private Message message;

    public MemberMessageHolder(@NonNull ItemMemberMessageBinding binding,
                               @NonNull ChatPresenter presenter) {
        super(binding);

        this.presenter = presenter;

        getBinding().memberMessagePointButton.setOnClickListener(view -> {
            message.getText();
            presenter.getViewState();
            // TODO: 9/4/2020 open point on map
        });
    }

    @Override
    public void bind(@NonNull Message entity) {
        message = entity;

        getBinding().memberNicknameText.setText(entity.getSenderName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        getBinding().memberSendTimeText.setText(dateFormat.format(entity.getDate()));

        if (entity.getText() != null) {
            getBinding().memberMessageText.setVisibility(View.VISIBLE);
            getBinding().memberMessagePointButton.setVisibility(View.GONE);

            getBinding().memberMessageText.setText(entity.getText());
        } else if (entity.getPoint() != null) {
            getBinding().memberMessageText.setVisibility(View.GONE);
            getBinding().memberMessagePointButton.setVisibility(View.VISIBLE);

            getBinding().memberMessagePointButton.setText(entity.getPoint()
                                                                .getTitle());
        }
    }
}
