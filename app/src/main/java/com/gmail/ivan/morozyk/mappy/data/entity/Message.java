package com.gmail.ivan.morozyk.mappy.data.entity;

import com.google.firebase.firestore.DocumentId;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Message {

    @DocumentId
    @Nullable
    private String id;

    @Nullable
    private String senderName;

    @NonNull
    private String senderEmail;

    @Nullable
    private String text;

    @Nullable
    private String photoLink;

    @Nullable
    private Point point;

    @Nullable
    private Date date;

    @MessageOwn
    private int messageOwner;

    public Message() {
    }

    public Message(@NonNull String senderName,
                   @NonNull String senderEmail,
                   @Nullable String text,
                   @Nullable String photoLink,
                   @Nullable Point point, @NonNull Date date) {
        this.id = "";
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.text = text;
        this.photoLink = photoLink;
        this.point = point;
        this.date = date;
    }

    public Message(@NonNull String id,
                   @NonNull String senderName,
                   @NonNull String senderEmail,
                   @Nullable String text,
                   @Nullable String photoLink,
                   @Nullable Point point, @NonNull Date date) {
        this(senderName, senderEmail, text, photoLink, point, date);

        this.id = id;

        // TODO: 6/2/2020 check if this constructor needed
    }

    @NonNull
    public String getId() {
        return Objects.requireNonNull(id);
    }

    @NonNull
    public String getSenderName() {
        return Objects.requireNonNull(senderName);
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

    public void setPoint(@Nullable Point point) {
        this.point = point;
    }

    @NonNull
    public Date getDate() {
        return Objects.requireNonNull(date);
    }

    @MessageOwn
    public int getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(@MessageOwn int messageOwner) {
        this.messageOwner = messageOwner;
    }

    @NonNull
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(@NonNull String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MessageOwn.YOU, MessageOwn.OTHER_MEMBER})
    public @interface MessageOwn {

        int YOU = 1; int OTHER_MEMBER = 2;
    }
}
