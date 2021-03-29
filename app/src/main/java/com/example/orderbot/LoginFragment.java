package com.example.orderbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.orderbot.Request.RequestSingleton;
import com.example.orderbot.ViewModel.AccessTokenViewModel;
import com.example.orderbot.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginFragment extends Fragment {
    private JsonObjectRequest jsonObjectRequest;

    private AccessTokenViewModel accessTokenViewModel;
    private FragmentLoginBinding binding;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        accessTokenViewModel = new ViewModelProvider(requireActivity()).get(AccessTokenViewModel.class);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return binding.getRoot();
    }

    private void login() {
        if (binding.usernameInput.getText().toString().isEmpty() || binding.passwordInput.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in username and password", Toast.LENGTH_SHORT).show();
        } else {
            binding.loginButton.setText("Login in...");

            HashMap<String, String> params = new HashMap<>();
            params.put("username", binding.usernameInput.getText().toString());
            params.put("password", binding.passwordInput.getText().toString());

            String url = Settings.domain + "/api/auth/login";

            RequestQueue queue = RequestSingleton.getInstance(getContext()).getRequestQueue();

            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        // save access token to shared preferences
                        accessTokenViewModel.setAccessToken(response.get("access_token").toString());
                        // done

                        // replace fragments
                        getParentFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.main_content, AdminPanelFragment.class, null)
                                .disallowAddToBackStack()
                                .commit();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Server Error. Check Internet Connection.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    binding.loginButton.setText("Login");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Username or password was incorrect", Toast.LENGTH_LONG).show();
                    binding.loginButton.setText("Login");
                }
            });

            RequestSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (jsonObjectRequest != null) {
            jsonObjectRequest.cancel();
        }
    }
}