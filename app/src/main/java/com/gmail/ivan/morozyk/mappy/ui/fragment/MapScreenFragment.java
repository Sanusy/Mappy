package com.gmail.ivan.morozyk.mappy.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.ivan.morozyk.mappy.R;
import com.gmail.ivan.morozyk.mappy.data.entity.Point;
import com.gmail.ivan.morozyk.mappy.data.firestore.FirestorePhotoModel;
import com.gmail.ivan.morozyk.mappy.databinding.FragmentMapScreenBinding;
import com.gmail.ivan.morozyk.mappy.mvp.contracts.MapScreenContract;
import com.gmail.ivan.morozyk.mappy.mvp.presenter.MapScreenPresenter;
import com.gmail.ivan.morozyk.mappy.ui.activity.EditPointActivity;
import com.gmail.ivan.morozyk.mappy.ui.activity.NewPointActivity;
import com.gmail.ivan.morozyk.mappy.ui.adapter.PhotoRecyclerAdapter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MapScreenFragment extends BaseFragment<FragmentMapScreenBinding>
        implements MapScreenContract.View {

    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private static final String MAP_ID = "map_id";

    private static final int REQUEST_LOCATION = 0;

    @NonNull
    private final Map<Point, Marker> points = new HashMap<>();

    @InjectPresenter
    MapScreenPresenter presenter;

    @Nullable
    private GoogleMap map;

    @Nullable
    private LatLng focusPoint;

    @Nullable
    private PhotoRecyclerAdapter photoRecyclerAdapter;

    @ProvidePresenter
    MapScreenPresenter providePresenter() {
        return new MapScreenPresenter(Objects.requireNonNull(Objects.requireNonNull(getArguments())
                                                                    .getString(MAP_ID)));
    }

    @Override
    public void showPoints(@NonNull Flowable<Point> points) {
        points.subscribe(point -> {
            LatLng latLng = new LatLng(point.getLat(), point.getLon());
            Marker marker = requireMap().addMarker(new MarkerOptions().position(latLng)
                                                                      .title(point.getTitle()));
            this.points.put(point, marker);
        });
    }

    @Override
    public void showPointDetails(@NonNull Point point) {
        getBinding().pointPhotosMapRecycler.setVisibility(View.GONE);
        BottomSheetBehavior.from(getBinding().mapBottomSheet)
                           .setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        getBinding().pointTitleMapText.setText(point.getTitle());
        getBinding().pointCoordinatesMapText.setText(point.getLat() + " " + point.getLon());
        getBinding().pointShortDescriptionMapText.setText(point.getDescription());
        getBinding().editPointButton.setOnClickListener(view -> presenter.openEdit(point));

        Objects.requireNonNull(photoRecyclerAdapter)
               .clear();
        Subject<String> uriSubject = PublishSubject.create();
        Objects.requireNonNull(point.getPhotoLinks())
               .subscribe(uri -> {
                   FirestorePhotoModel.getDownloadUri(uri)
                                      .addOnCompleteListener(task -> uriSubject.onNext(
                                              Objects.requireNonNull(task.getResult())
                                                     .toString()));
               });
        photoRecyclerAdapter.observeAdd(uriSubject.toFlowable(BackpressureStrategy.BUFFER)
                                                  .doOnNext(s -> getBinding().pointPhotosMapRecycler.setVisibility(
                                                          View.VISIBLE)));
    }

    @Override
    public void openNewPoint(@NonNull LatLng latLng) {
        startActivity(NewPointActivity.newIntent(requireContext(),
                                                 Objects.requireNonNull(Objects.requireNonNull(
                                                         getArguments())
                                                                               .getString(
                                                                                       MAP_ID)),
                                                 latLng.latitude,
                                                 latLng.longitude));
    }

    @Override
    public void openEdit(@NonNull Point point, @NonNull String mapId) {
        startActivity(EditPointActivity.newIntent(requireContext(), point.getId(), mapId));
    }

    @Override
    public void deletePoint(@NonNull Point point) {
        Objects.requireNonNull(points.get(point))
               .remove();

        points.remove(point);
    }

    @Override
    public void editPoint(@NonNull Point point) {
        for (Point pointToEdit :
                points.keySet()) {
            if (pointToEdit.getId()
                           .equals(point.getId())) {
                pointToEdit.setTitle(point.getTitle());
                pointToEdit.setDescription(point.getDescription());
                Objects.requireNonNull(points.get(pointToEdit))
                       .setTitle(pointToEdit.getTitle());
            }
        }
    }

    private boolean checkLocationPermission() {
        int result = ContextCompat.checkSelfPermission(requireContext(), LOCATION_PERMISSIONS[0]);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    private void focusMap() {
        LocationRequest request = LocationRequest.create()
                                                 .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                                 .setNumUpdates(1)
                                                 .setInterval(0);

        LocationServices.getFusedLocationProviderClient(requireContext())
                        .requestLocationUpdates(request, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                focusPoint = new LatLng(locationResult.getLastLocation()
                                                                      .getLatitude(),
                                                        locationResult.getLastLocation()
                                                                      .getLongitude());

                                LatLngBounds bounds =
                                        new LatLngBounds.Builder().include(Objects.requireNonNull(
                                                focusPoint))
                                                                  .build();
                                CameraUpdate
                                        update =
                                        CameraUpdateFactory.newLatLngBounds(bounds, 100);

                                requireMap().animateCamera(update);
                            }
                        }, null);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (checkLocationPermission()) {
                requireMap().setMyLocationEnabled(true);
                focusMap();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoRecyclerAdapter = new PhotoRecyclerAdapter();
        getBinding().pointPhotosMapRecycler.setAdapter(photoRecyclerAdapter);

        BottomSheetBehavior.from(getBinding().mapBottomSheet)
                           .setState(BottomSheetBehavior.STATE_HIDDEN);

        SupportMapFragment mapFragment = (SupportMapFragment) Objects.requireNonNull(
                getChildFragmentManager())
                                                                     .findFragmentById(R.id.map);

        Objects.requireNonNull(mapFragment)
               .getMapAsync(new OnMapReadyCallback() {
                   @SuppressLint("MissingPermission")
                   @Override
                   public void onMapReady(GoogleMap googleMap) {
                       map = googleMap;
                       if (checkLocationPermission()) {
                           requireMap().setMyLocationEnabled(true);
                           focusMap();
                       } else {
                           requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION);
                       }

                       requireMap().setOnMapLongClickListener(latLng -> {
                           presenter.createPoint(latLng);
                       });

                       requireMap().setOnMarkerClickListener(marker -> {
                           for (Map.Entry<Point, Marker> pointEntry : points.entrySet()) {
                               if (pointEntry.getValue()
                                             .equals(marker)) {
                                   presenter.loadPointDetails(pointEntry.getKey());
                               }
                           }

                           return true;
                       });

                       presenter.loadPoints();
                   }
               });
    }

    @Override
    public void onStart() {
        super.onStart();

        BottomSheetBehavior.from(getBinding().mapBottomSheet)
                           .setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @NonNull
    private GoogleMap requireMap() {
        return Objects.requireNonNull(map);
    }

    @Override
    protected FragmentMapScreenBinding inflateBinding(@NonNull LayoutInflater inflater,
                                                      @Nullable ViewGroup container,
                                                      boolean attachToRoot) {
        return FragmentMapScreenBinding.inflate(inflater, container, false);
    }

    public static MapScreenFragment newInstance(@NonNull String mapId) {
        Bundle args = new Bundle();
        args.putString(MAP_ID, mapId);
        MapScreenFragment fragment = new MapScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }
}