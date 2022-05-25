package com.example.travelcompanyapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelcompanyapplication.FlightsViewBuilder;
import com.example.travelcompanyapplication.databinding.FragmentFlightsBinding;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountFlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.FlightDBHelper;

public class FlightsFragment extends Fragment {
    private View view;
    private FragmentFlightsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FlightDBHelper flightDBHelper = new FlightDBHelper(getContext());
        AccountFlightDBHelper accountFlightDBHelper = new AccountFlightDBHelper(getContext());
        FlightsViewBuilder flightsViewBuilder = new FlightsViewBuilder(flightDBHelper, accountFlightDBHelper);
        view = flightsViewBuilder.createView(inflater, container, null, null, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}