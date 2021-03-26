package com.example.orderbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.orderbot.ViewModel.AccessTokenViewModel;

public class MainActivity extends AppCompatActivity {
    private AccessTokenViewModel accessTokenViewModel;

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

        accessTokenViewModel = new ViewModelProvider(this).get(AccessTokenViewModel.class);

        String accessToken = accessTokenViewModel.getAccessToken().getValue().toString();

        if (accessToken != "") {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(true);

            fragmentTransaction.replace(R.id.main_content, AdminPanelFragment.class, null);

            fragmentTransaction.commit();
        }

        setContentView(R.layout.activity_main);
    }
}