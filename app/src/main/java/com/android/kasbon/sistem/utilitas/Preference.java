package com.android.kasbon.sistem.utilitas;

import com.google.firebase.auth.FirebaseUser;

public class Preference {

    private static FirebaseUser userData;

    public static FirebaseUser getUserData() {
        return userData;
    }

    public static void setUserData(FirebaseUser userData) {
        Preference.userData = userData;
    }
}
