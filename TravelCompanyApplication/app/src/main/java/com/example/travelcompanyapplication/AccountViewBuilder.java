package com.example.travelcompanyapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.travelcompanyapplication.db_controller.db_contracts.AccountFlightReaderContract;
import com.example.travelcompanyapplication.db_controller.db_contracts.AccountHotelReaderContract;
import com.example.travelcompanyapplication.db_controller.db_contracts.FlightReaderContract;
import com.example.travelcompanyapplication.db_controller.db_contracts.HotelReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountFlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.AccountHotelDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.FlightDBHelper;
import com.example.travelcompanyapplication.db_controller.db_helpers.HotelDBHelper;
import com.example.travelcompanyapplication.ui.DownloadImageFromInternet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class AccountViewBuilder {
    private FlightDBHelper flightDBHelper;
    private HotelDBHelper hotelDBHelper;
    private AccountHotelDBHelper accountHotelDBHelper;
    private AccountFlightDBHelper accountFlightDBHelper;
    private LayoutInflater inflater;
    private ViewGroup container;
    private View view;
    private LinearLayout flightsLayout;
    private LinearLayout hotelsLayout;

    public AccountViewBuilder(HotelDBHelper hotelDBHelper, AccountHotelDBHelper accountHotelDBHelper, FlightDBHelper flightDBHelper, AccountFlightDBHelper accountFlightDBHelper) {
        this.hotelDBHelper = hotelDBHelper;
        this.accountHotelDBHelper = accountHotelDBHelper;
        this.flightDBHelper = flightDBHelper;
        this.accountFlightDBHelper = accountFlightDBHelper;
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

        ArrayList<ArrayList<String>> flightTicketsData = accountFlightDBHelper.getRecords(selection, selectionArgs);

        for (ArrayList<String> row : flightTicketsData) {

            TableLayout tableLayout = createFlightTable(row);

            View tableDivider = new View(view.getContext());
            tableDivider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            tableDivider.setBackgroundColor(view.getResources().getColor(R.color.table_divider));

            flightsLayout.addView(tableLayout);
            flightsLayout.addView(tableDivider);
        }

        ArrayList<ArrayList<String>> bookedHotelsData = accountHotelDBHelper.getRecords(selection, selectionArgs);

        for (ArrayList<String> row : bookedHotelsData) {
            TableLayout tableLayout = createHotelTable(row);

            View tableDivider = new View(view.getContext());
            tableDivider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            tableDivider.setBackgroundColor(view.getResources().getColor(R.color.table_divider));

            hotelsLayout.addView(tableLayout);
            hotelsLayout.addView(tableDivider);
        }
        return view;
    }

    private TableLayout createFlightTable(ArrayList<String> flightTicket) {
        ArrayList<String> flightData = flightDBHelper.getRecords(FlightReaderContract.FlightEntry._ID + " LIKE ?", new String[]{flightTicket.get(1)}).get(0);
        TableLayout tableLayout = FlightsViewBuilder.createTableInfo(view, flightData);

        TableLayout.LayoutParams numberOfTicketsLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        numberOfTicketsLayoutParams.setMargins(0, 70, 0, 0);
        TextView numberOfTicketsView = HotelsViewBuilder.createTableColumn(view, numberOfTicketsLayoutParams, "Number of tickets: " + flightTicket.get(2), 18, Integer.parseInt(flightData.get(0)) * 10000 + 17);
        tableLayout.addView(numberOfTicketsView);

        Integer totalCost = Integer.valueOf(flightData.get(6).replace(" ", "")) * Integer.valueOf(flightTicket.get(2)) / 60;

        TableLayout.LayoutParams totalCostLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        totalCostLayoutParams.setMargins(0, 20, 0, 0);
        TextView totalCostView = HotelsViewBuilder.createTableColumn(view, totalCostLayoutParams, "Total cost: " + totalCost + " $", 24, Integer.parseInt(flightData.get(0)) * 10000 + 18);
        tableLayout.addView(totalCostView);

        Button deleteFlightButton = new Button(view.getContext());
        TableLayout.LayoutParams deleteFlightButtonLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        deleteFlightButtonLayoutParams.setMargins(0, 20, 0, 30);
        deleteFlightButton.setLayoutParams(deleteFlightButtonLayoutParams);
        deleteFlightButton.setText("Delete flight");
        deleteFlightButton.setId(Integer.parseInt(flightData.get(0)) * 10000 + 19);
        deleteFlightButton.setOnClickListener(deleteFlight(flightTicket.get(0)));
        tableLayout.addView(deleteFlightButton);

        return tableLayout;
    }

    private TableLayout createHotelTable(ArrayList<String> bookingInfo) {
        ArrayList<String> hotelData = hotelDBHelper.getRecords(HotelReaderContract.HotelEntry._ID + " LIKE ?", new String[]{bookingInfo.get(3)}).get(0);
        TableLayout tableLayout = HotelsViewBuilder.createTableInfo(view, hotelData);
        TableLayout.LayoutParams adultsNumberLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        adultsNumberLayoutParams.setMargins(0, 50, 0, 0);
        TextView adultsNumberView = HotelsViewBuilder.createTableColumn(view, adultsNumberLayoutParams, "Number of adults: " + bookingInfo.get(4), 18, Integer.parseInt(hotelData.get(0)) * 10000 + 10);
        tableLayout.addView(adultsNumberView);
        TableLayout.LayoutParams kidsNumberLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        kidsNumberLayoutParams.setMargins(0, 20, 0, 0);
        TextView kidsNumberView = HotelsViewBuilder.createTableColumn(view, kidsNumberLayoutParams, "Number of kids: " + bookingInfo.get(5), 18, Integer.parseInt(hotelData.get(0)) * 10000 + 11);
        tableLayout.addView(kidsNumberView);

        TableLayout.LayoutParams arrivalDateLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        arrivalDateLayoutParams.setMargins(0, 50, 0, 0);
        TextView arrivalDateNumberView = HotelsViewBuilder.createTableColumn(view, arrivalDateLayoutParams, "Arrival date: " + bookingInfo.get(1), 18, Integer.parseInt(hotelData.get(0)) * 10000 + 12);
        tableLayout.addView(arrivalDateNumberView);
        TableLayout.LayoutParams departureDateLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        departureDateLayoutParams.setMargins(0, 20, 0, 0);
        TextView departureDateNumberView = HotelsViewBuilder.createTableColumn(view, departureDateLayoutParams, "Departure date: " + bookingInfo.get(2), 18, Integer.parseInt(hotelData.get(0)) * 10000 + 13);
        tableLayout.addView(departureDateNumberView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar arrivalCalendar = Calendar.getInstance();
        Calendar departureCalendar = Calendar.getInstance();
        Calendar difference = Calendar.getInstance();
        Integer numberOfNights = 0;
        try {
            arrivalCalendar.setTime(dateFormat.parse(bookingInfo.get(1)));
            departureCalendar.setTime(dateFormat.parse(bookingInfo.get(2)));
            difference.setTimeInMillis(arrivalCalendar.getTimeInMillis() - departureCalendar.getTimeInMillis());
            numberOfNights = difference.get(Calendar.DAY_OF_YEAR) - 1;
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        Double totalCost = (Double.valueOf(bookingInfo.get(4)) + Double.valueOf(bookingInfo.get(5)) / 2) * numberOfNights * Integer.valueOf(hotelData.get(4));

        TableLayout.LayoutParams numberOfNightsLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        numberOfNightsLayoutParams.setMargins(0, 20, 0, 0);
        TextView numberOfNightsView = HotelsViewBuilder.createTableColumn(view, departureDateLayoutParams, "Number of nights: " + numberOfNights, 18, Integer.parseInt(hotelData.get(0)) * 10000 + 14);
        tableLayout.addView(numberOfNightsView);

        TableLayout.LayoutParams totalCostLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        totalCostLayoutParams.setMargins(0, 50, 0, 0);
        TextView totalCostView = HotelsViewBuilder.createTableColumn(view, totalCostLayoutParams, "Total cost for room: " + totalCost + " $", 24, Integer.parseInt(hotelData.get(0)) * 10000 + 15);
        tableLayout.addView(totalCostView);

        Button deleteHotelButton = new Button(view.getContext());
        TableLayout.LayoutParams deleteHotelButtonLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        deleteHotelButtonLayoutParams.setMargins(0, 20, 0, 30);
        deleteHotelButton.setLayoutParams(deleteHotelButtonLayoutParams);
        deleteHotelButton.setText("Delete room");
        deleteHotelButton.setId(Integer.parseInt(hotelData.get(0)) * 10000 + 16);
        deleteHotelButton.setOnClickListener(deleteRoom(bookingInfo.get(0)));
        tableLayout.addView(deleteHotelButton);

        return tableLayout;
    }

    private View.OnClickListener deleteRoom(String rowId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = AccountHotelReaderContract.HotelEntry._ID + " LIKE ?";
                String[] selectionArgs = {rowId};
                SQLiteDatabase database = accountHotelDBHelper.getWritableDatabase();
                database.delete(AccountHotelReaderContract.HotelEntry.TABLE_NAME, selection, selectionArgs);
                createView(inflater, container, null, null, true);
            }
        };
    }

    private View.OnClickListener deleteFlight(String rowId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = AccountFlightReaderContract.FlightEntry._ID + " LIKE ?";
                String[] selectionArgs = {rowId};
                SQLiteDatabase database = accountFlightDBHelper.getWritableDatabase();
                database.delete(AccountFlightReaderContract.FlightEntry.TABLE_NAME, selection, selectionArgs);
                createView(inflater, container, null, null, true);
            }
        };
    }
}
