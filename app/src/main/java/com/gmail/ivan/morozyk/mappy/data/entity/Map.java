package com.gmail.ivan.morozyk.mappy.data.entity;

import androidx.annotation.NonNull;

public class Map {

    private int id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    public Map(@NonNull String title, @NonNull String description) {
        this.title = title;
        this.description = description;
    }

    public Map(int id, @NonNull String title, @NonNull String description) {
        this(title, description);

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
}
