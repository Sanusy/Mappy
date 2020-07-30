package com.gmail.ivan.morozyk.mappy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.databinding.ActivityNewPointBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.NewPointContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.NewPointPresenter;
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

public class NewPointActivity extends BaseActivity<NewPointPresenter, ActivityNewPointBinding>
        implements NewPointContract.View {

    private static final String TAG = NewPointActivity.class.getSimpleName();

    private static final String MAP_ID = "map_id";

    private static final String LATITUDE = "lat";

    private static final String LONGITUDE = "lon";

    private static final int REQUEST_PHOTO_CAPTURE = 0;

    private Uri newPhotoUri;

    @NonNull
    private final PhotoRecyclerAdapter recyclerAdapter = new PhotoRecyclerAdapter();

    @InjectPresenter
    NewPointPresenter presenter;

    @ProvidePresenter
    NewPointPresenter providePresenter() {
        return new NewPointPresenter(Objects.requireNonNull(getIntent().getStringExtra(MAP_ID)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().newPointPhotoRecycler.setAdapter(recyclerAdapter);

        getBinding().newPointCancelButton.setOnClickListener(view -> presenter.cancel());

        getBinding().newPointAddButton.setOnClickListener(view -> {
            String title = Objects.requireNonNull(getBinding().newPointTitleEditText.getEditText())
                                  .getText()
                                  .toString();

            String description =
                    Objects.requireNonNull(getBinding().newPointDesctiptionEditText.getEditText())
                           .getText()
                           .toString();

            double latitude = getIntent().getDoubleExtra(LATITUDE, 0);
            double longitude = getIntent().getDoubleExtra(LONGITUDE, 0);

            presenter.savePoint(new Point(title, description, latitude, longitude));
        });

        getBinding().newPointAddPhotoButton.setOnClickListener(view -> {
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
    }

    @Override
    public void showPhotos(@NonNull Flowable<String> photos) {
        recyclerAdapter.observeAdd(photos);
    }

    @Override
    public void invalidTitle() {
        getBinding().newPointTitleEditText.setError(getString(R.string.new_point_title_error_string));
    }

    @Override
    public void invalidDescription() {
        getBinding().newPointDesctiptionEditText.setError(getString(R.string.new_point_description_error_string));
    }

    @Override
    public void back() {
        finish();
    }

    @NonNull
    @Override
    protected ActivityNewPointBinding inflateBinding() {
        return ActivityNewPointBinding.inflate(getLayoutInflater());
    }

    @Override
    public String getToolBarTitle() {
        return getString(R.string.new_point_toolbar_title);
    }

    @Override
    public void showProgress() {
        getBinding().newPointProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
        getBinding().newPointProgressBar.setVisibility(View.GONE);
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

    @NonNull
    public static Intent newIntent(@NonNull Context context,
                                   @NonNull String mapId,
                                   double latitude,
                                   double longitude) {
        Intent intent = new Intent(context, NewPointActivity.class);

        intent.putExtra(MAP_ID, mapId);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);

        return intent;
    }
}
