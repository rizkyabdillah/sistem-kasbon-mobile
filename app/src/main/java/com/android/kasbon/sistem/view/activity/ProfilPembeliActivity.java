package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.kasbon.sistem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilPembeliActivity extends AppCompatActivity {

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pembeli);






    }
}