package com.gmail.ivan.morozyk.mappy.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Observable;

public class Point {

    private int id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private final double lat;

    private final double lon;

    @Nullable
    private final Observable<String> photoLinks;

    public Point(@NonNull String title,
                 @NonNull String description,
                 double lat,
                 double lon,
                 @Nullable Observable<String> photoLinks) {
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.photoLinks = photoLinks;
    }

    public Point(int id,
                 @NonNull String title,
                 @NonNull String description,
                 double lat,
                 double lon,
                 @Nullable Observable<String> photoLinks) {
        this(title, description, lat, lon, photoLinks);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
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

    @Nullable
    public Observable<String> getPhotoLinks() {
        return photoLinks;
    }
}
