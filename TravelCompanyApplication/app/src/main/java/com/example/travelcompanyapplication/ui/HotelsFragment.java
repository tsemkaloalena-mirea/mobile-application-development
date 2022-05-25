package com.example.travelcompanyapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelcompanyapplication.HotelsViewBuilder;
import com.example.travelcompanyapplication.databinding.FragmentHomeBinding;
import com.example.travelcompanyapplication.databinding.FragmentHotelsBinding;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountHotelDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.HotelDBHelper;

public class HotelsFragment extends Fragment {
    private View view;
    private FragmentHotelsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHotelsBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        HotelDBHelper hotelDBHelper = new HotelDBHelper(getContext());
        AccountHotelDBHelper accountHotelDBHelper = new AccountHotelDBHelper(getContext());
        HotelsViewBuilder hotelsViewBuilder = new HotelsViewBuilder(hotelDBHelper);
        view = hotelsViewBuilder.createView(inflater, container, null, null, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}