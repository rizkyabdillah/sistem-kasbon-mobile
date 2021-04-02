package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.view.fragment.HomePembeliFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        loadFragment(new HomePembeliFragment());

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment, fragment).commit();
    }

}