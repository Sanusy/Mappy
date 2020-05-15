package com.gmail.ivan.morozyk.mappy.data.entity;

import androidx.annotation.NonNull;

public class User {

    private int id;

    @NonNull
    private final String email;

    @NonNull
    private final String name;

    public User(@NonNull String email, @NonNull String name) {
        this.email = email;
        this.name = name;
    }

    public User(int id, @NonNull String email, @NonNull String name) {
        this(email, name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
