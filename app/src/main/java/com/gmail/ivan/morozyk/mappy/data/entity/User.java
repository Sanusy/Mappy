package com.gmail.ivan.morozyk.mappy.data.entity;

import com.google.firebase.firestore.DocumentId;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {

    @DocumentId
    @Nullable
    private String id;

    @Nullable
    private String email;

    @Nullable
    private String name;

    public User() {}

    public User(@NonNull String email, @NonNull String name) {
        this.id = "";
        this.email = email;
        this.name = name;
    }

    public User(@NonNull String id, @NonNull String email, @NonNull String name) {
        this(email, name);

        this.id = id;

        // TODO: 6/2/2020 check if this constructor needed
    }

    @NonNull
    public String getId() {
        return Objects.requireNonNull(id);
    }

    @NonNull
    public String getEmail() {
        return Objects.requireNonNull(email);
    }

    @NonNull
    public String getName() {
        return Objects.requireNonNull(name);
    }
}
