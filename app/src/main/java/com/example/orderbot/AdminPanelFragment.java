package com.example.orderbot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderbot.ViewModel.AccessTokenViewModel;
import com.example.orderbot.ViewModel.AdminPanelViewModel;
import com.example.orderbot.databinding.FragmentAdminPanelBinding;

public class AdminPanelFragment extends Fragment {
    private FragmentAdminPanelBinding binding;
    private AccessTokenViewModel accessTokenViewModel;
    private AdminPanelViewModel adminPanelViewModel;

    private final String TAG = AdminPanelFragment.class.getSimpleName();

    public AdminPanelFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminPanelBinding.inflate(getLayoutInflater());

        accessTokenViewModel = new ViewModelProvider(requireActivity()).get(AccessTokenViewModel.class);
        adminPanelViewModel = new ViewModelProvider(requireActivity()).get(AdminPanelViewModel.class);

        adminPanelViewModel.getCards(accessTokenViewModel.getAccessToken().getValue());

        adminPanelViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.admin_panel_fragment_main_container, AdminPanelLoadingFragment.class, null)
                        .commit();
            } else {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.admin_panel_fragment_main_container, AllCardsFragment.class, null)
                        .disallowAddToBackStack()
                        .commit();
            }
        });

        adminPanelViewModel.getCards(accessTokenViewModel.getAccessToken().getValue()).observe(getViewLifecycleOwner(), cards -> {
            if (cards != null) {
                Log.d("change", "cards has changed to length: " + cards.size());
            }
        });

        return binding.getRoot();
    }
}