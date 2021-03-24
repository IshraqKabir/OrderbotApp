package com.example.orderbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to remove the action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_content, LoginFragment.class, null)
                    .commit();
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultAccessKey = "";
        String accessKey = sharedPreferences.getString(getString(R.string.access_token_key), defaultAccessKey);

        if (accessKey != "") {
            Log.d("main", "onCreate: ok");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(true);

            fragmentTransaction.replace(R.id.main_content, AdminPanelFragment.class, null);

            fragmentTransaction.commit();
        }

        setContentView(R.layout.activity_main);
    }
}