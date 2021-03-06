package com.gmail.ivan.morozyk.mappy.data.entity;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Map {

    @DocumentId
    @Nullable
    private String id;

    @Nullable
    private String title;

    @Nullable
    private String description;

    @NonNull
    private Date timeStamp;

    public Map() { }

    public Map(@NonNull String title, @NonNull String description) {
        this.id = "";
        this.title = title;
        this.description = description;
        this.timeStamp = new Date();
    }

    public Map(@NonNull String id, @NonNull String title, @NonNull String description) {
        this(title, description);

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

    @NonNull
    @ServerTimestamp
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NonNull Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Map map = (Map) o;
        return Objects.equals(id, map.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
