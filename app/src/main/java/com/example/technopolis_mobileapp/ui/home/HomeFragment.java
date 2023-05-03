package com.example.technopolis_mobileapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.technopolis_mobileapp.BiologyActivity;
import com.example.technopolis_mobileapp.DBHelper;
import com.example.technopolis_mobileapp.EconomicsActivity;
import com.example.technopolis_mobileapp.LoginActivity;
import com.example.technopolis_mobileapp.PhysicsActivity;
import com.example.technopolis_mobileapp.ScienceActivity;
import com.example.technopolis_mobileapp.SocialActivity;
import com.example.technopolis_mobileapp.TechnologyActivity;
import com.example.technopolis_mobileapp.databinding.FragmentHomeBinding;
import com.example.technopolis_mobileapp.ui.Profile.EditProfileActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DBHelper DB;
    ImageButton science;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text change
                return false;
            }
        });

        final TextView textView = binding.textHome;
        LinearLayout linearLayout = binding.row1Layout;
        LinearLayout linearLayout2 = binding.row2Layout;

        LinearLayout scienceLayout = binding.scienceLayout;
        LinearLayout physicsLayout = binding.physicsLayout;
        LinearLayout socialLayout = binding.socialLayout;

        LinearLayout economicsLayout = binding.economicsLayout;
        LinearLayout biologyLayout = binding.biologyLayout;
        LinearLayout technologyLayout = binding.technologyLayout;

        //Science apply page
        ImageButton scienceButton = binding.scienceButton;
        scienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scienceIntent = new Intent(getActivity(), ScienceActivity.class);
                startActivity(scienceIntent);
            }
        });

        //Physics apply page
        ImageButton physicsButton = binding.physicsButton;
        physicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent physicsIntent = new Intent(getActivity(), PhysicsActivity.class);
                startActivity(physicsIntent);
            }
        });

        //Social apply page
        ImageButton socialButton = binding.socialButton;
        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent socialIntent = new Intent(getActivity(), SocialActivity.class);
                startActivity(socialIntent);
            }
        });

        //Economics apply page
        ImageButton economicsButton = binding.economicsButton;
        economicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent economicsIntent = new Intent(getActivity(), EconomicsActivity.class);
                startActivity(economicsIntent);
            }
        });

        //Biology apply page
        ImageButton biologyButton = binding.biologyButton;
        biologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent biologyIntent = new Intent(getActivity(), BiologyActivity.class);
                startActivity(biologyIntent);
            }
        });

        //Technology apply page
        ImageButton techButton = binding.technologyButton;
        techButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TechnologyIntent = new Intent(getActivity(), TechnologyActivity.class);
                startActivity(TechnologyIntent);
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}