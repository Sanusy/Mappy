package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityEditPointBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.EditPointContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.EditPointPresenter;
import com.gmail.ivan.morozyk.mappy.ui.adapter.PhotoRecyclerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import io.reactivex.rxjava3.core.Flowable;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class EditPointActivity extends BaseActivity<ActivityEditPointBinding>
        implements EditPointContract.View {

    private static final String POINT_ID = "point_id";

    private static final String MAP_ID = "map_id";

    private static final String TAG = EditPointActivity.class.getSimpleName();

    private static final int REQUEST_PHOTO_CAPTURE = 0;

    private Uri newPhotoUri;

    @NonNull
    private final PhotoRecyclerAdapter recyclerAdapter = new PhotoRecyclerAdapter();

    @InjectPresenter
    EditPointPresenter presenter;

    @ProvidePresenter
    EditPointPresenter providePresenter() {
        String pointId = Objects.requireNonNull(getIntent().getStringExtra(POINT_ID));
        String mapId = Objects.requireNonNull(getIntent().getStringExtra(MAP_ID));
        return new EditPointPresenter(pointId, mapId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().editPointRecycler.setAdapter(recyclerAdapter);

        getBinding().editPointCancelButton.setOnClickListener(view -> presenter.cancel());
        getBinding().editPointSaveButton.setOnClickListener(view -> {
            String title = Objects.requireNonNull(getBinding().editPointTitleEditText.getEditText())
                                  .getText()
                                  .toString();

            String description =
                    Objects.requireNonNull(getBinding().editPointDescriptionEditText.getEditText())
                           .getText()
                           .toString();

            presenter.save(title, description);
        });

        getBinding().editPointAddPhotoButton.setOnClickListener(view -> {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (captureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e(TAG, "onCreate: ", ex);
                }
                if (photoFile != null) {
                    newPhotoUri = FileProvider.getUriForFile(this,
                                                             "com.gmail.ivan.morozyk.mappy",
                                                             photoFile);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, newPhotoUri);
                    startActivityForResult(captureIntent, REQUEST_PHOTO_CAPTURE);
                }
            }
        });

        getMvpDelegate().onAttach();
        presenter.loadPhotos();
    }

    @NonNull
    @Override
    protected ActivityEditPointBinding inflateBinding() {
        return ActivityEditPointBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.edit_point_toolbar_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_point_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_point) {
            presenter.delete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDetails(@NonNull Point point) {
        Objects.requireNonNull(getBinding().editPointTitleEditText.getEditText())
               .setText(point.getTitle());

        Objects.requireNonNull(getBinding().editPointDescriptionEditText.getEditText())
               .setText(point.getDescription());
    }

    @Override
    public void showPhotos(@NonNull Flowable<String> photos) {
        recyclerAdapter.observeAdd(photos);
    }

    @Override
    public void invalidTitle() {
        getBinding().editPointTitleEditText.setError(getString(R.string.edit_point_title_error_string));
    }

    @Override
    public void invalidDescription() {
        getBinding().editPointDescriptionEditText.setError(getString(R.string.edit_point_description_error_string));
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void showProgress() {
        getBinding().editPointProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        getBinding().editPointProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_PHOTO_CAPTURE == requestCode && resultCode == RESULT_OK) {
            presenter.addPhoto(newPhotoUri);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir);

        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static Intent newIntent(@NonNull Context context,
                                   @NonNull String pointId,
                                   @NonNull String mapId) {
        Intent intent = new Intent(context, EditPointActivity.class);

        intent.putExtra(POINT_ID, pointId);
        intent.putExtra(MAP_ID, mapId);

        return intent;
    }
}
