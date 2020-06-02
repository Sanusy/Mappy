package com.gmail.ivan.morozyk.mappy.data.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Flowable;

public class Point {

    @DocumentId
    @Nullable
    private String id;

    @Nullable
    private String title;

    @Nullable
    private String description;

    private double lat;

    private double lon;

    @Nullable
    private Flowable<String> photoLinks;

    public Point() {}

    public Point(@NonNull String title,
                 @NonNull String description,
                 double lat,
                 double lon) {
        this.id = "";
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public Point(@NonNull String id,
                 @NonNull String title,
                 @NonNull String description,
                 double lat,
                 double lon) {
        this(title, description, lat, lon);

        this.id = id;

        // TODO: 6/2/2020 check if this constructor needed
    }

    @NonNull
    public String getId() {
        return Objects.requireNonNull(id);
    }

    @NonNull
    public String getTitle() {
        return Objects.requireNonNull(title);
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return Objects.requireNonNull(description);
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Exclude
    @Nullable
    public Flowable<String> getPhotoLinks() {
        return photoLinks;
    }

    public void setPhotoLinks(@Nullable Flowable<String> photoLinks) {
        this.photoLinks = photoLinks;
    }
}
