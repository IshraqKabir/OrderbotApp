package com.example.orderbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderbot.ViewModel.AccessTokenViewModel;
import com.example.orderbot.databinding.FragmentTopbarBinding;

public class TopbarFragment extends Fragment {
    private View rootView;

    private FragmentTopbarBinding binding;
    private AccessTokenViewModel accessTokenViewModel;

    public TopbarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTopbarBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        accessTokenViewModel = new ViewModelProvider(requireActivity()).get(AccessTokenViewModel.class);

        accessTokenViewModel.getAccessToken().observe(getViewLifecycleOwner(), accessToken -> {
            if (accessToken == "") {
                binding.logoutButton.setVisibility(View.INVISIBLE);
            } else {
                binding.logoutButton.setVisibility(View.VISIBLE);
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTokenViewModel.setAccessToken("");

                // replace fragments
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_content, LoginFragment.class, null)
                        .disallowAddToBackStack()
                        .commit();
            }
        });

        return rootView;
    }
}