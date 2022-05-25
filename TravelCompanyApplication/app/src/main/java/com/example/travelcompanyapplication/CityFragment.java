package com.example.travelcompanyapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.travelcompanyapplication.db_controller.db_contracts.HotelReaderContract;
import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;

import java.util.ArrayList;

public class CityFragment extends Fragment {
    private static GuideDBHelper guideDBHelper;
    static final String CITY_ID = "id";
    long mId = -1;

    public CityFragment(GuideDBHelper guideDBHelper) {
        this.guideDBHelper = guideDBHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mId = savedInstanceState.getInt(CITY_ID);
        }
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt(CITY_ID));
        } else if (mId != -1) {
            updateArticleView(mId);
        }
    }

    public void updateArticleView(long id) {
        ArrayList<String> data = guideDBHelper.getRecords(HotelReaderContract.HotelEntry._ID + " LIKE ?", new String[]{String.valueOf(id)}).get(0);

        TextView article = (TextView) getActivity().findViewById(R.id.article_text);
        article.setText(String.valueOf(id));
        mId = id;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(CITY_ID, mId);
    }

    public static CityFragment newInstance(long id) {
        CityFragment fragment = new CityFragment(guideDBHelper);
        Bundle args = new Bundle();
        args.putLong(CITY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }
}
