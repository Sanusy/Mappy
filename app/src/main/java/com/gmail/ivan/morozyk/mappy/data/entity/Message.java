package com.gmail.ivan.morozyk.mappy.data.entity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Message {

    private int id;

    private final int userId;

    @Nullable
    private final String text;

    @Nullable
    private final String photoLink;

    @Nullable
    private final Point point;

    @NonNull
    private final Date date;

    public Message(int userId,
                   @Nullable String text,
                   @Nullable String photoLink,
                   @Nullable Point point, @NonNull Date date) {
        this.userId = userId;
        this.text = text;
        this.photoLink = photoLink;
        this.point = point;
        this.date = date;
    }

    public Message(int id,
                   int userId,
                   @Nullable String text,
                   @Nullable String photoLink,
                   @Nullable Point point, @NonNull Date date) {
        this(userId, text, photoLink, point, date);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public String getPhotoLink() {
        return photoLink;
    }

    @Nullable
    public Point getPoint() {
        return point;
    }

    @NonNull
    public Date getDate() {
        return date;
    }
}
