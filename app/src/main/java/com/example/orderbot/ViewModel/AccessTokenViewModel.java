package com.example.orderbot.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orderbot.R;

public class AccessTokenViewModel extends AndroidViewModel {
    private MutableLiveData<String> accessToken;
    private Context context;
    private final String PREFERENCE_FILE_KEY = "PREFERENCE_FILE";
    private final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";

    public AccessTokenViewModel(@NonNull Application application) {
        super(application);
        this.accessToken = new MutableLiveData<String>();
        this.context = application.getApplicationContext();

        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences("PREFERENCE_FILE", Context.MODE_PRIVATE);
        String defaultAccessKey = "";
        String accessKey = sharedPreferences.getString("ACCESS_TOKEN", defaultAccessKey);
        this.setAccessToken(accessKey);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken.setValue(accessToken);

        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public MutableLiveData<String> getAccessToken() {
        return accessToken;
    }

}
