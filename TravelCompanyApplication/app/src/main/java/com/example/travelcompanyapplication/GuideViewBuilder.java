package com.example.travelcompanyapplication;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelcompanyapplication.db_controller.db_contracts.HotelReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;

import java.util.ArrayList;

public class GuideViewBuilder {
    private ActionBar actionBar;
    private GuideDBHelper guideDBHelper;
    private View view;
    private static Long currentCityId = -1L;

    public GuideViewBuilder(GuideDBHelper guideDBHelper, LayoutInflater inflater, ViewGroup container, ActionBar actionBar) {
        this.guideDBHelper = guideDBHelper;
        this.actionBar = actionBar;
        view = inflater.inflate(R.layout.fragment_guide, container, false);
        if (currentCityId == -1) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            createCityListView();
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            createCityView();
        }
    }

    public View getView() {
        return view;
    }

    public View createCityView() {
        LinearLayout cityLayout = (LinearLayout) view.findViewById(R.id.city_guide_container);
        cityLayout.removeAllViewsInLayout();
        ArrayList<String> data = guideDBHelper.getRecords(HotelReaderContract.HotelEntry._ID + "=?", new String[]{String.valueOf(currentCityId)}).get(0);

        LinearLayout.LayoutParams cityNameLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView cityNameView = new TextView(view.getContext());
        cityNameView.setLayoutParams(cityNameLayoutParams);
        cityNameView.setTextSize(24);
        cityNameView.setText(data.get(1));
        cityLayout.addView(cityNameView);

        LinearLayout.LayoutParams cityDescriptionLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cityDescriptionLayoutParams.setMargins(0, 30, 0, 50);
        TextView cityDescriptionView = new TextView(view.getContext());
        cityDescriptionView.setLayoutParams(cityDescriptionLayoutParams);
        cityDescriptionView.setText(data.get(2));
        cityDescriptionView.setLineHeight(80);
        cityDescriptionView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        cityLayout.addView(cityDescriptionView);

        Button learnEntertainmentButton = new Button(view.getContext());
        LinearLayout.LayoutParams learnEntertainmentLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        learnEntertainmentButton.setLayoutParams(learnEntertainmentLayoutParams);
        learnEntertainmentButton.setText(R.string.entertainment_link_title);
        learnEntertainmentButton.setAllCaps(false);
        learnEntertainmentButton.setOnClickListener(openLink(data.get(3)));
        learnEntertainmentButton.setPadding(0, 10, 0, 10);
        learnEntertainmentButton.setBackgroundColor(view.getResources().getColor(R.color.coral));
        cityLayout.addView(learnEntertainmentButton);

        return view;
    }

    public View createCityListView() {
        currentCityId = -1L;
        LinearLayout cityListLayout = (LinearLayout) view.findViewById(R.id.city_guide_container);
        cityListLayout.removeAllViewsInLayout();

        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.setMargins(0, 20, 0, 30);
        TextView titleView = new TextView(view.getContext());
        titleView.setLayoutParams(titleLayoutParams);
        titleView.setText(R.string.choose_city_title);
        ArrayList<ArrayList<String>> data = guideDBHelper.getRecords(null, null);
        cityListLayout.addView(titleView);

        for (ArrayList<String> row : data) {
            Button openCityButton = new Button(view.getContext());
            LinearLayout.LayoutParams openCityButtonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            openCityButtonLayoutParams.setMargins(0, 20, 0, 10);
            openCityButton.setLayoutParams(openCityButtonLayoutParams);
            openCityButton.setText(row.get(1));
//            openCityButton.setAllCaps(false);
            openCityButton.setId(Integer.parseInt(row.get(0)) * 10000 + 1);
            GradientDrawable border = new GradientDrawable();
            border.setCornerRadius(15);
            border.setStroke(5, view.getResources().getColor(R.color.coral));
            openCityButton.setBackground(border);
            openCityButton.setPadding(0, 10, 0, 10);
            openCityButton.setOnClickListener(openCity(row.get(0)));
            cityListLayout.addView(openCityButton);
        }
        return view;
    }

    public View.OnClickListener openCity(String cityId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                currentCityId = Long.valueOf(cityId);
                createCityView();
            }
        };
    }

    public View.OnClickListener openLink(String link) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, location);
                view.getContext().startActivity(intent);
            }
        };
    }
}