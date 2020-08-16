package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;
import com.gmail.ivan.morozyk.mappy.databinding.FragmentMapDetailsBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapDetailsContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapDetailsPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.UserListAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import io.reactivex.rxjava3.core.Flowable;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MapDetailsFragment extends BaseFragment<FragmentMapDetailsBinding>
        implements MapDetailsContract.View, AddUserDialogFragment.AddUserDialogListener {

    private static final String MAP_ID = "map_id";

    @Nullable
    private UserListAdapter adapter;

    @InjectPresenter
    MapDetailsPresenter presenter;

    @ProvidePresenter
    MapDetailsPresenter providePresenter() {
        return new MapDetailsPresenter(Objects.requireNonNull(Objects.requireNonNull(getArguments())
                                                                     .getString(MAP_ID)));
    }

    @Override
    protected FragmentMapDetailsBinding inflateBinding(@NonNull LayoutInflater inflater,
                                                       @Nullable ViewGroup container,
                                                       boolean attachToRoot) {
        return FragmentMapDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View createdView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(createdView, savedInstanceState);

        adapter = new UserListAdapter();
        getBinding().detailsMapUserRecycler.setAdapter(adapter);
        getMvpDelegate().onAttach();
        presenter.loadDetails();

        getBinding().mapDetailsAddUserButton.setOnClickListener(view -> presenter.showAddUser());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.leave_map_menu_item) {
            presenter.leave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUsers(@NonNull Flowable<User> users) {
        Objects.requireNonNull(adapter)
               .observeAdd(users);
    }

    @Override
    public void showDetails(@NonNull Map map) {
        getBinding().detailsMapTitleText.setText(map.getTitle());
        getBinding().detailsMapDescriptionText.setText(map.getDescription());
    }

    @Override
    public void openNewUser(@NonNull List<User> addedUsers) {
        DialogFragment addUserDialog = AddUserDialogFragment.newInstance(addedUsers);

        addUserDialog.show(getChildFragmentManager(), "add_user_dialog_fragment");
    }

    @Override
    public void closeMap() {
        requireBaseActivity().finish();
    }

    public static MapDetailsFragment newInstance(@NonNull String mapId) {
        Bundle args = new Bundle();
        args.putString(MAP_ID, mapId);
        MapDetailsFragment fragment = new MapDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPositiveButtonClicked(@NonNull DialogFragment dialog,
                                        @NonNull String addedUserEmail) {
        presenter.userAdded(addedUserEmail);
    }
}
