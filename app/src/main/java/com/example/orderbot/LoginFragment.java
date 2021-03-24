package com.example.orderbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.orderbot.Request.RequestSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginFragment extends Fragment {
    private View rootView;
    private Button loginButton;
    private EditText username, password;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = rootView.findViewById(R.id.login_button);
        username = rootView.findViewById(R.id.usernameInput);
        password = rootView.findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in username and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginButton.setText("Login in...");

                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username.getText().toString());
                    params.put("password", password.getText().toString());


                    String url = "https://orderbot.online/api/auth/login";

                    RequestQueue queue = RequestSingleton.getInstance(getContext()).getRequestQueue();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Login", "access_token " + response.get("access_token"));
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            loginButton.setText("Login");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", "error");
                            Toast.makeText(getContext(), "Username or password was incorrect", Toast.LENGTH_LONG).show();
                            loginButton.setText("Login");
                        }
                    });


                    RequestSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
                }
            }
        });


        return rootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}