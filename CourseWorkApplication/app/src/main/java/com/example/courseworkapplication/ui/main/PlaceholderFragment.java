package com.example.courseworkapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.courseworkapplication.db_controller.FlightDBHelper;
import com.example.courseworkapplication.view_builders.FlightViewBuilder;

public class PlaceholderFragment extends Fragment {
    private FlightDBHelper mFlightDBHelper;
    private FlightViewBuilder mFlightViewBuilder;
    private View view;

    private static final String ARG_TAB_NUMBER = "section_number";
    private int tabNumber;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TAB_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlightDBHelper = new FlightDBHelper(getContext());
        mFlightViewBuilder = new FlightViewBuilder(mFlightDBHelper);

        if (getArguments() != null) {
            tabNumber = getArguments().getInt(ARG_TAB_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if (tabNumber == 1) {
            view = mFlightViewBuilder.createView(inflater, container, null, null, false);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
