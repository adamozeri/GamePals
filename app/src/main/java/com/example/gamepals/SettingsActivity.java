package com.example.gamepals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.gamepals.databinding.ActivitySettingsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {


    private MaterialButton settings_BTN_signOut;
    private AppCompatImageButton settings_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews();
        settings_BTN_back.setOnClickListener(view -> loadMainActivity());
        settings_BTN_signOut.setOnClickListener(view -> signOut());

    }

    private void findViews() {
        settings_BTN_signOut = findViewById(R.id.settings_BTN_signOut);
        settings_BTN_back = findViewById(R.id.settings_BTN_back);
    }


    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}