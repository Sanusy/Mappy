package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.AddUserContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.AddUserPresenter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AddUserDialogFragment extends BaseDialogFragment
        implements AddUserContract.View {

    private static final String ADDED_USERS = "added_users";

    @InjectPresenter
    AddUserPresenter presenter;

    @NonNull
    private TextInputLayout emailTextField;

    @Nullable
    private AddUserDialogListener dialogListener;

    @ProvidePresenter
    AddUserPresenter providePresenter() {
        List<String> addedUsers = Objects.requireNonNull(getArguments())
                                         .getStringArrayList(ADDED_USERS);

        return new AddUserPresenter(Objects.requireNonNull(addedUsers));
    }

    @Override
    public void invalidEmail() {
        emailTextField.setError(getString(R.string.add_user_invalid_email));
    }

    @Override
    public void alreadyAdded() {
        emailTextField.setError(getString(R.string.add_user_already_added));
    }

    @Override
    public void success(@NonNull String email) {
        Objects.requireNonNull(dialogListener)
               .onPositiveButtonClicked(this, email);

        dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogListener = (AddUserDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater()
                                     .inflate(R.layout.dialog_add_user, null);

        emailTextField = view.findViewById(R.id.add_user_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_user_dialog_title)
               .setView(view)
               .setPositiveButton(android.R.string.ok, null)
               .setNegativeButton(android.R.string.cancel, (dialog, id) -> {});
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = Objects.requireNonNull((AlertDialog) getDialog());

        dialog.getButton(Dialog.BUTTON_POSITIVE)
              .setOnClickListener(view -> {
                  presenter.addUser(Objects.requireNonNull(emailTextField.getEditText())
                                           .getText()
                                           .toString());
              });
    }

    public static AddUserDialogFragment newInstance(List<User> users) {
        ArrayList<String> usersEmails = new ArrayList<>();
        for (User user : users) {
            usersEmails.add(user.getEmail());
        }
        Bundle args = new Bundle();
        args.putStringArrayList(ADDED_USERS, usersEmails);
        AddUserDialogFragment fragment = new AddUserDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface AddUserDialogListener {

        void onPositiveButtonClicked(@NonNull DialogFragment dialog,
                                     @NonNull String addedUserEmail);
    }
}
