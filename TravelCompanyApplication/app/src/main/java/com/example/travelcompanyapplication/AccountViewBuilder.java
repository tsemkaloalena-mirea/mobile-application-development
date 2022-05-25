package com.example.travelcompanyapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.travelcompanyapplication.db_controller.db_contracts.FlightReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountHotelDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.FlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.HotelDBHelper;
import com.example.travelcompanyapplication.ui.DownloadImageFromInternet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class AccountViewBuilder {
    private FlightDBHelper flightDBHelper;
    private HotelDBHelper hotelDBHelper;
    private AccountHotelDBHelper accountHotelDBHelper;
    private LayoutInflater inflater;
    private ViewGroup container;
    private View view;
    private LinearLayout flightsLayout;
    private LinearLayout hotelsLayout;

    public AccountViewBuilder(HotelDBHelper hotelDBHelper, AccountHotelDBHelper accountHotelDBHelper, FlightDBHelper flightDBHelper) {
        this.hotelDBHelper = hotelDBHelper;
        this.accountHotelDBHelper = accountHotelDBHelper;
        this.flightDBHelper = flightDBHelper;
    }

//    public View changeView() {
//        String departureCity = departureCityDropdown.getSelectedItem().toString();
//        String arrivalCity = arrivalCityDropdown.getSelectedItem().toString();
//        String departureDate = departureDateEditText.getText().toString();
//        Integer numberOfTickets = numberOfTicketsBar.getProgress();
//
//        ArrayList<String> selection = new ArrayList<>();
//        ArrayList<String> selectionArgs = new ArrayList<>();
//        if (!("Departure city".equals(departureCity))) {
//            selection.add(FlightReaderContract.FlightEntry.DEPARTURE_CITY + "=?");
//            selectionArgs.add(departureCity);
//        }
//        if (!("Arrival city".equals(arrivalCity))) {
//            selection.add(FlightReaderContract.FlightEntry.ARRIVAL_CITY + "=?");
//            selectionArgs.add(arrivalCity);
//        }
//        if (!("".equals(departureDate))) {
//            selection.add(FlightReaderContract.FlightEntry.DEPARTURE_DATE + " LIKE ?");
//            selectionArgs.add("%" + departureDate + "%");
//        }
//
//        view = createView(inflater, container, String.join(" and ", selection), selectionArgs.toArray(new String[0]), true);
//        return view;
//    }

    public View createView(LayoutInflater inflater, ViewGroup container, String selection, String[] selectionArgs, Boolean update) {
        if (!update) {
            this.inflater = inflater;
            this.container = container;
            view = inflater.inflate(R.layout.fragment_account, container, false);
            flightsLayout = (LinearLayout) view.findViewById(R.id.chosen_flights_container);
            hotelsLayout = (LinearLayout) view.findViewById(R.id.chosen_hotels_container);
        } else {
            flightsLayout.removeAllViewsInLayout();
            hotelsLayout.removeAllViewsInLayout();
        }

        ArrayList<ArrayList<String>> hotelsData = accountHotelDBHelper.getRecords(selection, selectionArgs);

        for (ArrayList<String> row : hotelsData) {


//            TableLayout tableLayout = createHotelTable();
//            if (!update) {
//                departureCities.add(row.get(1));
//                arrivalCities.add(row.get(2));
//            }
//
            View tableDivider = new View(view.getContext());
            tableDivider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            tableDivider.setBackgroundColor(view.getResources().getColor(R.color.table_divider));
//
//            hotelsLayout.addView(tableLayout);
            hotelsLayout.addView(tableDivider);
        }

//        departureCities = new ArrayList<>(new HashSet<String>(departureCities));
//        arrivalCities = new ArrayList<>(new HashSet<String>(arrivalCities));
//        departureCities.add(0, "Departure city");
//        arrivalCities.add(0, "Arrival city");
//        if (!update) {
//            setupMenu();
//        }
        return view;
    }

    private TableLayout createHotelTable(ArrayList<String> row) {
        TableLayout tableLayout = HotelsViewBuilder.createTableInfo(view, row);





        return tableLayout;
    }

    private void getHotelById() {

    }

    private void setupMenu() {
//        departureCityDropdown = view.findViewById(R.id.airport_departure_city_spinner);
//        ArrayAdapter<String> departureCityAdapter = createSpinnerAdapter(departureCities.toArray(new String[0]));
//        departureCityDropdown.setAdapter(departureCityAdapter);
//        arrivalCityDropdown = view.findViewById(R.id.airport_arrival_city_spinner);
//        ArrayAdapter<String> arrivalCityAdapter = createSpinnerAdapter(arrivalCities.toArray(new String[0]));
//        arrivalCityDropdown.setAdapter(arrivalCityAdapter);
//
//        departureDateEditText = (EditText) view.findViewById(R.id.airport_departure_date_text_edit);
//        setupDateDialog(departureDateEditText);
//
//        numberOfTicketsTextView = (TextView) view.findViewById(R.id.airport_number_of_tickets_text_view);
//        numberOfTicketsBar = (SeekBar) view.findViewById(R.id.airport_airport_number_of_tickets_seek_bar);
//        numberOfTicketsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                numberOfTicketsTextView.setText("Number of tickets: " + progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
//
//        findFlightsButton = (Button) view.findViewById(R.id.find_flights_button);
//        findFlightsButton.setOnClickListener(this);
//        clearFiltersButton = (Button) view.findViewById(R.id.airport_clear_filters_button);
//        clearFiltersButton.setOnClickListener(this);
    }


//    private View.OnClickListener buyTicket(String ticketId) {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<ArrayList<String>> data = flightDBHelper.getRecords(FlightReaderContract.FlightEntry._ID + "=?", new String[] {ticketId});
//                Integer numberOfTickets = numberOfTicketsBar.getProgress();
////                TODO запись в личный кабинет
//            }
//        };
//    }
}
