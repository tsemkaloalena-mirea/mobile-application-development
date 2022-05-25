package com.example.travelcompanyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;

import java.util.ArrayList;

public class GuideViewBuilder {
    private GuideDBHelper guideDBHelper;
    private LayoutInflater inflater;
    private ViewGroup container;
    private View view;
    private LinearLayout layout;

    public GuideViewBuilder(GuideDBHelper guideDBHelper) {
        this.guideDBHelper = guideDBHelper;
    }

    public View createView(LayoutInflater inflater, ViewGroup container, String selection, String[] selectionArgs, Boolean update) {
        if (!update) {
            this.inflater = inflater;
            this.container = container;
            view = inflater.inflate(R.layout.fragment_guide, container, false);
            layout = (LinearLayout) view.findViewById(R.id.guide_container);
        } else {
            layout.removeAllViewsInLayout();
        }

        ArrayList<ArrayList<String>> data = guideDBHelper.getRecords(selection, selectionArgs);
        for (ArrayList<String> row : data) {
            Button openCityButton = new Button(view.getContext());
            TableLayout.LayoutParams openCityButtonLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            openCityButtonLayoutParams.setMargins(0, 20, 0, 0);

            openCityButton.setLayoutParams(openCityButtonLayoutParams);
            openCityButton.setText(row.get(1));
            openCityButton.setTextSize(18);
            openCityButton.setAllCaps(false);
            openCityButton.setId(Integer.parseInt(row.get(0)) * 10000 + 7);
//            openCityButton.setOnClickListener(bookRoom(row.get(0)));
            layout.addView(openCityButton);
        }
        return view;
    }
}