package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.databinding.FragmentChatBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapChatContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.MessageAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ChatFragment extends BaseFragment<FragmentChatBinding>
        implements MapChatContract.View {

    private static final String MAP_ID = "map_id";

    @InjectPresenter
    ChatPresenter presenter;

    @Nullable
    private MessageAdapter messageAdapter;

    @ProvidePresenter
    ChatPresenter providePresenter() {
        return new ChatPresenter(Objects.requireNonNull(Objects.requireNonNull(getArguments())
                                                               .getString(MAP_ID)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messageAdapter = new MessageAdapter();
        getBinding().chatRecycler.setAdapter(messageAdapter);
        getMvpDelegate().onAttach();
        presenter.loadMessages();

        getBinding().chatSendMessageButton.setOnClickListener(v -> {
            String message = getBinding().messageEditText.getText().toString();

            if(!TextUtils.isEmpty(message)) {
                presenter.sendTextMessage(message);
            }
        });

        getBinding().chatSendPhotoButton.setOnClickListener(v -> presenter.loadPhotos());
        getBinding().chatSendPointButton.setOnClickListener(v -> presenter.loadPoints());
    }

    @Override
    public void showMessages(@NonNull Flowable<Message> messageList) {
        Objects.requireNonNull(messageAdapter)
               .observeAdd(messageList);
    }

    @Override
    public void showPoints(@NonNull List<Point> pointList) {

    }

    @Override
    public void showPhotos() {

    }

    @Override
    public void showDeleteMode() {

    }

    @Override
    public void showChatMode() {

    }

    @Override
    public void clearMessage() {
        getBinding().messageEditText.getText().clear();
    }

    @Override
    protected FragmentChatBinding inflateBinding(@NonNull LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 boolean attachToRoot) {
        return FragmentChatBinding.inflate(inflater, container, false);
    }

    public static ChatFragment newInstance(@NonNull String mapId) {
        Bundle args = new Bundle();
        args.putString(MAP_ID, mapId);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
