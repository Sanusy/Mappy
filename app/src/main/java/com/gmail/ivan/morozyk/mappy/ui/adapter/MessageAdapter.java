package com.gmail.ivan.morozyk.mappy.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Message;
import com.gmail.ivan.morozyk.mappy.databinding.ItemMemberMessageBinding;
import com.gmail.ivan.morozyk.mappy.databinding.ItemYourMessageBinding;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.ChatPresenter;
import com.gmail.ivan.morozyk.mappy.ui.holder.BaseViewHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.MemberMessageHolder;
import com.gmail.ivan.morozyk.mappy.ui.holder.YourMessageHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import io.reactivex.rxjava3.core.Flowable;

public class MessageAdapter
        extends RecyclerView.Adapter<BaseViewHolder<Message, ? extends ViewBinding>> {

    @NonNull
    private final List<Message> messages = new ArrayList<>();

    @NonNull
    private final ChatPresenter presenter;

    public MessageAdapter(@NonNull ChatPresenter presenter) {this.presenter = presenter;}

    @NonNull
    @Override
    public BaseViewHolder<Message, ? extends ViewBinding> onCreateViewHolder(@NonNull ViewGroup parent,
                                                                             @Message.MessageOwn int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == Message.MessageOwn.YOU) {
            View yourMessage = inflater.inflate(R.layout.item_your_message, parent, false);

            return new YourMessageHolder(ItemYourMessageBinding.bind(yourMessage), presenter);
        } else {
            View memberMessage = inflater.inflate(R.layout.item_member_message, parent, false);

            return new MemberMessageHolder(ItemMemberMessageBinding.bind(memberMessage), presenter);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Message, ? extends ViewBinding> holder,
                                 int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Message.MessageOwn
    @Override
    public int getItemViewType(int position) {
        return messages.get(position)
                       .getMessageOwner();
    }

    public void observeAdd(Flowable<Message> messages) {
        messages.subscribe(entity -> {
            this.messages.add(entity);
            notifyDataSetChanged();
        });
    }
}
