package com.example.courseworkapplication;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.example.courseworkapplication.ui.main.SectionsPagerAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


public class CityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionsPagerAdapter(CityActivity.this, getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_actions, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                setContentView(R.layout.activity_search);
////                openSearch();
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                return true;
//            case R.id.action_settings:
//                setContentView(R.layout.activity_settings);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////                openSettings();
//                return true;
//            case android.R.id.home:
////                setContentView(R.layout.activity_main);
//                setContentView(binding.getRoot());
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}