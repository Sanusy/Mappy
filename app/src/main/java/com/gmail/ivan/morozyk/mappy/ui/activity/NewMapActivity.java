package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityNewMapBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewMapContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.NewMapPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.UserListAdapter;
import com.gmail.ivan.morozyk.mappy.ui.fragment.AddUserDialogFragment;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.rxjava3.core.Flowable;
import moxy.presenter.InjectPresenter;

public class NewMapActivity extends BaseActivity<NewMapPresenter, ActivityNewMapBinding>
        implements NewMapContract.View, AddUserDialogFragment.AddUserDialogListener {

    @InjectPresenter
    NewMapPresenter presenter;

    @Nullable
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().cancelNewMapBtn.setOnClickListener(view -> presenter.cancel());

        getBinding().newMapAddUserBtn.setOnClickListener(view -> presenter.addUser());

        getBinding().addNewMapBtn.setOnClickListener(view -> {
            Map map = new Map(Objects.requireNonNull(getBinding().newMapTitleEditText.getEditText())
                                     .getText()
                                     .toString(),
                              getBinding().newMapDescriptionEditText.getText()
                                                                    .toString());

            presenter.createAndOpen(map);
        });

        adapter = new UserListAdapter();
        getBinding().newMapUserRecycler.setAdapter(adapter);
        getBinding().newMapUserRecycler.setLayoutManager(new LinearLayoutManager(this));
        getMvpDelegate().onAttach();
        presenter.loadUsers();
    }

    @Override
    public void showUsers(@NonNull Flowable<User> users) {
        Objects.requireNonNull(adapter)
               .observeAdd(users);
    }

    @Override
    public void openCreatedMap(@NonNull Map addedMap) {
        startActivity(MapPagerActivity.newIntent(this, addedMap));
        finish();
    }

    @Override
    public void cancel() {
        onBackPressed();
    }

    @Override
    public void emptyTitle() {
        getBinding().newMapTitleEditText.setError(getString(R.string.empty_title_error_new_map));
    }

    @Override
    public void openNewUser(@NonNull List<User> addedUsers) {
        AddUserDialogFragment.newInstance(addedUsers)
                             .show(getSupportFragmentManager(), "add_user_dialog_fragment");
    }

    @Override
    public void onPositiveButtonClicked(@NonNull DialogFragment dialog,
                                        @NonNull String addedUserEmail) {

        presenter.userAdded(addedUserEmail);
    }

    @NonNull
    @Override
    protected ActivityNewMapBinding inflateBinding() {
        return ActivityNewMapBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.new_map_action_bar_title);
    }

    @Override
    public void showProgress() {
        getBinding().newMapProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().newMapProgressBar.setVisibility(View.GONE);
    }
}
