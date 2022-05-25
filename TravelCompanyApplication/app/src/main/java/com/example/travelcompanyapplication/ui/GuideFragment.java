package com.example.travelcompanyapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelcompanyapplication.CityFragment;
import com.example.travelcompanyapplication.GuideViewBuilder;
import com.example.travelcompanyapplication.databinding.FragmentGuideBinding;
import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;


public class GuideFragment extends Fragment implements CityFragment.O {
    private View view;
    private FragmentGuideBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuideBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        GuideDBHelper guideDBHelper = new GuideDBHelper(getContext());
        GuideViewBuilder guideViewBuilder = new GuideViewBuilder(guideDBHelper);
        view = guideViewBuilder.createView(inflater, container, null, null, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}