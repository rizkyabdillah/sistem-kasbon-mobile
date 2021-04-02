package com.android.kasbon.sistem.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    private String uId, name, email;
    private boolean isAuthenticated, isNew, isCreated;

    public User() {
    }

    public User(String uId, String name, String email, boolean isAuthenticated, boolean isNew, boolean isCreated) {
        this.uId = uId;
        this.name = name;
        this.email = email;
        this.isAuthenticated = isAuthenticated;
        this.isNew = isNew;
        this.isCreated = isCreated;
    }

    public User(String uId, String name, String email) {
        this.uId = uId;
        this.name = name;
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }
}
