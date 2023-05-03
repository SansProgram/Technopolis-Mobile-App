package com.example.technopolis_mobileapp.ui.Apply;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.technopolis_mobileapp.BiologyApplicantActivity;
import com.example.technopolis_mobileapp.DBHelper;
import com.example.technopolis_mobileapp.EconomicsApplicantActivity;
import com.example.technopolis_mobileapp.PhysicsApplicantActivity;
import com.example.technopolis_mobileapp.ScienceApplicantActivity;
import com.example.technopolis_mobileapp.SocialApplicantActivity;
import com.example.technopolis_mobileapp.TechnologyApplicantActivity;
import com.example.technopolis_mobileapp.databinding.FragmentApplyBinding;
import com.example.technopolis_mobileapp.ui.Contact.ContactViewModel;

public class ApplyFragment extends Fragment {

    private FragmentApplyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ApplyViewModel applyViewModel =
                new ViewModelProvider(this).get(ApplyViewModel.class);
        binding = FragmentApplyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.idApply;
        Button button = binding.scienceApplicantsButton;
        Button button_phys = binding.physicsApplicantsButton;
        Button button_social = binding.socialApplicantsButton;
        Button button_economics = binding.economicsApplicantsButton;
        Button button_biology = binding.biologyApplicantsButton;
        Button button_Tech = binding.techApplicantsButton;

        applyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), ScienceApplicantActivity.class));
            }
        });

        button_phys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), PhysicsApplicantActivity.class));
            }
        });

        button_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), SocialApplicantActivity.class));
            }
        });

        button_economics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), EconomicsApplicantActivity.class));
            }
        });

        button_biology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), BiologyApplicantActivity.class));
            }
        });

        button_Tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                startActivity(new Intent(getActivity(), TechnologyApplicantActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
