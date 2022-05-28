package com.example.task5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onArticleSelected(int position) {
        ArticleFragment articleFragment = (ArticleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.article_fragment);
        if (articleFragment != null) {
            articleFragment.updateArticleView(position);
        } else {
            ArticleFragment newFragment = ArticleFragment.newInstance(position);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (findViewById(R.id.fragment_container) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (findViewById(R.id.fragment_container) != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    HeadlinesFragment firstFragment = new HeadlinesFragment();
                    firstFragment.setArguments(getIntent().getExtras());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, firstFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
