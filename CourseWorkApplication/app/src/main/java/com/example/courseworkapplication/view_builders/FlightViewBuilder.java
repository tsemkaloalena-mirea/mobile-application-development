package com.example.courseworkapplication.view_builders;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.courseworkapplication.R;
import com.example.courseworkapplication.db_controller.FlightDBHelper;
import com.example.courseworkapplication.db_controller.FlightReaderContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class FlightViewBuilder implements View.OnClickListener {
    private FlightDBHelper flightDBHelper;
    private LayoutInflater inflater;
    private ViewGroup container;
    private EditText departureDateEditText;
    private EditText arrivalDateEditText;
    private Spinner departureCityDropdown;
    private Spinner arrivalCityDropdown;
    private TextView numberOfTicketsTextView;
    private SeekBar numberOfTicketsBar;
    private Button findFlightsButton;
    private View view;
    private LinearLayout layout;
    ArrayList<String> departureCities = new ArrayList<String>();
    ArrayList<String> arrivalCities = new ArrayList<String>();

    public FlightViewBuilder(FlightDBHelper flightDBHelper) {
        this.flightDBHelper = flightDBHelper;
    }

    public View changeView() {
        String departureCity = departureCityDropdown.getSelectedItem().toString();
        String arrivalCity = arrivalCityDropdown.getSelectedItem().toString();
        String departureDate = departureDateEditText.getText().toString();
        String arrivalDate = arrivalDateEditText.getText().toString();
        Integer numberOfTickets = numberOfTicketsBar.getProgress();

        ArrayList<String> selection = new ArrayList<>();
        ArrayList<String> selectionArgs = new ArrayList<>();
        if (!("Departure city".equals(departureCity))) {
            selection.add(FlightReaderContract.FlightEntry.DEPARTURE_CITY + "=?");
            selectionArgs.add(departureCity);
        }
        if (!("Arrival city".equals(arrivalCity))) {
            selection.add(FlightReaderContract.FlightEntry.ARRIVAL_CITY + "=?");
            selectionArgs.add(arrivalCity);
        }
        if (!("".equals(departureDate))) {
            selection.add(FlightReaderContract.FlightEntry.DEPARTURE_DATE + " LIKE ?");
            selectionArgs.add("%" + departureDate + "%");
        }
        if (!("".equals(arrivalDate))) {
            selection.add(FlightReaderContract.FlightEntry.ARRIVAL_DATE + "=?");
            selectionArgs.add(arrivalDate);
        }

        view = createView(inflater, container, String.join(" and ", selection), selectionArgs.toArray(new String[0]), true);
        return view;
    }

    public View createView(LayoutInflater inflater, ViewGroup container, String selection, String[] selectionArgs, Boolean update) {
        if (!update) {
            this.inflater = inflater;
            this.container = container;
            view = inflater.inflate(R.layout.fragment_airport, container, false);
            layout = (LinearLayout) view.findViewById(R.id.flights_container);
        } else {
            layout.removeAllViewsInLayout();
        }

        ArrayList<ArrayList<String>> data = flightDBHelper.getRecords(selection, selectionArgs);

        for (ArrayList<String> row : data) {
            TableLayout tableLayout = createTableItem(row);
            if (!update) {
                departureCities.add(row.get(1));
                arrivalCities.add(row.get(2));
            }

            View tableDivider = new View(view.getContext());
            tableDivider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            tableDivider.setBackgroundColor(view.getResources().getColor(R.color.airport_table_divider));

            layout.addView(tableLayout);
            layout.addView(tableDivider);
        }

        departureCities = new ArrayList<>(new HashSet<String>(departureCities));
        arrivalCities = new ArrayList<>(new HashSet<String>(arrivalCities));
        departureCities.add(0, "Departure city");
        arrivalCities.add(0, "Arrival city");
        setupMenu();
        return view;
    }

    private TableLayout createTableItem(ArrayList<String> row) {
        TableLayout tableLayout = new TableLayout(view.getContext());
        LinearLayout.LayoutParams tableLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tableLayoutParams.setMargins(0, 20, 0, 20);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setLayoutParams(tableLayoutParams);

        TableRow dateRow = new TableRow(view.getContext());
        dateRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        TableRow.LayoutParams departureDateLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView departureDateView = createTableColumn(departureDateLayoutParams, row.get(3).split(" ")[0], 16, Integer.parseInt(row.get(0)) * 10000 + 1);
        TableRow.LayoutParams arrivalDateLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView arrivalDateView = createTableColumn(arrivalDateLayoutParams, row.get(4).split(" ")[0], 16, Integer.parseInt(row.get(0)) * 10000 + 2);
        dateRow.addView(departureDateView);
        dateRow.addView(arrivalDateView);
        tableLayout.addView(dateRow);

        TableRow timeRow = new TableRow(view.getContext());
        timeRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        TableRow.LayoutParams departureTimeLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView departureTimeView = createTableColumn(departureTimeLayoutParams, row.get(3).split(" ")[1], 34, Integer.parseInt(row.get(0)) * 10000 + 3);
        TableRow.LayoutParams arrivalTimeLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView arrivalTimeView = createTableColumn(arrivalTimeLayoutParams, row.get(4).split(" ")[1], 34, Integer.parseInt(row.get(0)) * 10000 + 4);
        timeRow.addView(departureTimeView);
        timeRow.addView(arrivalTimeView);
        tableLayout.addView(timeRow);

        TableRow cityRow = new TableRow(view.getContext());
        TableLayout.LayoutParams cityRowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        cityRowLayoutParams.setMargins(0, 10, 0, 0);
        cityRow.setLayoutParams(cityRowLayoutParams);
        TableRow.LayoutParams departureCityLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView departureCityView = createTableColumn(departureCityLayoutParams, row.get(1), 16, Integer.parseInt(row.get(0)) * 10000 + 5);
        TableRow.LayoutParams arrivalCityLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
        TextView arrivalCityView = createTableColumn(arrivalCityLayoutParams, row.get(2), 16, Integer.parseInt(row.get(0)) * 10000 + 6);
        cityRow.addView(departureCityView);
        cityRow.addView(arrivalCityView);
        tableLayout.addView(cityRow);

        TableRow costRow = new TableRow(view.getContext());
        TableLayout.LayoutParams costRowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        costRowLayoutParams.setMargins(0, 20, 0, 0);
        costRow.setLayoutParams(costRowLayoutParams);
        TableRow.LayoutParams costLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TextView costView = createTableColumn(costLayoutParams, row.get(6) + " ₽", 28, Integer.parseInt(row.get(0)) * 10000 + 7);
        costRow.addView(costView);
        tableLayout.addView(costRow);

        TableRow airlineRow = new TableRow(view.getContext());
        TableLayout.LayoutParams airlineRowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        airlineRowLayoutParams.setMargins(0, 10, 0, 0);
        airlineRow.setLayoutParams(airlineRowLayoutParams);
        TableRow.LayoutParams airlineLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TextView airlineView = createTableColumn(airlineLayoutParams, row.get(5), 16, Integer.parseInt(row.get(0)) * 10000 + 8);
        airlineRow.addView(airlineView);
        tableLayout.addView(airlineRow);

        Button ticketBuyButton = new Button(view.getContext());
        TableLayout.LayoutParams ticketBuyButtonLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        ticketBuyButtonLayoutParams.setMargins(0, 20, 0, 0);
        ticketBuyButton.setLayoutParams(ticketBuyButtonLayoutParams);
        ticketBuyButton.setText("Buy ticket");
        ticketBuyButton.setId(Integer.parseInt(row.get(0)) * 10000 + 9);
        ticketBuyButton.setOnClickListener(buyTicket(row.get(0)));
        tableLayout.addView(ticketBuyButton);

        return tableLayout;
    }

    private View.OnClickListener buyTicket(String ticketId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<String>> data = flightDBHelper.getRecords(FlightReaderContract.FlightEntry._ID + "=?", new String[] {ticketId});
                Integer numberOfTickets = numberOfTicketsBar.getProgress();
//                TODO запись в личный кабинет
            }
        };
    }

    private TextView createTableColumn(TableRow.LayoutParams layoutParams, String text, Integer textSize, Integer id) { //TODO remove view
        TextView textView = new TextView(view.getContext());
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setId(id);
        return textView;
    }

    private void setupMenu() {
        departureCityDropdown = view.findViewById(R.id.departure_city_spinner);
        ArrayAdapter<String> departureCityAdapter = createSpinnerAdapter(departureCities.toArray(new String[0]));
        departureCityDropdown.setAdapter(departureCityAdapter);
        arrivalCityDropdown = view.findViewById(R.id.arrival_city_spinner);
        ArrayAdapter<String> arrivalCityAdapter = createSpinnerAdapter(arrivalCities.toArray(new String[0]));
        arrivalCityDropdown.setAdapter(arrivalCityAdapter);

        departureDateEditText = (EditText) view.findViewById(R.id.departure_date_text_edit);
        arrivalDateEditText = (EditText) view.findViewById(R.id.arrival_date_text_edit);
        setupDateDialog(departureDateEditText);
        setupDateDialog(arrivalDateEditText);

        numberOfTicketsTextView = (TextView) view.findViewById(R.id.number_of_tickets_text_view);
        numberOfTicketsBar = (SeekBar) view.findViewById(R.id.number_of_tickets_seek_bar);
        numberOfTicketsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                numberOfTicketsTextView.setText("Number of tickets: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        findFlightsButton = (Button) view.findViewById(R.id.find_flights_button);
        findFlightsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_flights_button:

                this.view = changeView();
                break;
        }

    }

    private ArrayAdapter<String> createSpinnerAdapter(String[] listItems) {
        return new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, listItems) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
    }

    private void setupDateDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                editText.setText(dateFormat.format(calendar.getTime()));
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
