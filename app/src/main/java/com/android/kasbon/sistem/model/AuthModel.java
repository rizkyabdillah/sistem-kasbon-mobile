package com.android.kasbon.sistem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

public class AuthModel extends BaseObservable {

    private String email = "", password = "";

    public AuthModel() {
    }

    public AuthModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
