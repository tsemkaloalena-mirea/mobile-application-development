package com.example.travelcompanyapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelcompanyapplication.AccountViewBuilder;
import com.example.travelcompanyapplication.databinding.FragmentAccountBinding;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountFlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountHotelDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.FlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.HotelDBHelper;

public class AccountFragment extends Fragment {
    private View view;
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HotelDBHelper hotelDBHelper = new HotelDBHelper(getContext());
        AccountHotelDBHelper accountHotelDBHelper = new AccountHotelDBHelper(getContext());
        FlightDBHelper flightDBHelper = new FlightDBHelper(getContext());
        AccountFlightDBHelper accountFlightDBHelper = new AccountFlightDBHelper(getContext());
        AccountViewBuilder accountViewBuilder = new AccountViewBuilder(hotelDBHelper, accountHotelDBHelper, flightDBHelper, accountFlightDBHelper);
        view = accountViewBuilder.createView(inflater, container, null, null, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}